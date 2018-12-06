package facade;
import java.sql.SQLException;
import java.util.ArrayList;
import beans.*;
import dbdao.*;
import exceptions.*;

/**
 * 
 * this class handles admin functions
 * 
 *
 */
public class AdminFacade extends ClientFacade{
	public AdminFacade() {
		companiesDAO = new CompaniesDBDAO();
		customersDAO = new CustomersDBDAO();
		couponsDAO = new CouponsDBDAO();
	}

	@Override
	public boolean login(String email, String password) {
		return email.equals("admin@admin.com") && password.equals("admin");

	}


	public Company addCompany(Company company) throws SQLException, 
	InterruptedException, CompanyNameAlreadyExistsException, 
	CompanyEmailAlreadyExistsExeption {
		ArrayList<Company> allCompanies = companiesDAO.getAllCompanies();

		for(Company comp : allCompanies) {
			if(comp.getName().equals(company.getName())) {
				throw new CompanyNameAlreadyExistsException();
			}
			if(comp.getEmail().equals(company.getEmail())){
				throw new CompanyEmailAlreadyExistsExeption();
			}
		}
		int companyId = companiesDAO.addCompany(company);
		company.setId(companyId);
		return company;
	}

	public void updateCompany (Company company) throws SQLException, InterruptedException, 
	CompanyNameCantBeChangedException, CompanyDoesntExistException, CompanyNameAlreadyExistsException, CompanyEmailAlreadyExistsExeption {
		ArrayList<Company> companies = companiesDAO.getAllCompanies();
		for(Company tempCompany:companies) {
			if(tempCompany.getEmail().equals(company.getEmail())&& tempCompany.getId()!=company.getId()) {
				throw new CompanyEmailAlreadyExistsExeption();
			}
			if(tempCompany.getName().equals(company.getName()) && tempCompany.getId()!=company.getId()) {
				throw new CompanyNameAlreadyExistsException();
			}
		}
		company.setName(this.getOneCompany(company.getId()).getName());
		Company tempCompany = companiesDAO.getOneCompany(company.getId());
		if(tempCompany.getName().equals(company.getName()) && tempCompany.getId()==company.getId()) {
			companiesDAO.updateCompany(company);
		}
		else {
			throw new CompanyNameCantBeChangedException();
		}
	}


	public void deleteCompany (Company company) throws SQLException, InterruptedException {
		ArrayList<Coupon> couponArr =
				couponsDAO.getAllCouponsFromCompany(company.getId());
		for(Coupon coupon:couponArr) {
			couponsDAO.deleteCoupon(coupon.getId());
			couponsDAO.deleteCouponPurchaseByCoupon(coupon.getId());
		}
		companiesDAO.deleteCompany(company.getId());
	}

	public ArrayList<Company> getAllCompanies() throws SQLException, InterruptedException{
		return companiesDAO.getAllCompanies();
	}
	public Company getOneCompany(int companyID) throws SQLException, 
	InterruptedException, CompanyDoesntExistException {
		if(companiesDAO.getOneCompany(companyID)!=null) {
			return companiesDAO.getOneCompany(companyID);
		}
		else {
			throw new CompanyDoesntExistException();
		}
	}

	public Customer addCustomer(Customer customer) throws SQLException, InterruptedException, 
	CustomerEmailAlreadyExistsExeption {
		ArrayList<Customer> allCustomers = customersDAO.getAllCustomers();

		for(Customer cust : allCustomers) {
			if(cust.getEmail().equals(customer.getEmail())){
				throw new CustomerEmailAlreadyExistsExeption();
			}
		}
		int customerId = customersDAO.addCustomer(customer);
		customer.setId(customerId);
		return customer;
	}

	
	public void updateCustomer (Customer customer) throws SQLException, InterruptedException,
	CustomerIDCantBeChangedException, CustomerEmailAlreadyExistsExeption {
		ArrayList<Customer> customers = customersDAO.getAllCustomers();
		for(Customer arrayCustomer:customers) {
			if(arrayCustomer.getEmail().equals(customer.getEmail())&& arrayCustomer.getId()!=customer.getId()) {
				throw new CustomerEmailAlreadyExistsExeption();
			}
		}
		
		Customer tempCustomer = customersDAO.getOneCustomer(customer.getId());
		if(tempCustomer.getId() == customer.getId()) {
			customersDAO.updateCustomer(customer);
		}
		else {
			throw new CustomerIDCantBeChangedException();
		}
	}

	public void deleteCustomer (Customer customer) throws SQLException, InterruptedException,
	CompanyNameCantBeChangedException {
		couponsDAO.deleteCouponPurchaseByCustomer(customer.getId());
		customersDAO.deleteCustomer(customer.getId());

	}

	public ArrayList<Customer> getAllCustomers() throws SQLException, InterruptedException{
		return customersDAO.getAllCustomers();
	}
	public Customer getOneCustomer(int costumerID) throws SQLException, InterruptedException, CustomerDoesntExistException{
		if(customersDAO.getOneCustomer(costumerID)!=null) {
			return customersDAO.getOneCustomer(costumerID);
		}
		else{
			throw new CustomerDoesntExistException();
		}	}

	public String toString() {
		return "adminFacade";
	}

	public Company getOneCompanyByEmail(String email)throws SQLException, 
	InterruptedException, CompanyDoesntExistException {
		if(companiesDAO.getOneCompanyByEmail(email)!=null) {
			return companiesDAO.getOneCompanyByEmail(email);
		}
		else {
			throw new CompanyDoesntExistException();
		}
	}

	public Customer getOneCustomerByEmail(String email) throws SQLException, InterruptedException, CustomerDoesntExistException {
		if(customersDAO.getOneCustomerByEmail(email)!=null) {
			return customersDAO.getOneCustomerByEmail(email);
		}
		else{
			throw new CustomerDoesntExistException();
		}
	}


}
