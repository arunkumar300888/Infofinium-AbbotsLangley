package uk.co.jmr.sdp.service;

import java.util.List;

import uk.co.jmr.sdp.domain.Inventory;
import uk.co.jmr.sdp.domain.PropertyDetailsForm;
import uk.co.jmr.sdp.domain.User;

public interface InventoryService {

	public void save(Inventory inventory);
	
	public Inventory findInventoryFormById(long id);
	
	public List<Inventory> findInventoryFormbyCreatedBy(User user);
	
	public List<Inventory> findAllInventory();
	
	public List<Inventory> findInventoryByPropertyId(PropertyDetailsForm propertyDetailsForm);
}
