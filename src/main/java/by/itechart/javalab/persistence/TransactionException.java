package by.itechart.javalab.persistence;


public class TransactionException extends Exception {
    public TransactionException(String message){
        super(message);
    }

    public TransactionException(String message, Exception ex){
        super(message, ex);
    }
}
