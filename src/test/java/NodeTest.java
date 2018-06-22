import org.junit.Test;
import static org.junit.Assert.*;

public class NodeTest {

    @Test
    public void shouldIsLeftChildFree() {
        Node node = new Node(10); //value does not matter
        Boolean actual = node.isLeftChildFree();
        assertTrue(actual);

        node.setLeftChild(new Node(9));
        actual = node.isLeftChildFree();
        assertFalse(actual);
    }

    @Test
    public void shouldIsRightChildFree() {
        Node node = new Node(10); //value does not matter
        Boolean actual = node.isLeftChildFree();
        assertTrue(actual);

        node.setRightChild(new Node(11));
        actual = node.isRightChildFree();
        assertFalse(actual);
    }

    @Test
    public void shouldIsParentFree() {
        Node node = new Node(10); //value does not matter
        Boolean actual = node.isParentFree();
        assertTrue(actual);

        node.setParent(new Node(11));
        actual = node.isParentFree();
        assertFalse(actual);
    }

    @Test
    public void shouldSetValue() {
        Node node = new Node();
        node.setValue(10);
        assertEquals(10, 10);
    }

    @Test
    public void shouldGetRightChild() {
        Node node = new Node(10);
        Node actual = node.getRightChild();
        assertNull(actual);

        Node expectedAddedNode = new Node(11);
        node.setRightChild(expectedAddedNode);
        actual = node.getRightChild();
        assertEquals(expectedAddedNode, actual);
    }

    @Test
    public void shouldGetLeftChild() {
        Node node = new Node(10);
        Node actual = node.getLeftChild();
        assertNull(actual);

        Node expectedAddedNode = new Node(9);
        node.setLeftChild(expectedAddedNode);
        actual = node.getLeftChild();
        assertEquals(expectedAddedNode, actual);
    }

    @Test(expected = NullPointerException.class)
    public void shouldGetIncorrectValue() {
        Node node = new Node();
        node.getValue();
    }

    @Test
    public void shouldGetCorrectValue() {
        Node node = new Node(10);
        int actual = node.getValue();
        int expected = 10;
        assertEquals(expected, actual);
    }

    @Test
    public void shouldSetRed() {
        Node node = new Node(10);
        node.setBlack();            // beacase it's red on default
        assertFalse(node.isRed());

        node.setRed();
        assertTrue(node.isRed());
    }

    @Test
    public void shouldSetBlack() {
        Node node = new Node(10);
        node.setBlack();
        assertFalse(node.isRed());
    }

    @Test
    public void shouldGetGrandFather() {
        Node node = new Node(10);
        Node parent = new Node(15);
        Node expectedGrandParent = new Node(20);
        node.setParent(parent);
        parent.setParent(expectedGrandParent);
        Node actual = node.getGrandfather();
        assertEquals(expectedGrandParent, actual);
    }

    @Test
    public void shouldGetUncle() {
        Node node = new Node(10);
        Node parent = new Node(15);
        Node grandParent = new Node(20);
        Node expectedUncle = new Node(25);
        node.setParent(parent);
        parent.setParent(grandParent);
        grandParent.setLeftChild(parent);
        grandParent.setRightChild(expectedUncle);
        Node actual = node.getUncle();
        assertEquals(expectedUncle, actual);
    }

    @Test
    public void shouldGetSuccessor() {
        Node root = new Node(20);
        Node leftChild = new Node(10);
        Node rightChild = new Node(25);
        Node expectedSuccessor = new Node(15);
        root.setLeftChild(leftChild);
        leftChild.setParent(root);
        root.setRightChild(rightChild);
        rightChild.setParent(root);
        leftChild.setRightChild(expectedSuccessor);
        expectedSuccessor.setParent(leftChild);
        Node actual = root.getSuccessor();
        assertEquals(expectedSuccessor, actual);
    }

    @Test
    public void shouldIsRed() {
        Node node = new Node(); // node is red on default
        Boolean actual = node.isRed();
        assertTrue(actual);
    }
}
