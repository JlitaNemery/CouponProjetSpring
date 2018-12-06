package dbdao;

import java.sql.SQLException;
import java.util.ArrayList;
import beans.*;
import exceptions.CompanyDoesntExistException;

public interface CompaniesDAO {
	boolean isCompanyExists(String email, String password) throws SQLException, InterruptedException;
	int addCompany(Company company) throws SQLException, InterruptedException;
	void updateCompany(Company company) throws SQLException, InterruptedException;
	void deleteCompany(int companyID) throws SQLException, InterruptedException;
	ArrayList<Company> getAllCompanies() throws SQLException, InterruptedException;
	Company getOneCompany (int companyID) throws SQLException, InterruptedException;
	Company getOneCompanyByEmail(String companyEmail) throws SQLException, InterruptedException, CompanyDoesntExistException;
}
