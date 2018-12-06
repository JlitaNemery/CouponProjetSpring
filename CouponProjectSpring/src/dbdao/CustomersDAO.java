package dbdao;


import java.sql.SQLException;
import java.util.ArrayList;

import beans.*;

public interface CustomersDAO {
	 boolean isCustomerExists(String email, String password) throws SQLException, InterruptedException;
	 int addCustomer(Customer customer) throws SQLException, InterruptedException;
	 void updateCustomer (Customer customer) throws SQLException, InterruptedException;
	 void deleteCustomer (int costumerID) throws SQLException, InterruptedException;
	 ArrayList<Customer> getAllCustomers() throws SQLException, InterruptedException;
	 Customer getOneCustomer(int costumerID) throws SQLException, InterruptedException;
	 Customer getOneCustomerByEmail(String email) throws SQLException, InterruptedException;
}
