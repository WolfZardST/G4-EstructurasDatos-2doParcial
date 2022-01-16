
package TDAHeap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Heap<E> {
    
    private final Comparator<E> cmp;
    private E[] elements;
    private int capacity;
    private int effectiveSize;
    private final boolean isMax;
    
    public Heap(Comparator<E> cmp, boolean isMax) {
        this(cmp, isMax, 18);
    }
    
    public Heap(Comparator<E> cmp, boolean isMax, int capacity) {
        this.cmp = cmp;
        this.isMax = isMax;
        this.effectiveSize = 0;
        this.capacity = capacity;
        this.elements = (E[]) new Object[capacity];
    }
    
    private int getParent(int index) {
        return (index - 1)/2;
    }
    
    private int getLeftChild(int index) {
        return (index * 2) + 1;
    }
    
    private int getRightChild(int index) {
        return (index * 2) + 2;
    }
    
    private boolean isFull() {
        return effectiveSize == capacity;
    }
    
    private void addCapacity() {
        capacity = capacity * 2;
        
        E[] new_array = (E[]) new Object[capacity];
        System.arraycopy(elements, 0, new_array, 0, elements.length);
        elements = new_array;
    }
    
    private int getMaxElementIndex(int root_i) {
        
        int result_i = root_i;
        
        int Lchild_i = getLeftChild(root_i);
        int Rchild_i = getRightChild(root_i);
        
        if(isValid(Lchild_i) && cmp.compare(elements[Lchild_i], elements[result_i]) >= 0) {
            result_i = Lchild_i;
        }
            
        if(isValid(Rchild_i) && cmp.compare(elements[Rchild_i], elements[result_i]) >= 0) {
            result_i = Rchild_i;
        }
        
        return result_i;
    }
    
    private int getMinElementIndex(int root_i) {
        
        int result_i = root_i;
        
        int Lchild_i = getLeftChild(root_i);
        int Rchild_i = getRightChild(root_i);
        
        if(isValid(Lchild_i) && cmp.compare(elements[Lchild_i], elements[result_i]) <= 0) {
            result_i = Lchild_i;
        }
            
        if(isValid(Rchild_i) && cmp.compare(elements[Rchild_i], elements[result_i]) <= 0) {
            result_i = Rchild_i;
        }
            
        return result_i;
    }
    
    private boolean isValid(int index) {
        return index <= effectiveSize - 1;
    }
    
    private void swap(int index_1, int index_2) {
        E element_1 = elements[index_1];
        E element_2 = elements[index_2];
        
        elements[index_1] = element_2;
        elements[index_2] = element_1;
    }
    
    private void adjust(int root_i) {
        
        int new_root_i = (isMax) ? getMaxElementIndex(root_i) : getMinElementIndex(root_i);
        
        if(root_i != new_root_i){
            
            swap(root_i, new_root_i);
            adjust(new_root_i);
        }
    }
    
    public boolean isEmpty() {
        return effectiveSize == 0;
    }
    
    public void clear() {
        elements = (E[]) new Object[capacity];
        effectiveSize = 0;
    }
    
    public void buildFromArray(E[] elements) {
        
        boolean containsNullElement = Arrays.stream(elements)
                                            .filter(e -> e == null)
                                            .findFirst()
                                            .isPresent();
        
        if(containsNullElement) return;
        
        this.elements = elements;
        capacity = effectiveSize = elements.length;
        
        for(int i = effectiveSize/2 - 1; i >= 0; i--) {
            adjust(i);
        }
    }
    
    public boolean offer(E e) {
        
        if(e == null) return false;
        
        if(isFull()) addCapacity();
        
        elements[effectiveSize++] = e;
        
        int root_i = effectiveSize - 1;
        
        if(isMax) {
            
            int parent_i = getParent(root_i);
            while(cmp.compare(elements[parent_i], elements[root_i]) < 0) {
                swap(root_i, parent_i);
                
                root_i = parent_i;
                parent_i = getParent(root_i);
            }
            
        } else {
            
            int parent_i = getParent(root_i);
            while(cmp.compare(elements[parent_i], elements[root_i]) > 0) {
                swap(root_i, parent_i);
                
                root_i = parent_i;
                parent_i = getParent(root_i);
            }
            
        }
        
        return true;
    }
    
    public E poll() {
        if(isEmpty()) return null;
        
        E result = elements[0];
        elements[0] = elements[--effectiveSize];
        
        adjust(0);
                
        return result;
    }
    
    public void print() {
        boolean break_for = false;
        
        System.out.println("\n("+elements[0]+")");
        for(int i = 1; !break_for; i += i*2) {
            
            for(int j = i; j < i + i*2; j++){
                
                if(!isValid(j)) {
                    break_for = true;
                    System.out.println("");
                    break;
                }
                String position = (j % 2 == 0) ? "R": "L";
                System.out.print("("+elements[j]+"):"+position+" ");
            }
            System.out.println("");
        }
    }
    
    public E[] toSortedArray() {
        
        E[] result = (E[]) new Object[effectiveSize];
        if(isEmpty()) return result;
        
        Heap<E> clone = new Heap(cmp, isMax, effectiveSize - 1);
        E[] elements_clone = (E[]) new Object[effectiveSize];
        
        System.arraycopy(elements, 0, elements_clone, 0, effectiveSize);
        
        clone.buildFromArray(elements_clone);
        
        for(int i = 0; i < effectiveSize; i++) {
            result[i] = clone.poll();
        }
        
        return result;
    }
    
    public List<E> toSortedList() {
        return Arrays.asList(toSortedArray());
    }

    @Override
    public String toString() {
        for (E element: elements){
            if(element != null) System.out.println(element);
        }
        return "Heap{" + "elements=" + elements + '}';
    }
    
    
    
}

