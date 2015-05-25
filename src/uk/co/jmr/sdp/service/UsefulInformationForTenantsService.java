package uk.co.jmr.sdp.service;
//import uk.co.jmr.sdp.domain.BuilderDetailsForm;
import uk.co.jmr.sdp.domain.UsefulInformationForTenants;

public interface UsefulInformationForTenantsService {
	
	public void save(UsefulInformationForTenants usefulInformationForTenants);
	
	public UsefulInformationForTenants findUsefulInformationForTenantsDetailsFormById(long id);
	
	public UsefulInformationForTenants findUsefulInformationForTenantsDetailsFormByUserFormId(long userFormId);

}
