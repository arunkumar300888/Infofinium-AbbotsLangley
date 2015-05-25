package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.InventoryDao;
import uk.co.jmr.sdp.domain.Inventory;
import uk.co.jmr.sdp.domain.PropertyDetailsForm;
import uk.co.jmr.sdp.domain.User;

@Repository("inventoryForm1Dao")
public class InventoryDaoImpl implements InventoryDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void save(Inventory inventoryForm) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(inventoryForm);
	}

	@Override
	public Inventory findInventoryFormById(long id) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(Inventory.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Inventory> findInventoryFormbyCreatedBy(User user) {
		// TODO Auto-generated method stub
		List<Inventory> forms= hibernateTemplate.find("from Inventory where createdBy=?",user);
		/*if(forms.size()>=1)
			return forms.get(0);*/
		return forms;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Inventory> findAllInventory() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from Inventory");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Inventory> findInventoryByPropertyId(
			PropertyDetailsForm propertyDetailsForm) {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from Inventory where propertyDetailsForm=?",propertyDetailsForm);
	} 
}
