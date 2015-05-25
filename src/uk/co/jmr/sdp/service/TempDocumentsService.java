package uk.co.jmr.sdp.service;

import java.util.List;

import uk.co.jmr.sdp.domain.TempDocuments;

public interface TempDocumentsService {

	public void save(TempDocuments tempDocuments);
	
	void delete(TempDocuments tempDocuments);
	
	List<TempDocuments> findAllTempDocuments();
	
	TempDocuments findTempDocumentsByDocumentName(String documentName);

}