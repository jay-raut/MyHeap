package MyHeap;

public class EmptyHeapException extends RuntimeException{
    public EmptyHeapException (String message){
        super(message);
    }

    public EmptyHeapException(){
        super();
    }
}
