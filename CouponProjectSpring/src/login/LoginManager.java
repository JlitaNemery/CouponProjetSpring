package login;
import java.sql.SQLException;

import exceptions.BadLoginException;
import facade.*;

/**
 * 
 * this class handles all login requests
 *
 */
public class LoginManager {

	private AdminFacade adminFacade = new AdminFacade();
	private CompanyFacade companyFacade = new CompanyFacade();
	private CustomerFacade customerFacade = new CustomerFacade();


	private static LoginManager instance = new LoginManager();

	private LoginManager() {

	}
/**
 * this function checks if the login attempt matches any of the system users
 *  
 * @return
 */
	public static LoginManager getInstance() {
		return instance;
	}

	public ClientFacade login(String email, String password, ClientType clientType) 
			throws SQLException, InterruptedException, BadLoginException {
		
		switch (clientType){
		case ADMINISTRATOR: if(adminFacade.login(email, password)) {
			return adminFacade;
		}
		else {
			return null;
		}
		case COMPANY: if(companyFacade.login(email, password)) {
			return companyFacade;
		}
		else {
			return null;
		}
		case CUSTOMER: if(customerFacade.login(email, password)) {
			return customerFacade;
		}
		else {
			return null;
		}
		}
		return null;
	}
}
