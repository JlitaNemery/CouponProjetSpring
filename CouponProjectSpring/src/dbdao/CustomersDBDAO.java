package dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.*;
import connectionPool.*;
/**
 * 
 * this class handles access to the customers table
 *
 */
public class CustomersDBDAO implements CustomersDAO {

	CouponsDBDAO couponsDAO = new CouponsDBDAO();


	@Override
	public boolean isCustomerExists(String email, String password) throws SQLException, InterruptedException {

		Connection connection = ConnectionPool.getInstance().getConnection();
		try {

			String sql = String.format("select * from CUSTOMERS where EMAIL='%s' AND PASSWORD='%s'",email,password);

			try(PreparedStatement preparedStatment = connection.prepareStatement(sql)){

				try(ResultSet resultSet = preparedStatment.executeQuery()){
					while(resultSet.next()) {
						String resultEmail = resultSet.getString("EMAIL");
						String resultPassword = resultSet.getString("PASSWORD");					
						if(((resultEmail).equals(email)  && (resultPassword).equals(password)) &&
								((resultEmail != null) && (resultPassword != null))) {
							return true;
						}
					}

				}
			}
		}

		finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}	
		return false;

	}

	@Override
	public int addCustomer(Customer customer) throws SQLException, InterruptedException {
		Connection connection = ConnectionPool.getInstance().getConnection();

		try {
			String sql = String.format(
					"insert into CUSTOMERS(FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) values('%s','%s', '%s', '%s')", 
					customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPassword());

			try(PreparedStatement preparedStatement = 
					connection.prepareStatement(sql, 
							PreparedStatement.RETURN_GENERATED_KEYS)){

				preparedStatement.executeUpdate();

				try(ResultSet resultSet = preparedStatement.getGeneratedKeys()){
					resultSet.next();
					int id = resultSet.getInt(1);
					customer.setId(id);
					System.out.println("Insert succeeded. New customer id: " + id);
					return id;
				}
			}
		}

		finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	@Override
	public void updateCustomer(Customer customer) throws SQLException, InterruptedException {
		Connection connection = ConnectionPool.getInstance().getConnection();

		try {

			String sql = String.format(
					"update CUSTOMERS set FIRST_NAME='%s', LAST_NAME='%s', EMAIL='%s', PASSWORD='%s' where ID=%d", 
					customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPassword(), customer.getId());
			
			try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

				preparedStatement.executeUpdate();
			}
		}

		finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}


	}		

	@Override
	public void deleteCustomer(int costumerID) throws SQLException, InterruptedException {

		Connection connection = ConnectionPool.getInstance().getConnection();

		try {

			String sql = String.format("delete from CUSTOMERS where ID=%d", costumerID);

			try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

				preparedStatement.executeUpdate();
			}
		}

		finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	@Override
	public ArrayList<Customer> getAllCustomers() throws SQLException, InterruptedException {

		Connection connection = ConnectionPool.getInstance().getConnection();
		try {
			ArrayList<Customer> customerArr = new ArrayList<>();


			String sql = "select * from CUSTOMERS";

			try(PreparedStatement preparedStatment = connection.prepareStatement(sql)){


				try(ResultSet resultSet = preparedStatment.executeQuery()){

					while(resultSet.next()) {
						Customer customer = new Customer(resultSet.getInt("ID"), 
								resultSet.getString("FIRST_NAME"), resultSet.getString("LAST_NAME"), 
								resultSet.getString("EMAIL"), resultSet.getString("PASSWORD"),
								couponsDAO.getCouponByCustomerID(resultSet.getInt("ID"))); 
						customerArr.add(customer);
					}
					return customerArr;
				}
			}
		}

		finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}


	@Override
	public Customer getOneCustomer(int costumerID) throws SQLException, InterruptedException {

		Connection connection = ConnectionPool.getInstance().getConnection();
		try {
			String sql = String.format("select * from CUSTOMERS where ID=%d",costumerID) ;

			try(PreparedStatement preparedStatment = connection.prepareStatement(sql)){


				try(ResultSet resultSet = preparedStatment.executeQuery()){

					while(resultSet.next()) {
						Customer customer = new Customer(resultSet.getInt("ID"), 
								resultSet.getString("FIRST_NAME"), resultSet.getString("LAST_NAME"), 
								resultSet.getString("EMAIL"), resultSet.getString("PASSWORD"),
								couponsDAO.getCouponByCustomerID(resultSet.getInt("ID"))); 
						return customer;
					}
				}
			}
		}

		finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
		return null;
	}
	
	/**
	 * this function returns one customer by customer email
	 */
	@Override
	public Customer getOneCustomerByEmail(String email) throws SQLException, InterruptedException {
		
		Connection connection = ConnectionPool.getInstance().getConnection();
		try {
			String sql = String.format("select * from CUSTOMERS where EMAIL='%s'",email) ;
			
			try(PreparedStatement preparedStatment = connection.prepareStatement(sql)){
				
				
				try(ResultSet resultSet = preparedStatment.executeQuery()){
					
					while(resultSet.next()) {
						Customer customer = new Customer(resultSet.getInt("ID"), 
								resultSet.getString("FIRST_NAME"), resultSet.getString("LAST_NAME"), 
								resultSet.getString("EMAIL"), resultSet.getString("PASSWORD"),
								couponsDAO.getCouponByCustomerID(resultSet.getInt("ID"))); 
						return customer;
					}
				}
			}
		}

		finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
		return null;
	}

}
