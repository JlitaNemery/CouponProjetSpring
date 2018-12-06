package exceptions;

@SuppressWarnings("serial")
public class CouponExpiredException extends Exception {
	public String getMessage() {
		return "coupon expired";
	}
}
