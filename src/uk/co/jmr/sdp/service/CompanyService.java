package uk.co.jmr.sdp.service;

import java.util.List;

import uk.co.jmr.sdp.domain.Company;

public interface CompanyService {
	
	public List<Company> findAllCompany();
	
	public void save(Company company);
	
	public void delete(Company company);
	
	public Company findCompanyById(long id);
	
	public Company findCompanyByName(String companyName);
}
