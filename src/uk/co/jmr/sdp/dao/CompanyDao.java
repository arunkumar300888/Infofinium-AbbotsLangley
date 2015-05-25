package uk.co.jmr.sdp.dao;

import java.util.List;

import uk.co.jmr.sdp.domain.Company;

public interface CompanyDao {

	public List<Company> findAllCompany();
	
	public void save(Company company);
	
	public void delete(Company company);
	
	public Company findCompanyById(long id);
	
	public Company findCompanyByName(String companyName);
}
