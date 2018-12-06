package service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
import businessDelegate.BusinessDelegate;
import exceptions.BadInputException;
import facade.*;
import webFilters.CompanyFilter;

@Path("/company")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@ResourceFilters(CompanyFilter.class)
public class CompanyService {

	@Context 
	private HttpServletRequest request;



	CompanyFacade companyFacade = new CompanyFacade();
	BusinessDelegate businessDelegate = new BusinessDelegate();


	@PostConstruct
	public void login() {
		String json;
		try {
			HttpSession session = request.getSession();

			companyFacade.login(session.getAttribute("email").toString(), session.getAttribute("password").toString());

		}catch(Exception e){
			json = "{\"error!!!\": \""+e.getMessage()+"\"}";
			System.out.println(json);
		}		

	}

	@POST
	@Path("/coupons")
	public Response createCoupon(Coupon coupon) {
		String json = "";
		try {
			Coupon tempCoupon = companyFacade.addCoupon(coupon);
			json = Json.getCouponJson(companyFacade.getOneCoupon(tempCoupon.getId()));
			
			Income income = new Income();
			income.setName(companyFacade.getCompanyDetails().getName());
			income.setDescription(IncomeType.COMPANY_NEW_COUPON);
			income.setAmount(100);
			income.setCompanyId(companyFacade.getCompanyID());
			businessDelegate.storeIncomeCompany(income);
			
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();

		}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();

		}		
	}

	@DELETE
	@Path("/coupons/{id}")
	public Response removeCoupon(@PathParam("id") int id) {
		String json ="";
		try {

			Coupon coupon = companyFacade.getOneCoupon(id);
			companyFacade.deleteCoupon(coupon.getId());
			json = Json.doneJson();		
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();

		}   
	}

	@PUT
	@Path("/coupons/{id}")
	public Response updateCoupon(@PathParam("id") int id, Coupon coupon) {
		String json="";
		try {

			coupon.setId(id);
			companyFacade.updateCoupon(coupon);
			json = Json.getCouponJson(companyFacade.getOneCoupon(id));	
			
			Income income = new Income();
			income.setName(companyFacade.getCompanyDetails().getName());
			income.setDescription(IncomeType.COMPANY_UPDATE_COUPON);
			income.setAmount(10);
			income.setCompanyId(companyFacade.getCompanyID());
			businessDelegate.storeIncomeCompany(income);
			
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();

		}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();

		}
	}

	@GET
	@Path("/coupons/{id}")
	public Response getCoupon(@PathParam("id")int id) {
		String json = "";
		try {

			Coupon coupon = companyFacade.getOneCoupon(id);
			coupon.display();
			json = Json.getCouponJson(coupon);	
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();

		}
	}

	@GET
	@Path("/coupons") 
	public Response getAllCoupons() {
		String json = "";
		try {
			json = Json.getAllCompanyCouponsJson(companyFacade);
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();

		}
	}

	@GET
	@Path("/coupons/price/{price}") 
	public Response getAllCouponsByPrice(@PathParam("price") double price) {
		String json = "";
		try {
			if(price<0) {
				throw new BadInputException();

    		}
			json = Json.getAllCompanyCouponsByPriceJson(price,companyFacade);
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();

		}
	}
	
	@GET
	@Path("/coupons/category/{category}") 
	public Response getAllCouponsByType(@PathParam("category") Category type) {
		String json = "";
		try {
			if(type==null) {
				throw new BadInputException();
			}
			json = Json.getAllCompanyCouponsByTypeJson(type,companyFacade);
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();

		}
	}

	@GET
	@Path("income") 
	public Response getIncome() {
		String json = "";
		try {
			json = businessDelegate.getIncomeByCompanyNumber(companyFacade.getCompanyID());
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();

		}
	}
	
	@GET
	@Path("/income/array") 
	public Response getIncomeArray() {
		String json = "";
		try {
			json = businessDelegate.getIncomeByCompany(companyFacade.getCompanyID());
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();

		}
	}
}
