package webFilters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import com.sun.jersey.spi.container.ResourceFilter;

import facade.ClientFacade;
import facade.CustomerFacade;

public class CustomerFilter implements ResourceFilter, ContainerRequestFilter, ContainerResponseFilter {
	@Context
	private HttpServletRequest generalRequest;

	private CustomerFacade customerFacade = new CustomerFacade();
	
	@Override
	public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {
		return response;
	}
	@Override
	public ContainerRequest filter(ContainerRequest request) {

		HttpSession session = generalRequest.getSession();
		ClientFacade facade = (ClientFacade) session.getAttribute("clientType"); 


		if(facade.toString()!=customerFacade.toString()) {					
			ResponseBuilder builder = null;
			String response = "UNAUTHORIZED!";
			builder = Response.status(Response.Status.UNAUTHORIZED).entity(response);
			throw new WebApplicationException(builder.build());
		}

		return request;
	}
	@Override
	public ContainerRequestFilter getRequestFilter() {
		// TODO Auto-generated method stub
		return this;
	}
	@Override
	public ContainerResponseFilter getResponseFilter() {
		// TODO Auto-generated method stub
		return this;
	}

}
