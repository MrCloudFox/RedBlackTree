import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

//use https://www.cs.usfca.edu/~galles/visualization/RedBlack.html

public class RedBlackTree {
    private Node root;
    private static Node nil;

    public RedBlackTree(int val) {
        root = new Node(val);
        root.setBlack();
        nil = new Node();
    }

    public RedBlackTree(List<Node> listOfValues) {
        for (Node node : listOfValues) {
            insertNode(node);
        }
    }

    public static Node getNil() {
        return nil;
    }

    public Node getRoot() {
        return root;
    }

    public void leftRotate(Node node) {
        Node parent = node.getParent();

        node.setParent(node.getRightChild());
        if (node != root) {
            if (node == node.getParent().getLeftChild()) {
                parent.setLeftChild(node.getLeftChild());
            } else {
                parent.setRightChild(node.getRightChild());
            }

            node.getRightChild().setParent(parent);
        } else {
            root = node.getParent();
            node.getRightChild().setParent(null);
        }
        if (!node.getRightChild().isLeftChildFree()) {
            node.getRightChild().getLeftChild().setParent(node);
            node.setRightChild(node.getRightChild().getLeftChild());
        } else {
            node.setRightChild(nil);
        }
        node.getParent().setLeftChild(node);

    }

    public void rightRotate(Node node) {
        Node parent = node.getParent();

        node.setParent(node.getLeftChild());
        if (node != root) {
            if (node == node.getParent().getRightChild()) {
                parent.setRightChild(node.getLeftChild());
            } else {
                parent.setLeftChild(node.getLeftChild());
            }
            node.getLeftChild().setParent(parent);
        } else {
            root = node.getParent();
            node.getLeftChild().setParent(null);
        }
        if (!node.getLeftChild().isRightChildFree()) {
            node.getLeftChild().getRightChild().setParent(node);
            node.setLeftChild(node.getLeftChild().getRightChild());
        } else {
            node.setLeftChild(nil);
        }
        node.getParent().setRightChild(node);
    }

    public void insertNode(Node node) {
        Node comparableNode;
        if (root == null) {
            root = node;
        } else {
            comparableNode = root;
            while (true) {
                if (node.getValue() >= comparableNode.getValue()) {
                    if(!comparableNode.isRightChildFree()) {
                        comparableNode = comparableNode.getRightChild();
                        continue;
                    } else {
                        comparableNode.setRightChild(node);
                        node.setParent(comparableNode);
                        break;
                    }
                } else {
                    if(!comparableNode.isLeftChildFree()) {
                        comparableNode = comparableNode.getLeftChild();
                        continue;
                    } else {
                        comparableNode.setLeftChild(node);
                        node.setParent(comparableNode);
                        break;
                    }
                }
            }
        }
//        node.setLeftChild(nil);
//        node.setRightChild(nil);
        fixAfterInsert(node);
    }


    public void fixAfterInsert(Node node) {
        Node tempNode;

        while (node != root && node.getParent().isRed()) {
            //System.out.println(node.getParent() + " " + node.getGrandfather().getLeftChild());
            if (node.getParent() == node.getGrandfather().getLeftChild()) {
                if (node.isUncleFree()) {
                    node.getGrandfather().setRed();
                    node.getParent().setBlack();
                    node.setRed();
                    rightRotate(node.getGrandfather());
                    break;
                }
                tempNode = node.getUncle();                                 //testing after
                //System.out.println(tempNode);
                if (tempNode.isRed()) {
                    tempNode.setBlack();
                    node.getParent().setBlack();
                    node.getGrandfather().setRed();
                    node = node.getGrandfather();
                } else {
                    if (node == node.getParent().getRightChild()) {
                        node = node.getParent();
                        leftRotate(node);
                    }
                    node.getParent().setBlack();
                    node.getGrandfather().setRed();  // may be confused
                    //System.out.println(node.getGrandfather().getValue());
                    rightRotate(node.getGrandfather());
                }
            } else {
                if (node.isUncleFree()) {
                    node.getGrandfather().setRed();
                    node.getParent().setBlack();
                    node.setRed();
                    leftRotate(node.getGrandfather());
                    break;
                }
                tempNode = node.getUncle(); //.getGrandfather().getLeftChild();
                if (tempNode.isRed()) {
                    tempNode.setBlack();
                    node.getParent().setBlack();
                    node.getGrandfather().setRed();
                    node = node.getGrandfather();
                } else {
                    if (node == node.getParent().getLeftChild()) {
                        node = node.getParent();
                        rightRotate(node);
                    }
                    node.getParent().setBlack();
                    node.getGrandfather().setRed();
                    leftRotate(node.getGrandfather());
                }
            }
        }
        root.setBlack();
    }

    public boolean deleteNode(Node node) {
        Node successor = nil, tempNode = nil;

        if (node.isLeftChildFree() || node.isRightChildFree()) {
            successor = node;
        } else {
            successor = node.getSuccessor();
        }

        if (!successor.isLeftChildFree()) {
            tempNode = successor.getLeftChild();
        } else {
            tempNode = successor.getRightChild();
        }
        tempNode.setParent(successor.getParent());

        if (successor.isParentFree()) {
            root = tempNode;
        } else if (successor == successor.getParent().getLeftChild()) {
            successor.getParent().setLeftChild(tempNode);
        } else {
            successor.getParent().setRightChild(tempNode);
        }

        if (successor != node) {
            node.setValue(successor.getValue());
        }

        if (!successor.isRed()) {
            fixAfterDelete(tempNode);
        }

        return true;
    }

    public void fixAfterDelete(Node node) {
        Node tempNode;

        while (node != root && !node.isRed()) {
            if (node == node.getParent().getLeftChild()) {
                tempNode = node.getParent().getRightChild();
                if (tempNode.isRed()) {
                    tempNode.setBlack();
                    node.getParent().setRed();
                    leftRotate(node.getParent());
                    tempNode = node.getParent().getRightChild();
                }
                if (!tempNode.getLeftChild().isRed() && !tempNode.getRightChild().isRed()) {
                    tempNode.setRed();
                    node = node.getParent();
                } else {
                    if (!tempNode.getRightChild().isRed()) {
                        tempNode.getLeftChild().setBlack();
                        tempNode.setRed();
                        rightRotate(tempNode);
                        tempNode = node.getParent().getRightChild();
                    }
                    tempNode.setNodeColor(node.getParent().getNodeColor());
                    node.getParent().setBlack();
                    tempNode.getRightChild().setBlack();
                    leftRotate(node.getParent());
                    node = root;
                }
            } else {
                tempNode = node.getParent().getLeftChild();
                if (tempNode.isRed()) {
                    tempNode.setBlack();
                    node.getParent().setRed();
                    rightRotate(node.getParent());
                    tempNode = node.getParent().getLeftChild();
                }
                if (!tempNode.getLeftChild().isRed() && !tempNode.getRightChild().isRed()) {
                    tempNode.setRed();
                    node = node.getParent();
                } else {
                    if (!tempNode.getLeftChild().isRed()) {
                        tempNode.getRightChild().setBlack();
                        tempNode.setRed();
                        leftRotate(tempNode);
                        tempNode = node.getParent().getLeftChild();
                    }
                    tempNode.setNodeColor(node.getParent().getNodeColor());
                    node.getParent().setBlack();
                    tempNode.getLeftChild().setBlack();
                    rightRotate(node.getParent());
                    node = root;
                }
            }
        }
        node.setBlack();
    }

    public Node findNodeByValue(int value) {
        Node node = root;

        while (true) {
            if (value == node.getValue()) {
                return node;
            }
            if (value >= node.getValue()) {
                if (!node.isRightChildFree()) {
                    node = node.getRightChild();
                } else {
                    System.out.println("Value not found.");
                    break;
                }
            } else {
                if (!node.isLeftChildFree()) {
                    node = node.getLeftChild();
                } else {
                    System.out.println("Value not found.");
                    break;
                }
            }
        }
        return nil;
    }

    public void printTree() {
        Node printedNode = root;
        LinkedList<Node> childs = new LinkedList<Node>();

        while (true) {
            //System.out.println(redBackTree.size());
            System.out.println("Value: " + printedNode.getValue());
            if (printedNode.isParentFree()) {
                System.out.println("Parent: No - Root");
            } else {
                System.out.println("Parent: " + printedNode.getParent().getValue());
            }
            if (printedNode.isLeftChildFree()) {
                System.out.println("Left child: No");
            } else {
                System.out.println("Left child: " + printedNode.getLeftChild().getValue());
                childs.add(printedNode.getLeftChild());
            }
            if (printedNode.isRightChildFree()) {
                System.out.println("Right child: No");
            } else {
                System.out.println("Right child: " + printedNode.getRightChild().getValue());
                childs.add(printedNode.getRightChild());
            }
            if (printedNode.isRed()) {
                System.out.println("Color: Red");
            } else {
                System.out.println("Color: Black");
            }
            System.out.println();
            if (!childs.isEmpty()) {
                printedNode = childs.get(0);
                childs.remove(printedNode);
            } else {
                break;
            }

        }
    }

}
