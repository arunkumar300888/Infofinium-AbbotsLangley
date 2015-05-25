package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.InspectionInventoryDao;
import uk.co.jmr.sdp.domain.InspectionInventory;

@Repository("inventoryForm2Dao")
public class InspectionInventoryDaoImpl implements InspectionInventoryDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void save(InspectionInventory inventoryForm) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(inventoryForm);
	}

	@Override
	public InspectionInventory findInventoryFormById(long id) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(InspectionInventory.class, id);
	}

	@Override
	public InspectionInventory findInventoryFormByUserFormId(long userFormId) {
		// TODO Auto-generated method stub
		List<InspectionInventory> forms=hibernateTemplate.find("from InventoryForm2 where userFormId=?",userFormId);
		if(forms.size()>=1)
			return forms.get(0);
		return null;
	}
}
