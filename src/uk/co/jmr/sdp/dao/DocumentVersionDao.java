package uk.co.jmr.sdp.dao;

import java.util.List;

import uk.co.jmr.sdp.domain.DocumentVersion;

public interface DocumentVersionDao {
	
	List<DocumentVersion> findDocumentVersionsByDocumentId(long documentId);
	DocumentVersion findDocumentVersionById(long id);
	List<DocumentVersion> findallDocumentVersion();
	DocumentVersion findDocumentVersionByDocumentName(String documentName);
	public void save(DocumentVersion documentVersion);
	public void delete(DocumentVersion documentVersion);
	

}
