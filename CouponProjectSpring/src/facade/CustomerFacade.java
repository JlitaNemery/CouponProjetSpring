package facade;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import beans.*;
import dbdao.*;
import exceptions.*;

/**
 * 
 * this class handles customer functions
 *
 */
public class CustomerFacade extends ClientFacade {
	public CustomerFacade() {
		companiesDAO = new CompaniesDBDAO();
		customersDAO = new CustomersDBDAO();
		couponsDAO = new CouponsDBDAO();
	}
	
	private int customerID;

	public int getCustomerID() {
		return this.customerID;
	}

	public static LocalDate now = LocalDate.now();
	
	@Override
	public boolean login(String email, String password) throws SQLException, InterruptedException, BadLoginException {
		try {
		ArrayList<Customer> customers = customersDAO.getAllCustomers();
		for(Customer customer:customers) {
			if(customer.getPassword().equals(password)&&customer.getEmail().equals(email)) {
				this.customerID = customer.getId();
				return true;
			}
		}
		
		return false;
		}
		catch(Exception ex) {
			throw new BadLoginException();
		}
	}
	
	public ArrayList<Coupon> getAllCoupons() throws SQLException, InterruptedException{
		return couponsDAO.getAllCoupons();
	}

	public void purchaseCoupon(Coupon coupon) throws SQLException, InterruptedException,
	CouponExpiredException, CouponDepletedException, CouponAlreadyPurchasedException {
		ArrayList<Coupon> coupons = getCustomerCoupons();
		for(Coupon arrayCoupon:coupons) {
			if(arrayCoupon.getId()==coupon.getId()) {

				throw new CouponAlreadyPurchasedException();
			}
		}
		LocalDate now = LocalDate.now();
		if(coupon.getAmount()>0) {
			if(coupon.getEndDate().isAfter(now)) {

				couponsDAO.addCouponPurchase(this.customerID, coupon.getId());
				coupon.setAmount(coupon.getAmount()-1);
				couponsDAO.updateCoupon(coupon);
				return;
			}
			else {

				throw new CouponExpiredException();
			}
		}
		else {

			throw new CouponDepletedException();
		}
	}

	public ArrayList<Coupon> getCustomerCoupons() throws InterruptedException, SQLException{
		return couponsDAO.getCouponByCustomerID(this.customerID);
	}
	
//	---------------------
	public ArrayList<Coupon> getAllAvailableCoupons() throws InterruptedException, SQLException{
		return couponsDAO.getAllAvailableCoupons(this.customerID);
	}

	public ArrayList<Coupon> getCustomerCoupons(Category category) throws SQLException, InterruptedException{
		ArrayList<Coupon> tempCouponArray = couponsDAO.getCouponByCustomerID(this.customerID);
		ArrayList<Coupon> returnedCouponArray = new ArrayList<>();
		for(Coupon coupon:tempCouponArray) {
			if(coupon.getCategory()==category) {
				returnedCouponArray.add(coupon);
			}
		}
		return returnedCouponArray;
	}

	public ArrayList<Coupon> getCustomerCoupons(double maxPrice) throws SQLException, InterruptedException{
		ArrayList<Coupon> tempCouponArray = couponsDAO.getCouponByCustomerID(this.customerID);
		ArrayList<Coupon> returnedCouponArray = new ArrayList<>();
		for(Coupon coupon:tempCouponArray) {
			if(coupon.getPrice()<=maxPrice) {
				returnedCouponArray.add(coupon);
			}
		}
		return returnedCouponArray;
	}
	public Customer getCustomerDetails() throws SQLException, InterruptedException {
		return customersDAO.getOneCustomer(this.customerID);
	}

	public String toString() {
		return"customerFacade";
	}
	
	public Coupon getOneCoupon(int id) throws SQLException, InterruptedException {
		return couponsDAO.getOneCoupon(id);
	}
}
