package uk.co.jmr.sdp.dao;

import java.util.List;

import uk.co.jmr.sdp.domain.CheckingOutForm;
import uk.co.jmr.sdp.domain.User;


public interface CheckingOutFormDao {
	
	public void save(CheckingOutForm checkingOutForm);
	
	
	CheckingOutForm findCheckingOutFormById(long id);
	
	List<CheckingOutForm> findCheckingOutFormByCreatedBy(User user);
	
	List<CheckingOutForm> findAllCheckingOut();

}
