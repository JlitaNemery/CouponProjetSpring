package exceptions;

@SuppressWarnings("serial")
public class BadInputException extends Exception {

	public String getMessage() {
		return "bad input";
	}
}
