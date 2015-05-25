package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.TempDocumentDao;
import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.domain.TempDocuments;

@Repository("tempDocumentDao")
public class TempDocumentsDaoImpl implements TempDocumentDao{

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public void save(TempDocuments tempDocuments) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(tempDocuments);
	}

	@Override
	public void delete(TempDocuments tempDocuments) {
		// TODO Auto-generated method stub
		hibernateTemplate.delete(tempDocuments);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TempDocuments> findAllTempDocuments() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from TempDocuments");
	}

	@SuppressWarnings("unchecked")
	@Override
	public TempDocuments findTempDocumentsByDocumentName(String documentName) {
		// TODO Auto-generated method stub
		List<TempDocuments> docs = hibernateTemplate.find("from TempDocuments where name=?",documentName);
		// System.out.println("***** Out");
		if (docs.size() >= 1)
			return docs.get(0);
		return null;
		
	}

}
