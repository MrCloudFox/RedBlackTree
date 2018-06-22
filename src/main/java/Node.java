
enum NodeColor {
    RED,
    BLACK
}

public class Node {
    private Integer value;
    private Node parent;
    private Node leftChild;
    private Node rightChild;
    private NodeColor nodeColor;
    //private Node RedBlackTree.getNil();

    public Node() {
        this.value = null;
        this.parent = null;
        this.leftChild = null;
        this.rightChild = null;
        this.nodeColor = NodeColor.RED;
    }

    public Node(int value) {
        this.value = value;
        this.parent = null;
        this.leftChild = RedBlackTree.getNil();
        this.rightChild = RedBlackTree.getNil();
        this.nodeColor = NodeColor.RED;
    }


    public boolean isLeftChildFree() {
        return leftChild == RedBlackTree.getNil() || leftChild == null;
    }

    public boolean isRightChildFree() {
        return rightChild == RedBlackTree.getNil() || rightChild == null;
    }

    public boolean isParentFree() {
        return parent == RedBlackTree.getNil() || parent == null;
    }

    public boolean isUncleFree() {
        return getUncle() == RedBlackTree.getNil() || getUncle() == null;
    }

    public void setValue(int value) throws NullPointerException {
        this.value = value;
    }

    public void setBlack() {
        nodeColor = NodeColor.BLACK;
    }

    public void setRed() {
        nodeColor = NodeColor.RED;
    }

    public Node getGrandfather() {
        if (parent != null) {
            return parent.getParent();
        }
        return null;
    }

    public Node getUncle() {
        Node grandfather = getGrandfather();

        if (grandfather != null) {
            if (grandfather.leftChild == parent) {
                return grandfather.rightChild;
            } else {
                return grandfather.leftChild;
            }
        }
        return null;
    }

    public Node getSuccessor() {
        Node nearestNode;

        if (!this.isLeftChildFree()) {
            nearestNode = this.getLeftChild();
            while (!nearestNode.isRightChildFree())
                nearestNode = nearestNode.getRightChild();
            return nearestNode;
        }

        Node node = this;
        nearestNode = node.getParent();
        while (nearestNode != RedBlackTree.getNil() && node == nearestNode.getLeftChild()) {
            node = nearestNode;
            nearestNode = node.getParent();
        }
        return nearestNode;
    }

    public int getValue() {
        return value;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    public NodeColor getNodeColor() {
        return nodeColor;
    }

    public void setNodeColor(NodeColor nodeColor) {
        this.nodeColor = nodeColor;
    }

    public boolean isRed() {
        return nodeColor == NodeColor.RED;
    }
}