package uk.co.jmr.sdp.service;

import java.util.List;

import uk.co.jmr.sdp.domain.Mortgage;
import uk.co.jmr.sdp.domain.PropertyDetailsForm;

public interface MortgageService {

	public void save(Mortgage mortgage);
	public List<Mortgage> findAllMortgages();
	public List<Mortgage> findMortgagesForPropertyDetailsForm(PropertyDetailsForm propertyDetailsForm);
	public Mortgage findMortgageById(long id);
}
