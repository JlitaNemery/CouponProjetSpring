package beans;
import java.io.Serializable;
import java.time.LocalDate;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import Adapter.CategoryAdapter;
import Adapter.LocalDateAdapter;
/**
 * this class is for building a Coupon object
 * 
 *
 */

@XmlRootElement
@SuppressWarnings("serial")
public class Coupon implements Serializable {

	private int id;
	private int companyId;
	private Category category;
	private String title;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private int amount;
	private double price;
	private String image;
	
	public Coupon() {
		
	}
	
	public Coupon(Category category, String title, 
			String description, LocalDate startDate, LocalDate endDate, int amount, 
			double price, String image)
	{
		setCategory(category);
		setTitle(title);
		setDescription(description);
		setStartDate(startDate);
		setEndDate(endDate);
		setAmount(amount);
		setPrice(price);
		setImage(image);
	}
	
	public Coupon(int companyId, Category category, String title, 
			String description, LocalDate startDate, LocalDate endDate, int amount, 
			double price, String image)
	{
		setCompanyId(companyId);
		setCategory(category);
		setTitle(title);
		setDescription(description);
		setStartDate(startDate);
		setEndDate(endDate);
		setAmount(amount);
		setPrice(price);
		setImage(image);
	}
	public Coupon(int id, int companyId, Category category, String title, 
			String description, LocalDate startDate, LocalDate endDate, int amount, 
			double price, String image)
	{
		setId(id);
		setCompanyId(companyId);
		setCategory(category);
		setTitle(title);
		setDescription(description);
		setStartDate(startDate);
		setEndDate(endDate);
		setAmount(amount);
		setPrice(price);
		setImage(image);
	}
	public Coupon(int id, int companyId, Category category, String title, 
			String description, String startDate, String endDate, int amount, 
			double price, String image)
	{
		setId(id);
		setCompanyId(companyId);
		setCategory(category);
		setTitle(title);
		setDescription(description);
		setStartDate(LocalDate.parse(startDate));
		setEndDate(LocalDate.parse(endDate));
		setAmount(amount);
		setPrice(price);
		setImage(image);
	}


	public int getId() {
		return id;
	}

	@XmlElement
	public void setId(int id) {
		this.id = id;
	}

	public int getCompanyId() {
		return companyId;
	}

	@XmlElement
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public Category getCategory() {
		return category;
	}

	@XmlElement
	@XmlJavaTypeAdapter(CategoryAdapter.class)
	public void setCategory(Category category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	@XmlElement
	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	@XmlElement
	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	@XmlElement
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	@XmlElement
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public int getAmount() {
		return amount;
	}

	@XmlElement
	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	@XmlElement
	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	@XmlElement
	public void setImage(String image) {
		this.image = image;
	}
	
	public String toString()
	{
		return "Class = Coupon, Id = " + id + ", CompanyID = " + companyId + ", Type = " + category +
				", Title = " + title + ", Description = " + description + ", Start date = " + startDate +
				", End date = " + endDate + ", Amount = " + amount + ", Image = " + image;
	}
	public void display() {
		
		System.out.println("Class = Coupon, Id = " + id + ", CompanyID = " + companyId + ", Category = " + category +
				", Title = " + title + ", Description = " + description + ", Start date = " + startDate +
				", End date = " + endDate + ", Amount = " + amount + ", Image = " + image);
	}

}
