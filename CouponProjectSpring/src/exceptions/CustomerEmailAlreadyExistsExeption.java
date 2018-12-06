package exceptions;

@SuppressWarnings("serial")
public class CustomerEmailAlreadyExistsExeption extends Exception {
	public String getMessage() {
		return "customer email already exists";
	}
}
