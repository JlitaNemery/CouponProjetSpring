package exceptions;

@SuppressWarnings("serial")
public class CouponIDAndCouponCompanyIDCantBeChangedException extends Exception {
	public String getMessage() {
		return "coupon id and coupon company id cant be changed";
	}
}
