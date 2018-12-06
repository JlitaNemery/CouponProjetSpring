package dbdao;

import java.sql.Connection;
import java.time.format.DateTimeFormatter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import beans.*;
import connectionPool.*;

/**
 * 
 * this class handles access to the coupon table
 * CUSTOMERS_VS_COUPONS table
 * and category table
 *
 */

public class CouponsDBDAO implements CouponsDAO {
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");

	
	@Override
	public boolean isCouponExists(int couponID) throws SQLException, InterruptedException {

		Connection connection = ConnectionPool.getInstance().getConnection();
		try {

			String sql = String.format("select * from COUPONS where ID=%d",couponID);

			try(PreparedStatement preparedStatment = connection.prepareStatement(sql)){

				try(ResultSet resultSet = preparedStatment.executeQuery()){
					while(resultSet.next()) {
						int resultID = resultSet.getInt("ID");
						if(resultID != 0) {
							return true;
						}
					}

				}
			}
		}
		catch(Exception ex) {

		}
		finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}	
		return false;

	}

	@Override
	public int addCoupon(Coupon coupon) throws SQLException, InterruptedException {

		Connection connection = ConnectionPool.getInstance().getConnection();

		try {

			String sql = String.format(
					"insert into COUPONS(COMPANY_ID, TITLE, START_DATE, END_DATE, AMOUNT, CATEGORY_ID, DESCRIPTION, PRICE, IMAGE) values(%d,'%s', '%s', '%s', %d, %d, '%s', %.2f, '%s')",
					coupon.getCompanyId(),coupon.getTitle(), coupon.getStartDate(), coupon.getEndDate(), coupon.getAmount(),
					getCouponTypeID(coupon.getCategory()), coupon.getDescription(), coupon.getPrice(), coupon.getImage());

			try(PreparedStatement preparedStatement = 
					connection.prepareStatement(sql, 
							PreparedStatement.RETURN_GENERATED_KEYS)){

				preparedStatement.executeUpdate();

				try(ResultSet resultSet = preparedStatement.getGeneratedKeys()){
					resultSet.next();
					int id = resultSet.getInt(1);
					coupon.setId(id);
					System.out.println("Insert succeeded. New coupon id: " + id);
					return id;
				}
			}
		}

		finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	@Override
	public void updateCoupon(Coupon coupon) throws SQLException, InterruptedException {
		Connection connection = ConnectionPool.getInstance().getConnection();

		try {

			String sql = String.format(
					"update COUPONS set COMPANY_ID=%d, TITLE='%s', START_DATE='%s', "+
							"END_DATE='%s', AMOUNT=%d, CATEGORY_ID=%d, DESCRIPTION='%s', PRICE=%.2f, IMAGE='%s'  where ID=%d", 
							coupon.getCompanyId() ,coupon.getTitle(), coupon.getStartDate(), coupon.getEndDate(),
							coupon.getAmount(), getCouponTypeID(coupon.getCategory()), coupon.getDescription(),
							coupon.getPrice(), coupon.getImage(), coupon.getId());

			try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

				preparedStatement.executeUpdate();


			}
		}
		catch(Exception ex) {

		}
		finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	@Override
	public void deleteCoupon(int couponID) throws SQLException, InterruptedException {

		Connection connection = ConnectionPool.getInstance().getConnection();

		try {

			String sql = String.format("delete from COUPONS where ID=%d", couponID);

			try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

				preparedStatement.executeUpdate();

				System.out.println("Delete succeeded.");
			}
		}
		catch(Exception ex) {

		}
		finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	@Override
	public ArrayList<Coupon> getAllCoupons() throws SQLException, InterruptedException {

		Connection connection = ConnectionPool.getInstance().getConnection();

		try {

			ArrayList<Coupon> couponsArr = new ArrayList<>();

			String sql = "select * from COUPONS";

			try(PreparedStatement preparedStatment = connection.prepareStatement(sql)){

				try(ResultSet resultSet = preparedStatment.executeQuery()){

					while(resultSet.next()) {
						Coupon coupon = new Coupon(resultSet.getInt("ID"),resultSet.getInt("COMPANY_ID"),
								getCouponType(resultSet.getInt("CATEGORY_ID")), resultSet.getString("TITLE"),
								resultSet.getString("DESCRIPTION"), resultSet.getString("START_DATE"), 
								resultSet.getString("END_DATE"), resultSet.getInt("AMOUNT"),
								resultSet.getDouble("PRICE"), resultSet.getString("IMAGE")); 
						couponsArr.add(coupon);
					}
					return couponsArr;

				}
			}
		}
		catch(Exception ex) {

		}
		finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}

		return null;
	}

	@Override
	public Coupon getOneCoupon(int couponID) throws SQLException, InterruptedException {

		Connection connection = ConnectionPool.getInstance().getConnection();

		try {

			String sql = String.format("select * from COUPONS where ID=%d", couponID);

			try(PreparedStatement preparedStatment = connection.prepareStatement(sql)){

				try(ResultSet resultSet = preparedStatment.executeQuery()){

					while(resultSet.next()) {
						Coupon coupon = new Coupon(resultSet.getInt("ID"),resultSet.getInt("COMPANY_ID"),
								getCouponType(resultSet.getInt("CATEGORY_ID")), resultSet.getString("TITLE"),
								resultSet.getString("DESCRIPTION"), resultSet.getString("START_DATE"), 
								resultSet.getString("END_DATE"), resultSet.getInt("AMOUNT"),
								resultSet.getDouble("PRICE"), resultSet.getString("IMAGE"));
						return coupon;
					}

				}
			}
		}
		catch(Exception ex) {

		}
		finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
		return null;

	}

	
/**
 * this function adds a purchase of a specified coupon to the CUSTOMERS_VS_COUPONS table
 */
	@Override
	public void addCouponPurchase(int customerID, int couponID) throws SQLException, InterruptedException {

		Connection connection = ConnectionPool.getInstance().getConnection();

		try {

			String sql = String.format(
					"insert into CUSTOMERS_VS_COUPONS(CUSTOMER_ID, COUPON_ID) values(%d, %d)", customerID, couponID);


			try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

				preparedStatement.executeUpdate();


			}
		}

		finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}
/**
 * this function deletes a purchase of a specified coupon to the CUSTOMERS_VS_COUPONS table
 * using coustomerID and couponID
 */
	@Override
	public void deleteCouponPurchase(int customerID, int couponID) throws SQLException, InterruptedException {

		Connection connection = ConnectionPool.getInstance().getConnection();

		try {

			String sql = String.format("delete from CUSTOMERS_VS_COUPONS where CUSTOMER_ID=%d AND COUPON_ID=%d", customerID, couponID);

			try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

				preparedStatement.executeUpdate();

			}
		}
		catch(Exception ex) {

		}
		finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}


	}
/**
 * this function deletes a purchase of a specified coupon to the CUSTOMERS_VS_COUPONS table
 * using couponID
 */
	@Override
	public void deleteCouponPurchaseByCoupon(int couponID) throws SQLException, InterruptedException {

		Connection connection = ConnectionPool.getInstance().getConnection();

		try {

			String sql = String.format("delete from CUSTOMERS_VS_COUPONS where COUPON_ID=%d", couponID);

			try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

				preparedStatement.executeUpdate();

			}
		}
		catch(Exception ex) {

		}
		finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}
/**
 * this function deletes a purchase of a specified coupon to the CUSTOMERS_VS_COUPONS table
 * using coustomerID
 */
	@Override
	public void deleteCouponPurchaseByCustomer(int customerID) throws SQLException, InterruptedException {

		Connection connection = ConnectionPool.getInstance().getConnection();

		try {

			String sql = String.format("delete from CUSTOMERS_VS_COUPONS where CUSTOMER_ID=%d", customerID);

			try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

				preparedStatement.executeUpdate();

			}
		}
		catch(Exception ex) {

		}
		finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}	
	}

	//Get coupons of costumers and companies -------------------------------------------------------------------------------------------------

/**
 * this function returns all coupons that a customer purchased by customerID
 */
	@Override
	public ArrayList<Coupon> getCouponByCustomerID(int customerID) throws InterruptedException, SQLException{
		Connection connection = ConnectionPool.getInstance().getConnection();
		try {
			ArrayList<Coupon> couponsArr = new ArrayList<>();
			String sql =String.format("select * from COUPONS JOIN CUSTOMERS_VS_COUPONS " +
					"on COUPONS.ID=CUSTOMERS_VS_COUPONS.COUPON_ID " +
					"where CUSTOMER_ID=%d", customerID);
			try(PreparedStatement preparedStatment = connection.prepareStatement(sql)){

				try(ResultSet resultSet = preparedStatment.executeQuery()){
					while(resultSet.next()) {
						Coupon coupon = new Coupon(resultSet.getInt("ID"),resultSet.getInt("COMPANY_ID"),
								getCouponType(resultSet.getInt("CATEGORY_ID")), resultSet.getString("TITLE"),
								resultSet.getString("DESCRIPTION"),resultSet.getString("START_DATE"), 
								resultSet.getString("END_DATE"), resultSet.getInt("AMOUNT"),
								resultSet.getDouble("PRICE"), resultSet.getString("IMAGE"));
						couponsArr.add(coupon);
					}
					return couponsArr;
				}
			}
		}
		catch(Exception ex) {

		}
		finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
		return null;
	}

/**
 * this function returns all coupons from specified company by companyID
 */
	@Override
	public ArrayList<Coupon> getAllCouponsFromCompany(int companyID) throws SQLException, InterruptedException {

		Connection connection = ConnectionPool.getInstance().getConnection();

		try {

			ArrayList<Coupon> couponsArr = new ArrayList<>();


			String sql = String.format("select * from COUPONS where COMPANY_ID=%d", companyID);

			try(PreparedStatement preparedStatment = connection.prepareStatement(sql)){

				try(ResultSet resultSet = preparedStatment.executeQuery()){

					while(resultSet.next()) {
						Coupon coupon = new Coupon(resultSet.getInt("ID"),resultSet.getInt("COMPANY_ID"),
								getCouponType(resultSet.getInt("CATEGORY_ID")), resultSet.getString("TITLE"),
								resultSet.getString("DESCRIPTION"), resultSet.getString("START_DATE"), 
								resultSet.getString("END_DATE"), resultSet.getInt("AMOUNT"),
								resultSet.getDouble("PRICE"), resultSet.getString("IMAGE")); 
						couponsArr.add(coupon);
					}
					return couponsArr;
				}
			}
		}
		catch(Exception ex) {

		}
		finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
		return null;		
	}

	/**
	 * this function returns couponID for coupon of a specified category
	 * from category table
	 */

	@Override
	public int getCouponTypeID(Category couponType) throws SQLException, InterruptedException {
		Connection connection = ConnectionPool.getInstance().getConnection();

		try {

			String sql =String.format("select * from CATEGORIES where NAME='%s'" , couponType);

			try(PreparedStatement preparedStatment = connection.prepareStatement(sql)){

				try(ResultSet resultSet = preparedStatment.executeQuery()){

					while(resultSet.next()) {

						return resultSet.getInt("ID");
					}
				}
			}
		}
		catch(Exception ex) {

		}
		finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
		return 0;
	}
	
/**
 * this function returns category of a coupon by couponID
 * from category table
 */	
	public Category getCouponType(int couponTypeID) throws SQLException, InterruptedException {
		Connection connection = ConnectionPool.getInstance().getConnection();

		try {

			String sql =String.format("select * from CATEGORIES where ID=%d" , couponTypeID);

			try(PreparedStatement preparedStatment = connection.prepareStatement(sql)){

				try(ResultSet resultSet = preparedStatment.executeQuery()){

					while(resultSet.next()) {
						return Category.getType(resultSet.getString("NAME"));
					}
				}
			}
		}
		catch(Exception ex) {

		}
		finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
		return null;
	}
/**
 * this function gets all available 
 */
	public ArrayList<Coupon> getAllAvailableCoupons(int customerID) throws SQLException, InterruptedException {
		System.out.println("couopnsDBDAO"+customerID);
		Connection connection = ConnectionPool.getInstance().getConnection();

		try {

			ArrayList<Coupon> couponsArr = new ArrayList<>();

			String sql = "select * from COUPONS";

			try(PreparedStatement preparedStatment = connection.prepareStatement(sql)){

				try(ResultSet resultSet = preparedStatment.executeQuery()){

					while(resultSet.next()) {
						Coupon coupon = new Coupon(resultSet.getInt("ID"),resultSet.getInt("COMPANY_ID"),
								getCouponType(resultSet.getInt("CATEGORY_ID")), resultSet.getString("TITLE"),
								resultSet.getString("DESCRIPTION"), resultSet.getString("START_DATE"), 
								resultSet.getString("END_DATE"), resultSet.getInt("AMOUNT"),
								resultSet.getDouble("PRICE"), resultSet.getString("IMAGE")); 
						couponsArr.add(coupon);
//			----------------	
						for (Coupon c : getCouponByCustomerID(customerID)) {
							if(coupon.getId()==c.getId()) {
								couponsArr.remove(coupon);
							}
							
						}												
					}
					return couponsArr;
				}
			}
		}
		catch(Exception ex) {

		}
		finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}

		return null;
	}

	
}


