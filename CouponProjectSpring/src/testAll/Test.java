package testAll;

import java.time.LocalDate;
import java.util.ArrayList;

import beans.*;
import buildb.*;
import dailyJob.*;
import dbdao.*;
import facade.*;
import login.*;

/**
 * this class is for testing all the program
 * 
 */

public class Test {
	
	
	/**
	 * this function tests all the program
	 */
	
	public static void testAll() {

		CouponExpirationDailyJob dailyJob = new CouponExpirationDailyJob();	
		try {

			Thread dailyJobThread = new Thread(dailyJob);
			dailyJobThread.start();



			CompaniesDAO companiesDAO = new CompaniesDBDAO();
			CustomersDAO customersDAO = new CustomersDBDAO();
			CouponsDAO couponsDAO = new CouponsDBDAO();
		
			DBBuilding.buildCompaniesDB();
			DBBuilding.buildCouponsDB();
			DBBuilding.buildCustomersDB();
			DBBuilding.buildCustomerVsCouponsDB();
			DBBuilding.buildCATEGORIESDB();
			
			Class.forName("org.apache.derby.jdbc.ClientDriver");

			LocalDate startDate = LocalDate.parse("2011-12-24");
			LocalDate endDate = LocalDate.parse("2020-12-24");

			Coupon firstCoupon = new Coupon(Category.ELECTRICITY, "washer", "washer discount", startDate, endDate, 10, 500.90, "washing machine");
			Coupon secondCoupon = new Coupon(Category.FASHION, "dress", "dress discount", startDate, endDate, 10, 29.90, "dress");
			Coupon thirdCoupon = new Coupon(3, Category.FOOD, "cookies", "cookies discount", startDate, endDate, 10, 3.90, "cookies");
			Coupon fourthCoupon = new Coupon(2, Category.GARDEN, "orchid", "orchid discount", startDate, endDate, 10, 13.50, "orchid");
			Coupon fifthCoupon = new Coupon(1, Category.HOME_DEPOT, "drill", "drill discount", startDate, endDate, 10, 19.90, "drill");
			Coupon sixthCoupon = new Coupon(1, Category.MUSICAL_INSTRUMENTS, "guitar", "guitar discount", startDate, endDate, 10, 356.90, "guitar");
			Coupon seventhCoupon = new Coupon(1, Category.RESTAURANT, "dinner", "dinner discount", startDate, endDate, 10, 35.75, "dinner");
			Coupon eighthCoupon = new Coupon(1, Category.TOYS, "lego", "lego discount", startDate, endDate, 10, 14.90, "lego");
			Coupon ninethCoupon = new Coupon(1, Category.VACATION, "airline", "flight discount", startDate, endDate, 10, 78.90, "airline");
			
			


			
			ArrayList<Coupon> couponArr = new ArrayList<>();
			ArrayList<Coupon> secondCouponArr = new ArrayList<>();
			ArrayList<Coupon> thirdCouponArr = new ArrayList<>();
			
			thirdCouponArr.add(fifthCoupon);
			thirdCouponArr.add(sixthCoupon);
			thirdCouponArr.add(seventhCoupon);
			thirdCouponArr.add(eighthCoupon);
			
			ArrayList<Coupon> EmptyCouponArr = new ArrayList<>();
			couponArr.add(firstCoupon);
			couponArr.add(secondCoupon);
			secondCouponArr.add(thirdCoupon);
			
			Company company = new Company("google", "google@gmail.com", "goog", couponArr);
			Company company1 = new Company("yooo", "itzik@gmail.com", "log", EmptyCouponArr);
			Company company2 = new Company("yoho", "shik@gmail.com", "login", secondCouponArr);

			Customer customer = new Customer("shiko", "benDavid", "shiko@gmail.com", "2456", EmptyCouponArr);
			Customer customer1 = new Customer("roy", "haik", "eitan@gmail.com", "2468", secondCouponArr);
			Customer customer2 = new Customer("eitan", "nessing", "roy@gmail.com", "2469", EmptyCouponArr);

			System.out.println("adding");
			System.out.println();
			AdminFacade adminFacade = (AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin",ClientType.ADMINISTRATOR);			
			adminFacade.addCompany(company);
			adminFacade.addCompany(company1);
			adminFacade.addCompany(company2);
			CompanyFacade companyFacade = (CompanyFacade) LoginManager.getInstance().login("google@gmail.com", "goog", ClientType.COMPANY);
						
			companyFacade.addCoupon(firstCoupon);
			companyFacade.addCoupon(secondCoupon);
			
			couponsDAO.addCoupon(thirdCoupon);
			couponsDAO.addCoupon(fourthCoupon);
			couponsDAO.addCoupon(fifthCoupon);
			couponsDAO.addCoupon(sixthCoupon);
			couponsDAO.addCoupon(seventhCoupon);
			couponsDAO.addCoupon(eighthCoupon);
			couponsDAO.addCoupon(ninethCoupon);
			
			adminFacade.addCustomer(customer);
			adminFacade.addCustomer(customer1);
			adminFacade.addCustomer(customer2);
			
			CustomerFacade customerFacade = (CustomerFacade) LoginManager.getInstance().login("eitan@gmail.com", "2468", ClientType.CUSTOMER);
			System.out.println("customer 1 id is(needs to be 2): " + customer1.getId());
			System.out.println("first coupon id is(needs to  be 1): " + firstCoupon.getId());
			Coupon couponTest = couponsDAO.getOneCoupon(1);
			
			System.out.println("all company coupons: ");
			couponTest.display();
			ArrayList<Coupon> companyCouponsArray = companyFacade.getCompanyCoupons();
			for(Coupon tempCoupon:companyCouponsArray) {
				tempCoupon.display();
			}

			customerFacade.purchaseCoupon(couponsDAO.getOneCoupon(1));
			customerFacade.purchaseCoupon(couponsDAO.getOneCoupon(2));
			
			System.out.println();
			System.out.println("getting all coupons");
			ArrayList<Coupon>couponsArray = couponsDAO.getAllCoupons();
			for(Coupon tempCoupon:couponsArray) {
				tempCoupon.display();
			}
			
			System.out.println();
			System.out.println("getting all customers");
			ArrayList<Customer> customersArray = adminFacade.getAllCustomers();
			for(Customer tempCustomer:customersArray) {
				tempCustomer.display();
			}
			System.out.println();
			System.out.println("getting all companies");
			ArrayList<Company> companiesArray = adminFacade.getAllCompanies();
			for(Company tempCompany:companiesArray) {
				tempCompany.display();
			}
			
			System.out.println();
			System.out.println("getting all company coupons:");
			ArrayList<Coupon> companyCoupons = companyFacade.getCompanyCoupons();
			for(Coupon tempCoupon: companyCoupons) {
				tempCoupon.display();
			}
			System.out.println();
			System.out.println("getting all company coupons under certain price:");
			ArrayList<Coupon> companyCouponsUnderCertainPrice = companyFacade.getCompanyCoupons(30.0);
			for(Coupon tempCoupon: companyCouponsUnderCertainPrice) {
				tempCoupon.display();
			}
			System.out.println();
			System.out.println("getting all company couponsunder certain category:");
			ArrayList<Coupon> companyCouponsUnderCertainCategory = companyFacade.getCompanyCoupons(Category.ELECTRICITY);
			for(Coupon tempCoupon: companyCouponsUnderCertainCategory) {
				tempCoupon.display();
			}
			
			System.out.println();
			System.out.println("--------------getting all customers coupons:");
			ArrayList<Coupon> customerCoupons = customerFacade.getCustomerCoupons();
			for(Coupon tempCoupon: customerCoupons) {
				tempCoupon.display();
			}
			
			System.out.println();
			System.out.println("---------------------------getting all customers Avialable coupons:");
			ArrayList<Coupon> customersAvialableCoupons = customerFacade.getAllAvailableCoupons();
			for(Coupon tempCoupon: customersAvialableCoupons) {
				tempCoupon.display();
			}
			
			System.out.println();
			System.out.println("getting all customers coupons under certain price:");
			ArrayList<Coupon> customerCouponsUnderCertainPrice = customerFacade.getCustomerCoupons(30.0);
			for(Coupon tempCoupon: customerCouponsUnderCertainPrice) {
				tempCoupon.display();
			}
			System.out.println();
			System.out.println("getting all customers coupons under certain category:");
			ArrayList<Coupon> customerCouponsUnderCertainCategory = customerFacade.getCustomerCoupons(Category.ELECTRICITY);
			for(Coupon tempCoupon: customerCouponsUnderCertainCategory) {
				tempCoupon.display();
			}
			
			System.out.println();
			System.out.println("getting one company:");
			Company companyTester1 = companyFacade.getCompanyDetails();
			companyTester1.display();

			
			System.out.println();
			System.out.println("getting one customer:");
			Customer customerTester1 = customerFacade.getCustomerDetails();
			customerTester1.display();
			
			System.out.println();
			System.out.println("checking 'isExists'");
			boolean c = customersDAO.isCustomerExists("roomy@gmail.com", "goog");
			System.out.println("is customer "+c);

			boolean d = companiesDAO.isCompanyExists("google@gmail.com", "goog");
			System.out.println("is company "+d);

			System.out.println();
			System.out.println("updating");
			System.out.println("---------------------------------------------------------------------------");
			
			System.out.println("company before update");
			Company companyTester2 = adminFacade.getOneCompany(2);
			companyTester2.display();

			company1.setEmail("updated_Email@gmail.com");
			company1.setPassword("updated_password");
			adminFacade.updateCompany(company1);
			
			System.out.println("checking if company updated");
			companyTester2 = adminFacade.getOneCompany(2);
			companyTester2.display();
			
			System.out.println("customer before update");
			Customer customerTester2 = adminFacade.getOneCustomer(1);
			customerTester2.display();
			
			customer.setEmail("UpdatedEmail@gmail.com");
			customer.setPassword("updated_password");
			adminFacade.updateCustomer(customer);
			couponsDAO.addCouponPurchase(1, 3);

			System.out.println("checking if customer updated");
			customerTester2 = adminFacade.getOneCustomer(1);
			customerTester2.display();
			
//			System.out.println();
//			System.out.println("deleting");
//			System.out.println("---------------------------------------------------------------------------");
//			System.out.println();
//			adminFacade.deleteCompany(company2);
//			System.out.println("customer shouldn't have coupon");
//			customerTester2 = adminFacade.getOneCustomer(1);
//			customerTester2.display();
//			
//			System.out.println();
//			System.out.println("getting all companies (should be two now): ");
//			ArrayList<Company> companiesArrayAfterDeletion = adminFacade.getAllCompanies();
//			for(Company tempCompany:companiesArrayAfterDeletion) {
//				tempCompany.display();
//			}
//			
//			System.out.println();
//			System.out.println("all customers after deleting one(should be two now): ");
//			adminFacade.deleteCustomer(adminFacade.getOneCustomer(1));
//			ArrayList<Customer> customersArrayAfterDeletion = adminFacade.getAllCustomers();
//			for(Customer tempCustomer:customersArrayAfterDeletion) {
//				tempCustomer.display();
//			}
//			
//			System.out.println();
//			System.out.println("customer after deleting one coupon from the company, should have one coupon left: ");
//			companyFacade.deleteCoupon(2);
//			customerFacade.getCustomerDetails().display();
			
		}
		catch (Exception e) {
			System.out.println(e.getMessage()+" exception");
		}
		finally {dailyJob.stop();}
	}

}
