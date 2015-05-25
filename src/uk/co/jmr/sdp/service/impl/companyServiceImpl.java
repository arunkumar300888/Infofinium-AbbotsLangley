package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.CompanyDao;
import uk.co.jmr.sdp.domain.Company;
import uk.co.jmr.sdp.service.CompanyService;

@Service("companyService")
public class companyServiceImpl implements CompanyService{
	
	@Autowired
	private CompanyDao companyDao;

	@Override
	public List<Company> findAllCompany() {
		// TODO Auto-generated method stub
		return this.companyDao.findAllCompany();
	}

	@Override
	public void save(Company company) {
		// TODO Auto-generated method stub
		this.companyDao.save(company);
	}

	@Override
	public void delete(Company company) {
		// TODO Auto-generated method stub
		this.companyDao.delete(company);
	}

	@Override
	public Company findCompanyById(long id) {
		// TODO Auto-generated method stub
		return this.companyDao.findCompanyById(id);
	}

	@Override
	public Company findCompanyByName(String companyName) {
		// TODO Auto-generated method stub
		return this.companyDao.findCompanyByName(companyName);
	}

}
