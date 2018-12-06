package exceptions;

@SuppressWarnings("serial")
public class BadLoginException extends Exception {

	public String getMessage() {
		return "bad login";
	}

}
