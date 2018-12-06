package exceptions;

@SuppressWarnings("serial")
public class CompanyEmailAlreadyExistsExeption extends Exception {
	public String getMessage() {
		return "company Email already exists";
	}
}
