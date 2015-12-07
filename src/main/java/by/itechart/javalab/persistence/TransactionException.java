package by.itechart.javalab.persistence;

/**
 * Created by Dmitriy on 21.11.2015.
 */
public class TransactionException extends Exception {
    public TransactionException(String message){
        super(message);
    }

    public TransactionException(String message, Exception ex){
        super(message, ex);
    }
}
