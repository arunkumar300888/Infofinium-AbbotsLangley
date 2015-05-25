package uk.co.jmr.sdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.InspectionInventoryDao;
import uk.co.jmr.sdp.domain.InspectionInventory;
import uk.co.jmr.sdp.service.InspectionInventoryService;

@Service("inventoryForm2Service")
public class InspectionInventoryServiceImpl implements InspectionInventoryService {

	@Autowired
	private InspectionInventoryDao inventoryFormDao;

	@Override
	public void save(InspectionInventory inventoryForm) {
		// TODO Auto-generated method stub
		this.inventoryFormDao.save(inventoryForm);
	}

	@Override
	public InspectionInventory findInventoryFormById(long id) {
		// TODO Auto-generated method stub
		return this.inventoryFormDao.findInventoryFormById(id);
	}

	@Override
	public InspectionInventory findInventoryFormByUserFormId(long userFormId) {
		// TODO Auto-generated method stub
		return this.findInventoryFormByUserFormId(userFormId);
	}
}
