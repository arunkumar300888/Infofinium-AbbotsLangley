package uk.co.jmr.webforms.db.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.webforms.db.dao.ArrayItemsDao;
import uk.co.jmr.webforms.db.pojo.ArrayItems;
import uk.co.jmr.webforms.db.service.ArrayItemsService;

@Service("arrayItemsService")
public class ArrayItemsServiceImpl implements ArrayItemsService {
	@Autowired
	private ArrayItemsDao arrayItemsDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ArrayItems findArrayItemsById(long id) {

		return this.arrayItemsDao.findArrayItemsById(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(ArrayItems obj) {

		this.arrayItemsDao.save(obj);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(long id) {

		ArrayItems obj = this.arrayItemsDao.findArrayItemsById(id);
		this.arrayItemsDao.delete(obj);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ArrayItems> findAllArrayItems() {

		return this.arrayItemsDao.findAllArrayItems();
	}
}
