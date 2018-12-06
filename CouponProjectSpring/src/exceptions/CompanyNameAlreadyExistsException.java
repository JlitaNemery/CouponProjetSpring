package exceptions;

@SuppressWarnings("serial")
public class CompanyNameAlreadyExistsException extends Exception {
	public String getMessage() {
		return "company name already exists";
	}

}
