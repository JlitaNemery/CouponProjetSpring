package beans;

public class Income {
	
	private int id;
	private String name;
	private IncomeType description;
	private double amount;
	private int customerId;
	private int companyId;

	public Income() {}
	
	public Income(String name, IncomeType description, 
			double amount, int customerId, int companyId){
		setName(name);
		setDescription(description);
		setAmount(amount);
		setCustomerId(customerId);
		setCompanyId(companyId);
	}
		
	public Income(int id, String name, IncomeType description, 
			double amount, int customerId, int companyId){
		this(name, description, amount, customerId, companyId);
		setId(id);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IncomeType getDescription() {
		return description;
	}

	public void setDescription(IncomeType description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String toString() {
		return "Income [id=" + id + ", name=" + name 
				+ " description=" + description + ", amount=" + amount + "]";
	}
	

}
