package exceptions;

@SuppressWarnings("serial")
public class CompanyNameCantBeChangedException extends Exception {
	public String getMessage() {
		return "company name can't be changed";
	}
}
