package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.MortgageDao;
import uk.co.jmr.sdp.domain.Mortgage;
import uk.co.jmr.sdp.domain.PropertyDetailsForm;
import uk.co.jmr.sdp.service.MortgageService;

@Service("mortgageService")
public class MortgageServiceImpl implements MortgageService{
	
	@Autowired
	private MortgageDao mortgageDao;

	@Override
	public void save(Mortgage mortgage) {
		// TODO Auto-generated method stub
		this.mortgageDao.save(mortgage);
	}

	@Override
	public List<Mortgage> findAllMortgages() {
		// TODO Auto-generated method stub
		return this.mortgageDao.findAllMortgages();
	}

	@Override
	public List<Mortgage> findMortgagesForPropertyDetailsForm(
			PropertyDetailsForm propertyDetailsForm) {
		// TODO Auto-generated method stub
		return this.mortgageDao.findMortgagesForPropertyDetailsForm(propertyDetailsForm);
	}

	@Override
	public Mortgage findMortgageById(long id) {
		// TODO Auto-generated method stub
		return this.mortgageDao.findMortgageById(id);
	}
	
	
	

}
