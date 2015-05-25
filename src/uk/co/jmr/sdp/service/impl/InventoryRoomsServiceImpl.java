package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.InventoryRoomsDao;
import uk.co.jmr.sdp.domain.Inventory;
import uk.co.jmr.sdp.domain.InventoryRooms;
import uk.co.jmr.sdp.service.InventoryRoomsService;

@Service("inventoryRoomsService")
public class InventoryRoomsServiceImpl implements InventoryRoomsService{

	
	@Autowired
	private InventoryRoomsDao inventoryRoomsDao;

	@Override
	public void save(InventoryRooms inventoryRooms) {
		// TODO Auto-generated method stub
		this.inventoryRoomsDao.save(inventoryRooms);
	}

	@Override
	public void delete(List<InventoryRooms> inventoryRooms) {
		// TODO Auto-generated method stub
		this.inventoryRoomsDao.delete(inventoryRooms);
	}

	@Override
	public List<InventoryRooms> findInventoryRoomsForInventory(
			Inventory inventory) {
		// TODO Auto-generated method stub
		return this.inventoryRoomsDao.findInventoryRoomsForInventory(inventory);
	}
	
	
}
