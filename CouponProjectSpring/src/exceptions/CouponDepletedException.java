package exceptions;

@SuppressWarnings("serial")
public class CouponDepletedException extends Exception {
	public String getMessage() {
		return "coupon depleted";
	}
}
