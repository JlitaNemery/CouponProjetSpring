package webFilters;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import com.sun.jersey.spi.container.ResourceFilter;

public class EnableSessionFilter implements ResourceFilter, ContainerRequestFilter, ContainerResponseFilter {

	public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {
		response.getHttpHeaders().add("Access-Control-Allow-Origin", "http://localhost:4200");
		response.getHttpHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, x-requested-with");
		response.getHttpHeaders().add("Access-Control-Allow-Credentials", "true");
		response.getHttpHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		return response;
	}

	public ContainerRequest filter(ContainerRequest request) { return request; }
	public ContainerRequestFilter getRequestFilter() { return this; }
	public ContainerResponseFilter getResponseFilter() { return this; }
}
