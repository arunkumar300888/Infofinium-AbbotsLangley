package uk.co.jmr.sdp.dao;

import java.util.List;

import uk.co.jmr.sdp.domain.Inventory;
import uk.co.jmr.sdp.domain.InventoryRooms;

public interface InventoryRoomsDao {
	
	public void save(InventoryRooms inventoryRooms);
	
	public void delete(List<InventoryRooms> inventoryRooms);
	
	 List<InventoryRooms> findInventoryRoomsForInventory(Inventory inventory);

	
	
	

}
