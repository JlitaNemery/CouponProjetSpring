package service;

import webFilters.AdminFilter;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import com.sun.jersey.spi.container.ResourceFilters;

import javax.servlet.http.HttpServletResponse;

import beans.*;
import businessDelegate.BusinessDelegate;
import exceptions.BadInputException;
import facade.*;

@Path("/admin")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@ResourceFilters(AdminFilter.class)
public class AdminService {
	
	AdminFacade adminFacade = new AdminFacade();
	BusinessDelegate businessDelegate = new BusinessDelegate();

	
	

	@POST
	@Path("/companies")
	public Response createCompany(Company company) {
    	String json = "";
		try {
			Company tempCompany = adminFacade.addCompany(company);			
			json = Json.getCompanyJson(adminFacade.getOneCompanyByEmail(tempCompany.getEmail()));			
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
						
		}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();

		}		
	}
    
	@DELETE
    @Path("/companies/{id}")
    public Response removeCompany(@PathParam("id") int id) {
    	String json ="";
    	try {

    		Company company = adminFacade.getOneCompany(id);
    		adminFacade.deleteCompany(company);
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
    	}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();

    	}   
    }
    
	@PUT
    @Path("/companies/{id}")
    public Response updateCompany(@PathParam("id") int id, Company company) {
    	String json="";
    	try {
    		if(id<1) {
				throw new BadInputException();

    		}
    		company.setId(id);
    		adminFacade.updateCompany(company);
    		json = Json.getCompanyJson(adminFacade.getOneCompany(id));	
    		return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
    	}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();

    	}
    }
    
	@GET
    @Path("/companies/{id}")
    public Response getCompany(@PathParam("id")int id) {
    	String json = "";
    	try {

    		Company company = adminFacade.getOneCompany(id);
    		json = Json.getCompanyJson(company);
    		return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
    	}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();

    	}
    }
  
	@GET
    @Path("/companies") 
    public Response getAllCompanies() {
    	String json = "";
    	try {
    		json = Json.getAllCompaniesJson();
    		return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
    	}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();

    	}
    }
    
	@POST
	@Path("/customers")
	public Response createCustomer(Customer customer) {
    	String json = "";
		try {
			Customer tempCustomer = adminFacade.addCustomer(customer);			
			json = Json.getCustomerJson(adminFacade.getOneCustomerByEmail(tempCustomer.getEmail()));			
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
						
		}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();

		}		
	}
    
	@DELETE
    @Path("/customers/{id}")
    public Response removeCustomer(@PathParam("id") int id) {
    	String json ="";
    	try {

    		Customer customer = adminFacade.getOneCustomer(id);
    		adminFacade.deleteCustomer(customer);
    		json = Json.doneJson();		
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
    	}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();

    	}   
    }
    
	@PUT
    @Path("/customers/{id}")
    public Response updateCustomer(@PathParam("id") int id, Customer customer) {
    	String json="";
    	try {

    		customer.setId(id);
    		adminFacade.updateCustomer(customer);
    		json = Json.getCustomerJson(adminFacade.getOneCustomer(id));
    		return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
    	}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();

    	}
    }
    
	@GET
    @Path("/customers/{id}")
    public Response getCustomer(@PathParam("id")int id) {
    	String json = "";
    	try {

    		json = Json.getCustomerJson(adminFacade.getOneCustomer(id));
    		return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
    	}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();

    	}
    }
  
	@GET
    @Path("/customers") 
    public Response getAllCustomers() {
    	String json = "";
    	try {
    		json = Json.getAllCustomersJson();
    		return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
    	}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();

    	}
    }
	
	@GET
	@Path("/income/company/{id}") 
	public Response getIncomeByCompany(@PathParam("id") int companyID) {
		String json = "";
		try {
			json = businessDelegate.getIncomeByCompany(companyID);
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();

		}
	}
	
	@GET
	@Path("/income/number/company/{id}") 
	public Response getIncomeByCompanyNumber(@PathParam("id") int companyID) {
		String json = "";
		try {
			json = businessDelegate.getIncomeByCompanyNumber(companyID);
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();

		}
	}
	
	@GET
	@Path("/income/customer/{id}") 
	public Response getIncomeByCustomer(@PathParam("id") int customerID) {
		String json = "";
		try {
			json = businessDelegate.getIncomesByCustomer(customerID);
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();

		}
	}
	
	@GET
	@Path("/income/number/customer/{id}") 
	public Response getIncomeByCustomerNumber(@PathParam("id") int customerID) {
		String json = "";
		try {
			json = businessDelegate.getIncomesByCustomerNumber(customerID);
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();

		}
	}
	
	@GET
	@Path("/income") 
	public Response getAllIncome() {
		String json = "";
		try {
			json = businessDelegate.getAllIncomes();
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();

		}
	}
	
	@GET
	@Path("/income/number") 
	public Response getAllIncomeNumber() {
		String json = "";
		try {
			json = businessDelegate.getAllIncomesNumber();
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();

		}
	}
}
