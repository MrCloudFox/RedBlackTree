import static org.junit.Assert.*;
import org.junit.Test;


//@RunWith(Parameterized.class)
public class RedBlackTreeTest {
    private RedBlackTree redBlackTree;

    public void precondition () {
        RedBlackTree redBlackTree = new RedBlackTree(20);
        redBlackTree.insertNode(new Node(10));
        redBlackTree.insertNode(new Node(30));
        redBlackTree.insertNode(new Node(5));
        redBlackTree.insertNode(new Node(13));
        redBlackTree.insertNode(new Node(25));
        redBlackTree.insertNode(new Node(35));
        redBlackTree.insertNode(new Node(12));
        redBlackTree.insertNode(new Node(15));
        //rbt.insertNode(new Node(16)); //running rebuild tree
        redBlackTree.insertNode(new Node(22));
        redBlackTree.insertNode(new Node(27));
        redBlackTree.insertNode(new Node(33));
        redBlackTree.insertNode(new Node(37));
        redBlackTree.insertNode(new Node(3));
        redBlackTree.insertNode(new Node(7));
        redBlackTree.insertNode(new Node(2));

        this.redBlackTree = redBlackTree;
    }

//    @Parameterized.Parameters
//    public static RedBlackTree tree() {
//        return redBlackTree;
//    }

    @Test
    public void shouldGetRoot() {
        precondition();
        Node node = new Node(20);
        Node rootNode = redBlackTree.getRoot();
        int expectedValOfRoot = node.getValue();
        int actual = rootNode.getValue();
        boolean expectedColor = rootNode.isRed();
        assertEquals(expectedValOfRoot, actual);
        assertFalse(expectedColor);

    }

    @Test
    public void shouldLeftRotate() {
        precondition();
        redBlackTree.insertNode(new Node(38));
        redBlackTree.insertNode(new Node(39));
        redBlackTree.printTree();
        assertEquals(35, redBlackTree.findNodeByValue(38).getParent().getValue());
        assertEquals(38, redBlackTree.findNodeByValue(39).getParent().getValue());
        assertEquals(37, redBlackTree.findNodeByValue(38).getLeftChild().getValue());
        assertEquals(39, redBlackTree.findNodeByValue(38).getRightChild().getValue());
        assertTrue(redBlackTree.findNodeByValue(37).isRed());
        assertFalse(redBlackTree.findNodeByValue(38).isRed());
        assertTrue(redBlackTree.findNodeByValue(39).isRed());
    }

    @Test
    public void shouldRightRotate() {
        precondition();
        redBlackTree.insertNode(new Node(1));                                      // in addition tested rightRotate()
        assertEquals(2, redBlackTree.findNodeByValue(1).getParent().getValue());
        assertEquals(2, redBlackTree.findNodeByValue(3).getParent().getValue());
        assertEquals(1, redBlackTree.findNodeByValue(2).getLeftChild().getValue());
        assertEquals(3, redBlackTree.findNodeByValue(2).getRightChild().getValue());
        assertTrue(redBlackTree.findNodeByValue(1).isRed());
        assertTrue(redBlackTree.findNodeByValue(3).isRed());
        assertFalse(redBlackTree.findNodeByValue(2).isRed());
    }

    @Test
    public void shouldInsertWithFix() {
        precondition();
        redBlackTree.insertNode(new Node(16));
        //assertEquals(expectedRedBlackTree, redBlackTree);
        //assertEquals(newNode, redBlackTree.findNodeByValue(16));                        // don't actual because it's compared just pointers
        assertEquals(15, redBlackTree.findNodeByValue(16).getParent().getValue());
        assertTrue(redBlackTree.findNodeByValue(16).isRed());
        assertFalse(redBlackTree.findNodeByValue(15).isRed());;
        assertTrue(redBlackTree.findNodeByValue(13).isRed());
    }

    @Test
    public void shouldDeleteWithFix() {
        precondition();
        redBlackTree.deleteNode(redBlackTree.findNodeByValue(10));

        assertEquals(20, redBlackTree.findNodeByValue(7).getParent().getValue());
        assertEquals(13, redBlackTree.findNodeByValue(7).getRightChild().getValue());
        assertEquals(5, redBlackTree.findNodeByValue(7).getLeftChild().getValue());
        assertEquals(7, redBlackTree.findNodeByValue(5).getParent().getValue());
        assertEquals(5, redBlackTree.findNodeByValue(3).getParent().getValue());
        assertEquals(7, redBlackTree.getRoot().getLeftChild().getValue());
        assertFalse(redBlackTree.findNodeByValue(3).isRed());
        assertTrue(redBlackTree.findNodeByValue(5).isRed());
        assertTrue(redBlackTree.findNodeByValue(2).isRed());

    }
}