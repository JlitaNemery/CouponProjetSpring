package exceptions;

@SuppressWarnings("serial")
public class CompanyCouponDoesntExistException extends Exception {
	public String getMessage() {
		return "company coupon doesn't exist";
	}
}
