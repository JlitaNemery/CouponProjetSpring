package exceptions;
@SuppressWarnings("serial")
public class CustomerIDCantBeChangedException extends Exception {

	public String getMessage() {
		return "customer ID cant be changed";
	}
}


