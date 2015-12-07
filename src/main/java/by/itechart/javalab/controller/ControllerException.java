package by.itechart.javalab.controller;


public class ControllerException extends Exception {

	private static final long serialVersionUID = 1L;

	public ControllerException(String message){
		super(message);
	}
	
	public ControllerException(String message, Exception ex){
		super(message, ex);
	}
}
