package binarysearch;


public class Node {
    private Node leftNode;
    private Node rightNode;
    private int value;

    /**
     * Node constructor with initial Node value
     *
     * @param value Initial value for this node
     */
    public Node(int value) {
        leftNode = null;
        rightNode = null;
        this.value = value;
    }

    /**
     * Set left Node pointer for this Node
     *
     * @param Node Left Node for this Node
     */
    public void setLeftNode(Node Node){
        this.leftNode = Node;
    }
    /**
     * Set right Node pointer for this Node
     *
     * @param Node Right Node for this Node
     */
    public void setRightNode(Node Node){
        this.rightNode = Node;
    }
    /**
     * Set value for this Node
     *
     * @param value Value for this Node
     */
    public void setValue(int value){
        this.value = value;
    }

    /**
     * @return Left node for this Node
     */
    public Node getLeftNode() {
        return leftNode;
    }
    /**
     * @return Right node for this Node
     */
    public Node getRightNode() {
        return rightNode;
    }
    /**
     * @return Current value for this Node
     */
    public int getValue() {
        return value;
    }
}
