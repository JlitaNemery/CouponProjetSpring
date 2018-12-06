package service;

import businessDelegate.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.sun.jersey.spi.container.ResourceFilters;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import beans.*;
import exceptions.BadInputException;
import facade.*;
import webFilters.CustomerFilter;


@Path("/customer")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@ResourceFilters(CustomerFilter.class)
public class CustomerService {
	
	@Context // Servlet-הזרקה של האוביקט המתאים מה
	private HttpServletRequest request;

	CustomerFacade customerFacade = new CustomerFacade();
	
	BusinessDelegate businessDelegate = new BusinessDelegate();
	
	@PostConstruct
	public void login() {
		String json;
		try {
			HttpSession session = request.getSession();

			customerFacade.login(session.getAttribute("email").toString(), session.getAttribute("password").toString());
		}catch(Exception e){
			json = "{\"error!!!\": \""+e.getMessage()+"\"}";
			System.out.println(json);
		}	
	}
	
	@POST
	@Path("/purchase")
	public Response purchaseCoupon(Coupon coupon) {
		String json;
		try {
			Coupon perCoupon = customerFacade.getOneCoupon(coupon.getId());
			customerFacade.purchaseCoupon(perCoupon);
			json = Json.getCouponJson(perCoupon);

			Income income = new Income();
			income.setName(customerFacade.getCustomerDetails().getFirstName());
			income.setDescription(IncomeType.CUSTOMER_BUY_COUPON);
			income.setAmount(coupon.getPrice());
			income.setCustomerId(customerFacade.getCustomerDetails().getId());
			income.setCompanyId(0);
			businessDelegate.storeIncome(income);
			
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
	@GET
	@Path("/coupons")
	public Response getAllPurchasedCoupons() {
		String json;
		try {
			json = Json.getAllCustomerCouponsJson(customerFacade);
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();
		}
		
	}
	@GET
	@Path("/coupons/{id}")
	public Response getOneCoupon(@PathParam("id") int id) {
		String json;
		try {
			if(id<1) {
				throw new BadInputException();

    		}
			Coupon coupon = customerFacade.getOneCoupon(id);
			json = Json.getCouponJson(coupon);
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();
		}
		
	}
	@GET
	@Path("/coupons/category/{category}")
	public Response getAllPurchasedCouponsByCategory(@PathParam("category") Category category) {
		String json;
		try {

			json = Json.getAllCustomerCouponsByCategoryJson(category, customerFacade);
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();
		}		
	}
	
	@GET
	@Path("/coupons/price/{price}")
	public Response getAllPurchasedCouponsByPrice(@PathParam("price") double price) {
		String json;
		try {

			json = Json.getAllCustomerCouponsByPriceJson(price, customerFacade);
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();

		}		
	}
	
	@GET
	@Path("/coupons/all")
	public Response getAllCoupons() {
		String json;
		try {
			json = Json.getAllCoupons();
			System.out.println(json);
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();

		}		
	}
	@GET
	@Path("/coupons/available")
	public Response getAllAvailableCoupons() {
		String json;
		try {
			System.out.println("romo");
			json = Json.getAllCustomerAvailableCouponsJson(customerFacade);
			System.out.println(json);
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();

		}		
	}
	
	
}
