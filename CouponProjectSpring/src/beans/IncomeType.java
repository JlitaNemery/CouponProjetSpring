package beans;

public enum IncomeType {

	CUSTOMER_BUY_COUPON,
	COMPANY_NEW_COUPON,
	COMPANY_UPDATE_COUPON;
	
	public static IncomeType getDescription(String description){
		switch(description) {
		case "CUSTOMER_BUY_COUPON": return IncomeType.CUSTOMER_BUY_COUPON;
		case "COMPANY_NEW_COUPON": return IncomeType.COMPANY_NEW_COUPON;
		default: return IncomeType.COMPANY_UPDATE_COUPON;
		}
	}
	
	public static String getString(IncomeType description){
		switch(description) {
		case CUSTOMER_BUY_COUPON: return "CUSTOMER_BUY_COUPON";
		case COMPANY_NEW_COUPON: return "COMPANY_NEW_COUPON";
		default: return "COMPANY_UPDATE_COUPON";
		}
	}
}
