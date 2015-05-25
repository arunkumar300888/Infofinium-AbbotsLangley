package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.CompanyDao;
import uk.co.jmr.sdp.domain.Company;

@Repository("companyDao")
public class CompanyDaoImpl implements CompanyDao{
	
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<Company> findAllCompany() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from Company");
	}

	@Override
	public void save(Company company) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(company);
	}

	@Override
	public void delete(Company company) {
		// TODO Auto-generated method stub
		hibernateTemplate.delete(company);
	}

	@Override
	public Company findCompanyById(long id) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(Company.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Company findCompanyByName(String companyName) {
		// TODO Auto-generated method stub
		List<Company> comps=hibernateTemplate.find("from Company where companyName=?",companyName);
		if(comps.size()>=1)
			return comps.get(0);
		return null;
	}
	
	

}
