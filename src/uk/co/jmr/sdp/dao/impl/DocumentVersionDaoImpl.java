package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.DocumentVersionDao;
import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.domain.DocumentVersion;
import uk.co.jmr.sdp.domain.User;

@Repository("doucmentVersionDao")
public class DocumentVersionDaoImpl implements DocumentVersionDao{

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentVersion> findDocumentVersionsByDocumentId(
			long documentId) {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from DocumentVersion where documentId=? order by id DESC",documentId);
	}

	@Override
	public DocumentVersion findDocumentVersionById(long id) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(DocumentVersion.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentVersion> findallDocumentVersion() {
		// TODO Auto-generated method stub
		return (List<DocumentVersion>) hibernateTemplate.find("from DocumentVersion");
	}

	@Override
	public void save(DocumentVersion documentVersion) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(documentVersion);
	}

	@Override
	public void delete(DocumentVersion documentVersion) {
		// TODO Auto-generated method stub
		hibernateTemplate.delete(documentVersion);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DocumentVersion findDocumentVersionByDocumentName(String documentName) {
		// TODO Auto-generated method stub
		List<DocumentVersion> dvs= hibernateTemplate.find("from DocumentVersion where documentName=?",documentName);
		if (dvs.size() >= 1)
			return dvs.get(0);
		return null;
	}

	
}
