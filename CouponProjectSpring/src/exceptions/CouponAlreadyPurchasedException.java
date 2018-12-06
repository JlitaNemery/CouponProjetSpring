package exceptions;
@SuppressWarnings("serial")
public class CouponAlreadyPurchasedException extends Exception {

	public String getMessage() {
		return "coupon already purchased";
	}
}


