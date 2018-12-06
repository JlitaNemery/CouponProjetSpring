package exceptions;

@SuppressWarnings("serial")
public class CompanyDoesntExistException extends Exception {

	public String getMessage() {
		return "company doesn't exist";
	}

}
