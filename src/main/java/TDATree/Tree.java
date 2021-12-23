
package TDATree;

public class Tree<E> {
    
    private Node<E> root;
    
    // DONA

    public Tree(Node<E> root) {
        this.root = root;
    }

    public Tree() {
    }

    public Node<E> getRoot() {
        return root;
    }

    public void setRoot(Node<E> root) {
        this.root = root;
    }
    
    
}
