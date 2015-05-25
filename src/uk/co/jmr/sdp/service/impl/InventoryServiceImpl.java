package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.InventoryDao;
import uk.co.jmr.sdp.domain.Inventory;
import uk.co.jmr.sdp.domain.PropertyDetailsForm;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.service.InventoryService;

@Service("inventoryForm1Service")
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private InventoryDao inventoryFormDao;

	@Override
	public void save(Inventory inventoryForm) {
		// TODO Auto-generated method stub
		this.inventoryFormDao.save(inventoryForm);
	}

	@Override
	public Inventory findInventoryFormById(long id) {
		// TODO Auto-generated method stub
		return this.inventoryFormDao.findInventoryFormById(id);
	}

	@Override
	public List<Inventory> findInventoryFormbyCreatedBy(User user) {
		// TODO Auto-generated method stub
		return this.inventoryFormDao.findInventoryFormbyCreatedBy(user);
	}

	@Override
	public List<Inventory> findAllInventory() {
		// TODO Auto-generated method stub
		return this.inventoryFormDao.findAllInventory();
	}

	@Override
	public List<Inventory> findInventoryByPropertyId(
			PropertyDetailsForm propertyDetailsForm) {
		// TODO Auto-generated method stub
		return this.inventoryFormDao.findInventoryByPropertyId(propertyDetailsForm);
	}

	
}
