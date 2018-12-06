package dailyJob;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import beans.*;
import dbdao.*;
/**
 * this class schedules and configures the daily job 
 * 
 *
 */
public class CouponExpirationDailyJob implements Runnable {

	private CouponsDBDAO couponsDAO = new CouponsDBDAO();
	private boolean quit = false;
	

	public CouponExpirationDailyJob(){
		
	}
	
	public void run(){
		while(!quit) {
			try {
				Thread.sleep(1000);
							
				if(isTimeToOperate(6,0,0)){ 
					LocalDate now = LocalDate.now();
					ArrayList<Coupon> allCoupons = couponsDAO.getAllCoupons();										
				
					for (Coupon coupon : allCoupons) {
						if(now.isAfter(coupon.getEndDate())) {
							couponsDAO.deleteCoupon(coupon.getId());
							couponsDAO.deleteCouponPurchaseByCoupon(coupon.getId());
						}
					}
				}
			} catch (Exception e) { }			
		}			
	}
	
	public void stop(){
		quit = true;		
	}
	

/**
  * this function receives hour,minute,second and compares it to the current time
  */	
	private static boolean isTimeToOperate(int operationHour, int operatingMinute,int operatingSecond) {
		Calendar calendar = Calendar.getInstance();
	    int hour = calendar.get(Calendar.HOUR_OF_DAY);
	    int minute = calendar.get(Calendar.MINUTE);
	    int second = calendar.get(Calendar.SECOND);
	    return(hour==operationHour && minute==operatingMinute && second==operatingSecond);		
	}
	
}
