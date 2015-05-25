package uk.co.jmr.sdp.service;

import uk.co.jmr.sdp.domain.InspectionInventory;

public interface InspectionInventoryService {

	public void save(InspectionInventory inspectionInventory);
	
	public InspectionInventory findInventoryFormById(long id);
	
	public InspectionInventory findInventoryFormByUserFormId(long userFormId);
}
