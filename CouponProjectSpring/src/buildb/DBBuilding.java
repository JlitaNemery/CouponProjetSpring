package buildb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import connectionPool.*;
/**
 * this class creates the DB
 * 
 *
 */
public class DBBuilding {


	public static void buildCompaniesDB() throws SQLException, InterruptedException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			try {
				String sql = "create table COMPANIES (" +
						"ID integer not null primary key " +
						"generated always as identity(start with 1, increment by 1), " +
						"NAME varchar(50) not null, " +
						"EMAIL varchar(50) not null, " +
						"PASSWORD varchar(30) not null)";

				try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

					preparedStatement.executeUpdate();

					System.out.println("Companies Products table has been created.");			
				}
			}

			finally {
				ConnectionPool.getInstance().restoreConnection(connection);
			}

		}
		catch(SQLException ex) {
			if(ex.getSQLState().equals("X0Y32")) {
				return;
			}
			throw ex;
		}

	}
	public static void buildCustomersDB() throws SQLException, InterruptedException {
		try {

			Connection connection = ConnectionPool.getInstance().getConnection();
			try {
				String sql = "create table CUSTOMERS (" +
						"ID integer not null primary key " +
						"generated always as identity(start with 1, increment by 1), " +
						"FIRST_NAME varchar(50) not null, " +
						"LAST_NAME varchar(50) not null, " +
						"EMAIL varchar(50) not null, " +
						"PASSWORD varchar(30) not null)";
				try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

					preparedStatement.executeUpdate();

					System.out.println("Costumers Products table has been created.");			
				}

			}

			finally {
				ConnectionPool.getInstance().restoreConnection(connection);
			}

		}
		catch(SQLException ex) {
			if(ex.getSQLState().equals("X0Y32")) {
				return;
			}
			throw ex;
		}
	}
	public static void buildCouponsDB() throws SQLException, InterruptedException {
		try {

			Connection connection = ConnectionPool.getInstance().getConnection();
			try {

				String sql = "create table COUPONS (" +
						"ID integer not null primary key " +
						"generated always as identity(start with 1, increment by 1), " +
						"COMPANY_ID bigint not null, " +
						"CATEGORY_ID bigint not null, " +
						"TITLE varchar(50) not null, " +
						"DESCRIPTION varchar (250) not null, " +
						"START_DATE date not null, " +
						"END_DATE date not null, " +
						"AMOUNT integer not null, " +
						"PRICE double not null, " +
						"IMAGE varchar(50) not null)";

				try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

					preparedStatement.executeUpdate();

					System.out.println("Coupons Products table has been created.");			
				}

			}

			finally {
				ConnectionPool.getInstance().restoreConnection(connection);
			}

		}
		catch(SQLException ex) {
			if(ex.getSQLState().equals("X0Y32")) {
				return;
			}
			throw ex;
		}
	}
	public static void buildCATEGORIESDB() throws SQLException, InterruptedException {
		try {

			Connection connection = ConnectionPool.getInstance().getConnection();
			try {

				String sql = "create table CATEGORIES (" +
						"ID integer not null primary key " +
						"generated always as identity(start with 1, increment by 1), " +
						"NAME varchar(50) not null)";

				try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

					preparedStatement.executeUpdate();

					System.out.println("CATEGORIES Products table has been created.");			
				}

				String sql2 = "insert into CATEGORIES(NAME) values('FOOD')," +
						"('ELECTRICITY'), ('RESTAURANT'), ('VACATION')," + 
						"('FASHION'), ('HOME_DEPOT'), ('TOYS'), ('GARDEN'), ('MUSICAL_INSTRUMENTS')";

				try(PreparedStatement preparedStatement = connection.prepareStatement(sql2)){

					preparedStatement.executeUpdate();

					System.out.println("CATEGORIES Products table inserted to.");			
				}
			}

			finally {
				ConnectionPool.getInstance().restoreConnection(connection);
			}

		}
		catch(SQLException ex) {
			if(ex.getSQLState().equals("X0Y32")) {
				return;
			}
			throw ex;
		}
	}
	public static void buildCustomerVsCouponsDB() throws SQLException, InterruptedException {
		try {

			Connection connection = ConnectionPool.getInstance().getConnection();

			try {

				String sql = "create table CUSTOMERS_VS_COUPONS (" +
						"CUSTOMER_ID integer, " +
						"COUPON_ID integer, " +
						"primary key (CUSTOMER_ID,COUPON_ID))";

				try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

					preparedStatement.executeUpdate();

					System.out.println("CustomerVsCouponsDB Products table has been created.");			
				}
			}


			finally {
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
		catch(SQLException ex) {
			if(ex.getSQLState().equals("X0Y32")) {
				return;
			}
			throw ex;
		}
	}

}
