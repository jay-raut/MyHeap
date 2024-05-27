package MyHeap;

public interface HeapInterface<E> {
    boolean add(E e);

    void clear();

    boolean contains(E e);

    E poll();

    E peek();

    boolean remove(E e);

    int size();
    boolean isEmpty();

}
