package uk.co.jmr.sdp.dao;

import uk.co.jmr.sdp.domain.InspectionInventory;

public interface InspectionInventoryDao {

	public void save(InspectionInventory inspectionInventory);
	
	public InspectionInventory findInventoryFormById(long id);
	
	public InspectionInventory findInventoryFormByUserFormId(long userFormId);
}
