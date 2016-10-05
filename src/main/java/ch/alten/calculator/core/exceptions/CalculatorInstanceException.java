package ch.alten.calculator.core.exceptions;

/**
 * 
 * @author biagio.dangelo
 *
 * CalculatorInstanceException related to an Error in the instance creation.
 */

public class CalculatorInstanceException extends Exception{

	private static final long serialVersionUID = 8195458874863061777L;

	public CalculatorInstanceException (String message){
		super(message);
	}
	
	public CalculatorInstanceException (Exception e){
		super(e);
	}
}
