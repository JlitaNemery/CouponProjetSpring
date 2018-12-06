package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;


import beans.Login;
import exceptions.BadLoginException;
import exceptions.BadInputException;
import facade.*;
import login.LoginManager;

@Path("/login")
public class LoginService {


	@Context // Servlet-הזרקה של האוביקט המתאים מה
	private HttpServletRequest request;
	public HttpSession session;


	LoginManager localLoginManager = LoginManager.getInstance();


	@GET
	public Response logOut() {
		try {
			this.session = this.request.getSession(false);
			this.session.invalidate();
			return Response.ok().build();
		}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

	@SuppressWarnings("unchecked")
	@POST
	public Response login(Login login) {

		String json = "";
		try {
			if (login.getClientType()==null) {
				throw new BadInputException();
			}
			System.out.println(login.getEmail() + " "+ login.getPassword() + " "+login.getClientType());
			ClientFacade loginUser = localLoginManager.login(login.getEmail(), login.getPassword(), login.getClientType());
			if(loginUser==null) {
				throw new BadLoginException();
			}
			this.session = this.request.getSession();
			session.setAttribute("clientType", loginUser);
			session.setAttribute("email", login.getEmail());
			session.setAttribute("password", login.getPassword());
			String facade = loginUser.toString();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("clientType", facade);
			jsonObject.put("session", session.getId());
			json = jsonObject.toString();
			return Response.ok(json).build();

		}catch(Exception e){

			return Response.serverError().entity(e.getMessage()).build();
		}
	}

}
