package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.DocumentTrailDao;
import uk.co.jmr.sdp.domain.DocumentTrail;

@Repository("documentTrailDao")
public class DocumentTrailDaoImpl implements DocumentTrailDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void save(DocumentTrail documentTrail) {

		hibernateTemplate.saveOrUpdate(documentTrail);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentTrail> findDocTrailForDocument(long docId) {

		List<DocumentTrail> docTrails = hibernateTemplate.find("from DocumentTrail as d where d.document.id=?", docId);
		return docTrails;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentTrail> findDocTrailByLastDownloadedDocument() {
		// TODO Auto-generated method stub
		List<DocumentTrail> docTrail=hibernateTemplate.find("from DocumentTrail as d where d.action='Download' order by date desc");
		/*if(docTrail.size()>=1)
		return docTrail.get(0);*/
		return docTrail;
	}
	
}
