package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.InventoryRoomsDao;
import uk.co.jmr.sdp.domain.Inventory;
import uk.co.jmr.sdp.domain.InventoryRooms;

@Repository("inventoryRoomsDao")
public class InventoryRoomsDaoImpl implements InventoryRoomsDao{

	
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void save(InventoryRooms inventoryRooms) {
		// TODO Auto-generated method stub
		hibernateTemplate.save(inventoryRooms);
	}

	@Override
	public void delete(List<InventoryRooms> inventoryRooms) {
		// TODO Auto-generated method stub
		hibernateTemplate.deleteAll(inventoryRooms);
	}

	

	@SuppressWarnings("unchecked")
	@Override
	public List<InventoryRooms> findInventoryRoomsForInventory(
			Inventory inventory) {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from InventoryRooms where inventory=?",inventory);
	}



	
	
}
