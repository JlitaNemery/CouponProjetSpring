package beans;
import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * this class is for building a Customer object
 * 
 *
 */

@XmlRootElement
@SuppressWarnings("serial")
public class Customer implements Serializable{

	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private ArrayList<Coupon> coupon;
	
	
	public Customer() {
		
	}
	
	public Customer(String firstName, String lastName, String email, String pasword, ArrayList<Coupon> coupon) {
		setFirstName(firstName);
		setLastName(lastName);
		setEmail(email);
		setPassword(pasword);
		setCoupon(coupon);
	}
	
	public Customer(int id, String firstName, String lastName, String email, String pasword) {
		setFirstName(firstName);
		setLastName(lastName);
		setEmail(email);
		setPassword(pasword);
		setCoupon(coupon);
	}
	
	public Customer(int id, String firstName, String lastName, String email, String pasword, ArrayList<Coupon> coupon) {
		setId(id);
		setFirstName(firstName);
		setLastName(lastName);
		setEmail(email);
		setPassword(pasword);
		setCoupon(coupon);
	}

	public int getId() {
		return id;
	}

	@XmlElement
	public void setId(int id) {
		this.id = id;
	}

	@XmlElement
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	@XmlElement
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}
	
	@XmlElement
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	@XmlElement
	public void setPassword(String pasword) {
		this.password = pasword;
	}

	public ArrayList<Coupon> getCoupon() {
		return coupon;
	}

	@XmlElement
	public void setCoupon(ArrayList<Coupon> coupon) {
		this.coupon = coupon;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", coupon=" + coupon + "]";
	}
	
	public void display () {
		
		System.out.println("Customer [Id= " + id + ", First Name= " + firstName + ", Last Name= " + lastName + ", Email= " + email
				+ ", password= " + password);
		System.out.println("----coupon arrayList----");
		for (Coupon couponInArr : coupon) 
		{
			System.out.println(couponInArr.toString());
		}

		System.out.println("------------------------------------------------");
	
	}
	

	
	
}
