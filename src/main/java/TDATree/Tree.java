
package TDATree;

public class Tree<E> {
    
    private Node<E> root;
    
    // DONA

    public Tree(Node<E> root) {
        this.root = root;
    }
    
    public Tree(E rootElement) {
        this( new Node(rootElement) );
    }

    public Tree() {}

    public Node<E> getRoot() {
        return root;
    }

    public void setRoot(Node<E> root) {
        this.root = root;
    }
    
    public boolean isEmpty() {
        return root == null;
    }    
    
    public boolean isLeaf() {
        return root.getChildren().isEmpty();
    }
    
    public void addChild(E element){
        if(element != null) root.getChildren().add( new Tree(element) );
    }
    
    public void addChild(Tree<E> tree){
        if(tree != null && !tree.isEmpty()) root.getChildren().add( tree );
    }
    
}
