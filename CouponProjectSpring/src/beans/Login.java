package beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import login.ClientType;

import java.io.Serializable;

@XmlRootElement
@SuppressWarnings("serial")
public class Login implements Serializable{
	private ClientType clientType;
	private String password;
	private String email;
	
	public Login() {}
	public Login (ClientType clientType, String password, String email) {
		setEmail(email);
		setPassword(password);
		setClientType(clientType);
	}
	public Login (String password, String email) {
		setEmail(email);
		setPassword(password);
	}
	public ClientType getClientType() {
		return clientType;
	}
	@XmlElement
	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}
	public String getPassword() {
		return password;
	}
	@XmlElement
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	@XmlElement
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
