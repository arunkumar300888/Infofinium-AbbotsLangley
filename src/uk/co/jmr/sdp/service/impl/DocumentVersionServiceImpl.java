package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.DocumentVersionDao;
import uk.co.jmr.sdp.domain.DocumentVersion;
import uk.co.jmr.sdp.service.DocumentVersionService;

@Service("documentServiceImpl")
public class DocumentVersionServiceImpl implements DocumentVersionService{

	@Autowired
	private DocumentVersionDao documentVersionDao;
	
	@Override
	public List<DocumentVersion> findDocumentVersionsByDocumentId(
			long documentId) {
		// TODO Auto-generated method stub
		return this.documentVersionDao.findDocumentVersionsByDocumentId(documentId);
	}

	@Override
	public DocumentVersion findDocumentVersionById(long id) {
		// TODO Auto-generated method stub
		return this.documentVersionDao.findDocumentVersionById(id);
	}

	@Override
	public List<DocumentVersion> findallDocumentVersion() {
		// TODO Auto-generated method stub
		return this.documentVersionDao.findallDocumentVersion();
	}

	@Override
	public void save(DocumentVersion documentVersion) {
		// TODO Auto-generated method stub
		this.documentVersionDao.save(documentVersion);
	}

	@Override
	public void delete(DocumentVersion documentVersion) {
		// TODO Auto-generated method stub
		this.documentVersionDao.delete(documentVersion);
	}

	@Override
	public DocumentVersion findDocumentVersionByDocumentName(String documentName) {
		// TODO Auto-generated method stub
		return this.documentVersionDao.findDocumentVersionByDocumentName(documentName);
	}

}
