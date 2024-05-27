package MyHeap;

import java.lang.reflect.Array;
import java.util.Comparator;

public class MyHeap<E extends Comparable<E>> implements HeapInterface<E> {
    private int capacity;
    private Comparator<E> elementComparator;
    private int size;
    private E[] heapArray;

    // Default constructor with default capacity
    public MyHeap() {
        this(10);
    }

    // Constructor with specified capacity
    public MyHeap(int capacity) {
        this(new Comparator<E>() {
            @Override
            public int compare(E o1, E o2) {
                return o1.compareTo(o2);
            }
        }, capacity);
    }

    // Constructor with specified comparator
    public MyHeap(Comparator<E> comparator) {
        this(comparator, 10);
    }

    // Constructor with specified comparator and capacity
    public MyHeap(Comparator<E> comparator, int capacity) {
        this.elementComparator = comparator;
        this.size = 0;
        this.capacity = capacity;
        this.heapArray = (E[]) Array.newInstance(Comparable.class, capacity);
    }

    @Override
    public boolean add(E e) {
        ensureCapacity();
        this.heapArray[size] = e;
        size++;
        heapifyUp();
        return true;
    }

    @Override
    public void clear() {
        this.size = 0;
        this.heapArray = (E[]) Array.newInstance(Comparable.class, capacity);
    }

    @Override
    public boolean contains(E e) {
        for (int i = 0; i < size; i++) {
            if (heapArray[i].equals(e)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public E poll() {
        if (this.size == 0) {
            throw new EmptyHeapException();
        }
        E elementAtZero = this.heapArray[0];
        this.heapArray[0] = this.heapArray[size - 1];
        size--;
        heapifyDown();
        return elementAtZero;
    }

    @Override
    public E peek() {
        if (this.size == 0) {
            throw new EmptyHeapException();
        }
        return this.heapArray[0];
    }

    @Override
    public boolean remove(E e) {
        for (int i = 0; i < size; i++) {
            if (heapArray[i].equals(e)) {
                heapArray[i] = heapArray[size - 1];
                size--;
                heapifyDown();
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty(){
        return size == 0;
    }

    private int leftNodeIndex(int root) {
        return 2 * root + 1;
    }

    private int rightNodeIndex(int root) {
        return 2 * root + 2;
    }

    private int root(int childIndex) {
        return (childIndex - 1) / 2;
    }

    private void swapElements(int index1, int index2) {
        E temp = this.heapArray[index1];
        this.heapArray[index1] = this.heapArray[index2];
        this.heapArray[index2] = temp;
    }

    private void ensureCapacity() {
        if (this.size == this.heapArray.length) {
            capacity = capacity * 2;
            E[] newArray = (E[]) Array.newInstance(heapArray.getClass().getComponentType(), capacity);
            System.arraycopy(this.heapArray, 0, newArray, 0, size);
            this.heapArray = newArray;
        }
    }

    private void heapifyDown() {
        int root = 0;
        while (leftNodeIndex(root) < size) {
            int smallLeftChild = leftNodeIndex(root);
            if (rightNodeIndex(root) < size && this.elementComparator.compare(this.heapArray[rightNodeIndex(root)], this.heapArray[leftNodeIndex(root)]) < 0) {
                smallLeftChild = rightNodeIndex(root);
            }
            if (this.elementComparator.compare(this.heapArray[root], this.heapArray[smallLeftChild]) < 0) {
                break;
            } else {
                swapElements(root, smallLeftChild);
            }
            root = smallLeftChild;
        }
    }

    private void heapifyUp() {
        int lastElement = size - 1;
        while (root(lastElement) >= 0 && this.elementComparator.compare(this.heapArray[lastElement], this.heapArray[root(lastElement)]) < 0) {
            swapElements(root(lastElement), lastElement);
            lastElement = root(lastElement);
        }
    }


}
