package binarysearch;

public class BinarySearchTree {

    public Node rootNode;

    /**
     * Create new Node with value and insert into this Tree
     *
     * @param value Initial value for new Node
     */
    public void insertValue(int value) {
        insertNode(new Node(value));
    }

    /**
     * Insert new Node into this Tree
     *
     * @param newNode Node to be inserted into this Tree
     */
    public void insertNode(Node newNode){
        if (rootNode == null) {
            rootNode = new Node(newNode.getValue());
        } else {
            insertNode(rootNode, newNode);
        }
    }

    /**
     * Search this tree for a value
     *
     * @param value Value to be searched in this Tree
     * @return True if this tree contains value
     */
    public boolean containsValue(int value){
        if (getNode(rootNode, value) == null) {
            return false;
        }
        return true;
    }

    /**
     * Get Node in this Tree that contains value
     *
     * @param value Value to be searched within this Tree
     * @return Node that matches value or null if no match found
     */
    public Node getNode(Node currentNode, int value){
        if (currentNode == null)
            return null;
        if (currentNode.getValue() == value)
            return currentNode;
        if (value < currentNode.getValue())
            return getNode(currentNode.getLeftNode(), value);
        return getNode(currentNode.getRightNode(), value);
    }

    /**
     * Insert a new node into this tree
     *
     * @param currentNode Current node we are comparing to newNode
     * @param newNode     New node that is to be inserted
     */
    private void insertNode(Node currentNode, Node newNode) {
        if (newNode.getValue() < currentNode.getValue()) {
            if (currentNode.getLeftNode() == null)
                currentNode.setLeftNode(newNode);
            else
                insertNode(currentNode.getLeftNode(), newNode);
        }
        if (newNode.getValue() > currentNode.getValue()) {
            if (currentNode.getRightNode() == null)
                currentNode.setRightNode(newNode);
            else
                insertNode(currentNode.getRightNode(), newNode);
        }
    }

    private int minValue(Node root) {
        if (root.getLeftNode() == null)
            return root.getValue();
        else
            return minValue(root.getLeftNode());
    }

    private boolean deleteNode(int value,Node currentNode,Node parentNode){
        if (value < currentNode.getValue()){
            if (currentNode.getLeftNode() != null){
                return deleteNode(value,currentNode.getLeftNode(),currentNode);
            }else {
                return false;
            }
        }else if (value > currentNode.getValue()){
            if (currentNode.getRightNode() != null){
                return deleteNode(value,currentNode.getRightNode(),currentNode);
            }else{
                return false;
            }
        }else{
            if (currentNode.getLeftNode() != null && currentNode.getRightNode() != null) {
                int minValue = minValue(currentNode.getRightNode());
                currentNode.setValue(minValue);
                deleteNode(minValue, currentNode.getRightNode(),currentNode);
            } else if (parentNode.getLeftNode() == currentNode) {
                parentNode.setLeftNode(currentNode.getLeftNode());
            } else if (parentNode.getRightNode() == currentNode) {
                parentNode.setRightNode(currentNode.getRightNode());
            }
            return true;
        }
    }

    // A utility function to print preorder traversal of the tree.
    // The function also prints height of every avl.AVLNode
    void preOrder(Node node) {
        if (node != null) {
            System.out.print(node.getValue() + " ");
            preOrder(node.getLeftNode());
            preOrder(node.getRightNode());
        }
    }

    public static void main(String[] args) {
        BinarySearchTree myTree = new BinarySearchTree();
        myTree.insertValue(10);
        myTree.insertValue(13);
        myTree.insertValue(9);
        myTree.insertValue(20);
        myTree.insertValue(12);
        myTree.insertValue(15);
        myTree.deleteNode(10,myTree.rootNode,myTree.rootNode);
        myTree.preOrder(myTree.rootNode);
        /* The BinarySearch Tree
               10
              /  \
             9    13
                 /  \
                12 20
                   /
                  15
         */
    }
}
