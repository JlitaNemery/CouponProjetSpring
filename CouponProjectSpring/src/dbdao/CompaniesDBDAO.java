package dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import beans.*;
import connectionPool.*;

/**
 * this class handles access to the companies table
 * 
 *
 */

public class CompaniesDBDAO implements CompaniesDAO {

	CouponsDBDAO couponsDAO = new CouponsDBDAO();


	@Override
	public boolean isCompanyExists(String email, String password) throws SQLException, InterruptedException {

		Connection connection = ConnectionPool.getInstance().getConnection();

		try {

			String sql = String.format("select * from COMPANIES where EMAIL='%s' AND PASSWORD='%s'",email,password);

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
	public int addCompany(Company company) throws SQLException, InterruptedException {

		Connection connection = ConnectionPool.getInstance().getConnection();

		try {

			String sql = String.format(
					"insert into COMPANIES(NAME, PASSWORD, EMAIL) values('%s', '%s', '%s')", company.getName(), company.getPassword(), company.getEmail());

			try(PreparedStatement preparedStatement = 
					connection.prepareStatement(sql, 
							PreparedStatement.RETURN_GENERATED_KEYS)){

				preparedStatement.executeUpdate();

				try(ResultSet resultSet = preparedStatement.getGeneratedKeys()){
					resultSet.next();
					int id = resultSet.getInt(1);
					company.setId(id);
					System.out.println("Insert succeeded. New create id: " + id);
					return id;
				}
			}
		}

		finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}

	}

	@Override
	public void updateCompany(Company company) throws SQLException, InterruptedException {
		Connection connection = ConnectionPool.getInstance().getConnection();

		try {

			String sql = String.format(
					"update COMPANIES set NAME='%s', PASSWORD='%s', EMAIL='%s' where ID=%d", 
					company.getName() ,company.getPassword() , company.getEmail(), company.getId());

			try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

				preparedStatement.executeUpdate();

			}
		}

		finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}


	}

	@Override
	public void deleteCompany(int companyID) throws SQLException, InterruptedException {

		Connection connection = ConnectionPool.getInstance().getConnection();

		try {

			String sql = String.format("delete from COMPANIES where ID=%d", companyID);

			try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

				preparedStatement.executeUpdate();

				System.out.println("Delete succeeded.");
			}
		}

		finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	@Override
	public ArrayList<Company> getAllCompanies() throws SQLException, InterruptedException {

		Connection connection = ConnectionPool.getInstance().getConnection();

		try {

			ArrayList<Company> companyArr = new ArrayList<>();

			String sql = "select * from COMPANIES";

			try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){


				try(ResultSet resultSet = preparedStatement.executeQuery()){

					while(resultSet.next()) {
						Company company = new Company(resultSet.getInt("ID"), resultSet.getString("NAME"),
								resultSet.getString("EMAIL"), resultSet.getString("PASSWORD"), 
								couponsDAO.getAllCouponsFromCompany(resultSet.getInt("ID"))); 
						companyArr.add(company);
					}
					return companyArr;
				}
			}
		}

		finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	@Override
	public Company getOneCompany(int companyID) throws SQLException, InterruptedException {

		Connection connection = ConnectionPool.getInstance().getConnection();

		try {

			String sql = String.format("select * from COMPANIES where ID=%d", companyID);

			try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

				try(ResultSet resultSet = preparedStatement.executeQuery()){

					while(resultSet.next()) {
						Company company = new Company(resultSet.getInt("ID"), resultSet.getString("NAME"), 
								resultSet.getString("EMAIL"), resultSet.getString("PASSWORD"),
								couponsDAO.getAllCouponsFromCompany(resultSet.getInt("ID"))); 
						return company;
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
 * this function returns one company by email
 * 	
 */
	@Override
	public Company getOneCompanyByEmail(String companyEmail) throws SQLException, InterruptedException {
		
		Connection connection = ConnectionPool.getInstance().getConnection();
		
		try {
			
			String sql = String.format("select * from COMPANIES where EMAIL='%s'", companyEmail);
			
			try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
				
				try(ResultSet resultSet = preparedStatement.executeQuery()){
					
					while(resultSet.next()) {
						Company company = new Company(resultSet.getInt("ID"), resultSet.getString("NAME"), 
								resultSet.getString("EMAIL"), resultSet.getString("PASSWORD"),
								couponsDAO.getAllCouponsFromCompany(resultSet.getInt("ID"))); 
						return company;
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
