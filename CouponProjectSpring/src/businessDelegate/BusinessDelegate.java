package businessDelegate;

import javax.ws.rs.core.MediaType;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import beans.Income;
import service.Json;


public class BusinessDelegate {

	
	public synchronized void storeIncome(Income income) throws Exception {
			Client client = Client.create();

			WebResource webResource = client.resource("http://localhost:8888/income");
			String json = Json.storeIncomeJson(income);
			WebResource.Builder wb = webResource.type(MediaType.APPLICATION_JSON);
			ClientResponse response = wb.post(ClientResponse.class,json);

			if (response.getStatus() >= 300) {
				System.out.println(response.getStatus());
			   throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
	}
	
	public synchronized void storeIncomeCompany(Income income) throws Exception {
		Client client = Client.create();

		WebResource webResource = client.resource("http://localhost:8888/income");
		String json = Json.storeIncomeJsonCompany(income);
		WebResource.Builder wb = webResource.type(MediaType.APPLICATION_JSON);
		ClientResponse response = wb.post(ClientResponse.class,json);

		if (response.getStatus() >= 300) {
			System.out.println(response.getStatus());
		   throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}
}
	
	
	
	public synchronized String getAllIncomes() {
		Client client = Client.create();
		String url = "http://localhost:8888/income";
		WebResource webResource = client.resource(url);
		WebResource.Builder wb = webResource.accept(MediaType.APPLICATION_JSON);
		ClientResponse response = wb.get(ClientResponse.class);
		
		if(response.getStatus() != 200) {
			throw new RuntimeException("Error: " + response.getStatus());
		}
		
		String income = response.getEntity(String.class);
		return income;
	}
	
	public synchronized String getAllIncomesNumber() {
		Client client = Client.create();
		String url = "http://localhost:8888/income/number";
		WebResource webResource = client.resource(url);
		WebResource.Builder wb = webResource.accept(MediaType.APPLICATION_JSON);
		ClientResponse response = wb.get(ClientResponse.class);
		
		if(response.getStatus() != 200) {
			throw new RuntimeException("Error: " + response.getStatus());
		}
		
		String income = response.getEntity(String.class);
		return income;
	}
	
	public synchronized String getIncomesByCustomer(int customerID) {
		Client client = Client.create();
		String url = "http://localhost:8888/income/customerId/" + customerID;
		WebResource webResource = client.resource(url);
		WebResource.Builder wb = webResource.accept(MediaType.APPLICATION_JSON);
		ClientResponse response = wb.get(ClientResponse.class);
		
		if(response.getStatus() >= 300) {
			throw new RuntimeException("Error: " + response.getStatus());
		}
		
		String income = response.getEntity(String.class);
		return income;
	}
	
	public synchronized String getIncomesByCustomerNumber(int customerID) {
		Client client = Client.create();
		String url = "http://localhost:8888/income/number/customerId/" + customerID;
		WebResource webResource = client.resource(url);
		WebResource.Builder wb = webResource.accept(MediaType.APPLICATION_JSON);
		ClientResponse response = wb.get(ClientResponse.class);
		
		if(response.getStatus() >= 300) {
			throw new RuntimeException("Error: " + response.getStatus());
		}
		
		String income = response.getEntity(String.class);
		return income;
	}
	
	public synchronized String getIncomeByCompany(int companyID) {
		Client client = Client.create();
		String url = "http://localhost:8888/income/companyId/" + companyID;
		WebResource webResource = client.resource(url);
		WebResource.Builder wb = webResource.accept(MediaType.APPLICATION_JSON);
		ClientResponse response = wb.get(ClientResponse.class);
		
		if(response.getStatus() >= 300) {
			throw new RuntimeException("Error: " + response.getStatus());
		}
		
		String income = response.getEntity(String.class);
		return income;
	}
	
	public synchronized String getIncomeByCompanyNumber(int companyID) {
		Client client = Client.create();
		String url = "http://localhost:8888/income/number/companyId/" + companyID;
		WebResource webResource = client.resource(url);
		WebResource.Builder wb = webResource.accept(MediaType.APPLICATION_JSON);
		ClientResponse response = wb.get(ClientResponse.class);
		
		if(response.getStatus() >= 300) {
			throw new RuntimeException("Error: " + response.getStatus());
		}
		
		String income = response.getEntity(String.class);
		return income;
	}
}
