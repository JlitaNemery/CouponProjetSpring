package facade;
import java.sql.SQLException;
import java.util.ArrayList;
import beans.*;
import dbdao.*;
import exceptions.*;

/**
 * 
 * this class handles company functions
 *
 */
public class CompanyFacade extends ClientFacade{
	public CompanyFacade() {
		companiesDAO = new CompaniesDBDAO();
		customersDAO = new CustomersDBDAO();
		couponsDAO = new CouponsDBDAO();
	}

	private int companyID;

	public int getCompanyID() {
		return this.companyID;
	}

	@Override
	public boolean login(String email, String password) throws SQLException,
	InterruptedException, BadLoginException {
		try {
			ArrayList<Company> companys = companiesDAO.getAllCompanies();
			for(Company company:companys) {
				if((company.getPassword()).equals(password)&&company.getEmail().equals(email)){
					this.companyID = company.getId();
					return true;
				}
			}
			return false;
		}
		catch(Exception ex) {
			throw new BadLoginException();
		}
	}

	public Coupon addCoupon(Coupon coupon) throws SQLException,
	InterruptedException, CouponAlreadyExistsForCompanyException {
		ArrayList<Coupon> couponArr =
				couponsDAO.getAllCouponsFromCompany(this.companyID);
		for(Coupon couponFromArr:couponArr) {
			if((couponFromArr.getTitle()).equals(coupon.getTitle())) {
				throw new CouponAlreadyExistsForCompanyException();
			}			
		}
		coupon.setCompanyId(this.companyID);
		int couponID = couponsDAO.addCoupon(coupon);
		coupon.setId(couponID);
		return coupon;
	}

	public void updateCoupon (Coupon coupon) throws SQLException, InterruptedException, 
	CouponIDAndCouponCompanyIDCantBeChangedException, CouponAlreadyExistsForCompanyException {
		ArrayList<Coupon> coupons = couponsDAO.getAllCouponsFromCompany(this.companyID);
		for(Coupon arrayCoupon:coupons) {
			if(arrayCoupon.getTitle().equals(coupon.getTitle()) && arrayCoupon.getId()!=(coupon.getId())) {
				throw new CouponAlreadyExistsForCompanyException();
			}
		}
		coupon.setCompanyId(this.companyID);
		Coupon tempCoupon = couponsDAO.getOneCoupon(coupon.getId());
		if((tempCoupon.getCompanyId()==coupon.getCompanyId()) && (tempCoupon.getId()==coupon.getId())){
			couponsDAO.updateCoupon(coupon);
		}
		else {
			throw new CouponIDAndCouponCompanyIDCantBeChangedException();
		}
	}

	public void deleteCoupon (int couponID) throws SQLException, 
	InterruptedException {
		ArrayList<Coupon> allCompanyCoupons = couponsDAO.getAllCouponsFromCompany(this.companyID);
		for(Coupon tempCoupon:allCompanyCoupons) {
			if(tempCoupon.getId() == couponID) {
				couponsDAO.deleteCoupon(couponID);
				couponsDAO.deleteCouponPurchaseByCoupon(couponID);
			}
		}
	}

	public ArrayList<Coupon> getCompanyCoupons() throws SQLException, InterruptedException{
		return couponsDAO.getAllCouponsFromCompany(this.companyID);
	}
	public Coupon getOneCoupon(int couponId) throws SQLException, InterruptedException, CompanyCouponDoesntExistException{
		ArrayList<Coupon> couponArr = couponsDAO.getAllCouponsFromCompany(this.companyID);
		for(Coupon coupon:couponArr) {
			if (coupon.getId()== couponId){
				return coupon;
			}
		}
		throw new CompanyCouponDoesntExistException();
	}

	public ArrayList<Coupon> getCompanyCoupons(Category category) throws SQLException, InterruptedException{
		ArrayList<Coupon> tempCouponArray = couponsDAO.getAllCouponsFromCompany(this.companyID);
		ArrayList<Coupon> returnedCouponArray = new ArrayList<>();
		for(Coupon coupon:tempCouponArray) {
			if(coupon.getCategory()==category) {
				returnedCouponArray.add(coupon);
			}
		}
		return returnedCouponArray;
	}

	public ArrayList<Coupon> getCompanyCoupons(double maxPrice) throws SQLException, InterruptedException{
		ArrayList<Coupon> tempCouponArray = couponsDAO.getAllCouponsFromCompany(this.companyID);
		ArrayList<Coupon> returnedCouponArray = new ArrayList<>();
		for(Coupon coupon:tempCouponArray) {
			if(coupon.getPrice()<=maxPrice) {
				returnedCouponArray.add(coupon);
			}
		}
		return returnedCouponArray;
	}

	public Company getCompanyDetails() throws SQLException, InterruptedException {
		return companiesDAO.getOneCompany(this.companyID);
	}

	public String toString() {
		return "companyFacade";
	}

}
