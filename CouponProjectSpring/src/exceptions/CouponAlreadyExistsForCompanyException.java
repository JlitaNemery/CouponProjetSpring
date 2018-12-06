package exceptions;

@SuppressWarnings("serial")
public class CouponAlreadyExistsForCompanyException extends Exception {
	public String getMessage() {
		return "coupon already exists for company";
	}
}
