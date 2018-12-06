package dbdao;

import java.sql.SQLException;
import java.util.ArrayList;

import beans.*;

public interface CouponsDAO {
	 int addCoupon (Coupon coupon) throws SQLException, InterruptedException;
	 void updateCoupon(Coupon coupon) throws SQLException, InterruptedException;
	 void deleteCoupon(int couponID) throws SQLException, InterruptedException;
	 ArrayList<Coupon> getAllCoupons() throws SQLException, InterruptedException;
	 Coupon getOneCoupon(int couponID) throws SQLException, InterruptedException;
	 void addCouponPurchase(int customerID, int CouponID) throws SQLException, InterruptedException;
	 void deleteCouponPurchase(int customerID, int couponID) throws SQLException, InterruptedException;
	 boolean isCouponExists(int couponID) throws SQLException, InterruptedException;
	 void deleteCouponPurchaseByCoupon(int couponID) throws SQLException, InterruptedException;
	 void deleteCouponPurchaseByCustomer(int customerID) throws SQLException, InterruptedException;
	 ArrayList<Coupon> getCouponByCustomerID(int customerID) throws InterruptedException, SQLException;
	 ArrayList<Coupon> getAllCouponsFromCompany(int companyID) throws SQLException, InterruptedException;
	 int getCouponTypeID(Category couponType) throws SQLException, InterruptedException;
	 Category getCouponType(int couponTypeID) throws SQLException, InterruptedException;
	 ArrayList<Coupon> getAllAvailableCoupons(int customerID) throws SQLException, InterruptedException;
	

}
