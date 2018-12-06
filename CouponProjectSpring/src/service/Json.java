package service;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import beans.*;
import dbdao.*;
import facade.*;


/**
 * 
 * this class handles JSON conversions
 *
 */
public class Json {

	static AdminFacade adminFacade = new AdminFacade();
	static CompanyFacade companyFacade = new CompanyFacade();
	static CustomerFacade customerFacade = new CustomerFacade();
	static CouponsDBDAO couponsDAO = new CouponsDBDAO();


	@SuppressWarnings("unchecked")
	public static String getCompanyJson(Company company) throws Exception {
		if(company == null) {
			return null;
		}
		JSONObject jsonObject =new JSONObject();		
		ArrayList<Coupon> coupons = company.getCoupon();
		JSONArray jsonArray = new JSONArray();

		for (Coupon coupon : coupons) {
			JSONObject jsonCoupon =new JSONObject();
			jsonCoupon.put("id", coupon.getId());
			jsonCoupon.put("companyId", coupon.getCompanyId());
			jsonCoupon.put("category", coupon.getCategory().toString());
			jsonCoupon.put("title", coupon.getTitle());
			jsonCoupon.put("description", coupon.getDescription());
			jsonCoupon.put("startDate", coupon.getStartDate().toString());
			jsonCoupon.put("endDate", coupon.getEndDate().toString());
			jsonCoupon.put("amount", coupon.getAmount());
			jsonCoupon.put("price", coupon.getPrice());
			jsonCoupon.put("image", coupon.getImage());		
			jsonArray.add(jsonCoupon);
		}

		jsonObject.put("id", company.getId());
		jsonObject.put("name", company.getName());
		jsonObject.put("email", company.getEmail());
		jsonObject.put("password", company.getPassword());
		jsonObject.put("coupons", jsonArray);

		return jsonObject.toJSONString();
	}
	@SuppressWarnings("unchecked")
	public static String getAllCompaniesJson() throws Exception {
		JSONArray jsonArrayCompany = new JSONArray();
		ArrayList<Company> allCompanies = adminFacade.getAllCompanies();
		for (Company company : allCompanies) {
			JSONObject jsonObject =new JSONObject();		
			ArrayList<Coupon> coupons = company.getCoupon();
			JSONArray jsonArray = new JSONArray();

			for (Coupon coupon : coupons) {
				JSONObject jsonCoupon =new JSONObject();
				jsonCoupon.put("id", coupon.getId());
				jsonCoupon.put("companyId", coupon.getCompanyId());
				jsonCoupon.put("category", coupon.getCategory().toString());
				jsonCoupon.put("title", coupon.getTitle());
				jsonCoupon.put("description", coupon.getDescription());
				jsonCoupon.put("startDate", coupon.getStartDate().toString());
				jsonCoupon.put("endDate", coupon.getEndDate().toString());
				jsonCoupon.put("amount", coupon.getAmount());
				jsonCoupon.put("price", coupon.getPrice());
				jsonCoupon.put("image", coupon.getImage());		
				jsonArray.add(jsonCoupon);
			}

			jsonObject.put("id", company.getId());
			jsonObject.put("name", company.getName());
			jsonObject.put("email", company.getEmail());
			jsonObject.put("password", company.getPassword());
			jsonObject.put("coupons", jsonArray);
			jsonArrayCompany.add(jsonObject);
		}
		return jsonArrayCompany.toString();				



	}



	@SuppressWarnings("unchecked")
	public static String doneJson() {
		JSONObject jsonObject =new JSONObject();
		jsonObject.put("done. ", "done. ");
		return jsonObject.toString();
	}

	@SuppressWarnings("unchecked")
	public static String getCouponJson(Coupon coupon) throws Exception {

		JSONObject jsonCoupon =new JSONObject();		

		jsonCoupon.put("id", coupon.getId());
		jsonCoupon.put("companyId", coupon.getCompanyId());
		jsonCoupon.put("category", coupon.getCategory().toString());
		jsonCoupon.put("title", coupon.getTitle());
		jsonCoupon.put("description", coupon.getDescription());
		jsonCoupon.put("startDate", coupon.getStartDate().toString());
		jsonCoupon.put("endDate", coupon.getEndDate().toString());
		jsonCoupon.put("amount", coupon.getAmount());
		jsonCoupon.put("price", coupon.getPrice());
		jsonCoupon.put("image", coupon.getImage());
		return jsonCoupon.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject getCouponJsonObject(Coupon coupon) throws Exception {
		
		JSONObject jsonCoupon =new JSONObject();		
		
		jsonCoupon.put("id", coupon.getId());
		jsonCoupon.put("companyId", coupon.getCompanyId());
		jsonCoupon.put("category", coupon.getCategory().toString());
		jsonCoupon.put("title", coupon.getTitle());
		jsonCoupon.put("description", coupon.getDescription());
		jsonCoupon.put("startDate", coupon.getStartDate().toString());
		jsonCoupon.put("endDate", coupon.getEndDate().toString());
		jsonCoupon.put("amount", coupon.getAmount());
		jsonCoupon.put("price", coupon.getPrice());
		jsonCoupon.put("image", coupon.getImage());
		return jsonCoupon;
	}


	@SuppressWarnings("unchecked")
	public static String getAllCustomersJson() throws Exception {
		JSONArray jsonCustomerArray = new JSONArray();
		ArrayList<Customer> allCustomers = adminFacade.getAllCustomers();
		for (Customer customer : allCustomers) {
			jsonCustomerArray.add(getCustomerJsonObject(customer));
		}
		return jsonCustomerArray.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static String getAllCustomerCouponsJson(CustomerFacade customerFacade) throws Exception {
		JSONArray jsonCouponArray = new JSONArray();
		ArrayList<Coupon> coupons = customerFacade.getCustomerCoupons();
		for (Coupon coupon : coupons) {
			jsonCouponArray.add(getCouponJsonObject(coupon));
		}
		return jsonCouponArray.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static String getAllCustomerAvailableCouponsJson(CustomerFacade customerFacade) throws Exception {
		JSONArray jsonCouponArray = new JSONArray();
		ArrayList<Coupon> coupons = customerFacade.getAllAvailableCoupons();
		for (Coupon coupon : coupons) {
			
			jsonCouponArray.add(getCouponJsonObject(coupon));
		}
		return jsonCouponArray.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static String getAllCustomerCouponsByPriceJson(double price, CustomerFacade customerFacade) throws Exception {
		JSONArray jsonCouponArray = new JSONArray();
		ArrayList<Coupon> coupons = customerFacade.getCustomerCoupons(price);
		for (Coupon coupon : coupons) {			
			jsonCouponArray.add(getCouponJsonObject(coupon));				
		}
		return jsonCouponArray.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static String getAllCompanyCouponsByTypeJson(Category category, CompanyFacade companyFacade) throws Exception {
		JSONArray jsonCouponArray = new JSONArray();
		ArrayList<Coupon> coupons = companyFacade.getCompanyCoupons(category);
		for (Coupon coupon : coupons) {			
			jsonCouponArray.add(getCouponJsonObject(coupon));				
		}
		return jsonCouponArray.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static String getAllCompanyCouponsByPriceJson(double price, CompanyFacade companyFacade) throws Exception {
		JSONArray jsonCouponArray = new JSONArray();
		ArrayList<Coupon> coupons = companyFacade.getCompanyCoupons(price);
		for (Coupon coupon : coupons) {			
			jsonCouponArray.add(getCouponJsonObject(coupon));				
		}
		return jsonCouponArray.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static String getAllCompanyCouponsJson(CompanyFacade companyFacade) throws Exception {
		JSONArray jsonCouponArray = new JSONArray();
		ArrayList<Coupon> coupons = companyFacade.getCompanyCoupons();
		for (Coupon coupon : coupons) {			
			jsonCouponArray.add(getCouponJsonObject(coupon));
		}
		return jsonCouponArray.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static String getAllCustomerCouponsByCategoryJson(Category category, CustomerFacade customerFacade) throws Exception {
		JSONArray jsonCouponArray = new JSONArray();
		ArrayList<Coupon> coupons = customerFacade.getCustomerCoupons(category);
		for (Coupon coupon : coupons) {			
			jsonCouponArray.add(getCouponJsonObject(coupon));				
		}
		return jsonCouponArray.toString();
	}

	@SuppressWarnings("unchecked")
	public static String getCustomerJson(Customer customer) throws Exception {
		if(customer == null) {
			return null;
		}

		JSONObject jsonObject =new JSONObject();		
		ArrayList<Coupon> coupons = customer.getCoupon();
		JSONArray jsonArray = new JSONArray();

		for (Coupon coupon : coupons) {
			JSONObject jsonCoupon =new JSONObject();
			jsonCoupon.put("id", coupon.getId());
			jsonCoupon.put("companyId", coupon.getCompanyId());
			jsonCoupon.put("category", coupon.getCategory().toString());
			jsonCoupon.put("title", coupon.getTitle());
			jsonCoupon.put("description", coupon.getDescription());
			jsonCoupon.put("startDate", coupon.getStartDate().toString());
			jsonCoupon.put("endDate", coupon.getEndDate().toString());
			jsonCoupon.put("amount", coupon.getAmount());
			jsonCoupon.put("price", coupon.getPrice());
			jsonCoupon.put("image", coupon.getImage());			
			jsonArray.add(jsonCoupon);
		}
		jsonObject.put("id", customer.getId());
		jsonObject.put("firstName", customer.getFirstName());
		jsonObject.put("lastName", customer.getLastName());
		jsonObject.put("email", customer.getEmail());
		jsonObject.put("password", customer.getPassword());
		jsonObject.put("coupons", jsonArray);

		return jsonObject.toString();

	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject getCustomerJsonObject(Customer customer) throws Exception {
		if(customer == null) {
			return null;
		}
		
		JSONObject jsonObject =new JSONObject();		
		ArrayList<Coupon> coupons = customer.getCoupon();
		JSONArray jsonArray = new JSONArray();
		
		for (Coupon coupon : coupons) {
			JSONObject jsonCoupon =new JSONObject();
			jsonCoupon.put("id", coupon.getId());
			jsonCoupon.put("companyId", coupon.getCompanyId());
			jsonCoupon.put("category", coupon.getCategory().toString());
			jsonCoupon.put("title", coupon.getTitle());
			jsonCoupon.put("description", coupon.getDescription());
			jsonCoupon.put("startDate", coupon.getStartDate().toString());
			jsonCoupon.put("endDate", coupon.getEndDate().toString());
			jsonCoupon.put("amount", coupon.getAmount());
			jsonCoupon.put("price", coupon.getPrice());
			jsonCoupon.put("image", coupon.getImage());			
			jsonArray.add(jsonCoupon);
		}
		jsonObject.put("id", customer.getId());
		jsonObject.put("firstName", customer.getFirstName());
		jsonObject.put("lastName", customer.getLastName());
		jsonObject.put("email", customer.getEmail());
		jsonObject.put("password", customer.getPassword());
		jsonObject.put("coupons", jsonArray);
		
		return jsonObject;
		
	}
	
	@SuppressWarnings("unchecked")
	public static String getAllCoupons() throws Exception {
		JSONArray jsonCouponArray = new JSONArray();
		ArrayList<Coupon> coupons = customerFacade.getAllCoupons();
		for (Coupon coupon : coupons) {			
			jsonCouponArray.add(getCouponJsonObject(coupon));				
		}
		return jsonCouponArray.toString();
	}

	@SuppressWarnings("unchecked")
	public static String storeIncomeJson(Income income) {
		
		JSONObject jsonObject = new JSONObject();
				
		jsonObject.put("name", income.getName());
		jsonObject.put("description", income.getDescription().toString());
		jsonObject.put("amount", income.getAmount());
		jsonObject.put("customerId", income.getCustomerId());
		jsonObject.put("companyId", income.getCompanyId());
				
		return jsonObject.toString();	
	}
	@SuppressWarnings("unchecked")
	public static String storeIncomeJsonCompany(Income income) {
		
		JSONObject jsonObject = new JSONObject();
				
		jsonObject.put("name", income.getName());
		jsonObject.put("description", income.getDescription().toString());
		jsonObject.put("amount", income.getAmount());
		jsonObject.put("companyId", income.getCompanyId());
				
		return jsonObject.toString();	
	}
}


