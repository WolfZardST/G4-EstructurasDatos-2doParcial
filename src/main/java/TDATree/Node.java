
package TDATree;

import java.util.LinkedList;

public class Node<E> {
    
    private E content;
    private LinkedList<Tree<E>> children;
    
    
    // DONA

    public Node(E content, LinkedList<Tree<E>> children) {
        this.content = content;
        this.children = children;
    }

    public E getContent() {
        return content;
    }

    public LinkedList<Tree<E>> getChildren() {
        return children;
    }

    public void setContent(E content) {
        this.content = content;
    }

    public void setChildren(LinkedList<Tree<E>> children) {
        this.children = children;
    }
    
    
}
