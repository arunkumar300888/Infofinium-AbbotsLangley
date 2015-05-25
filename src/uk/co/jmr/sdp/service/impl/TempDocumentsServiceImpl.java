package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.TempDocumentDao;
import uk.co.jmr.sdp.domain.TempDocuments;
import uk.co.jmr.sdp.service.TempDocumentsService;

@Service("tempDocumentsService")
public class TempDocumentsServiceImpl implements TempDocumentsService{

	@Autowired
	private TempDocumentDao tempDocumentDao;

	@Override
	public void save(TempDocuments tempDocuments) {
		// TODO Auto-generated method stub
		this.tempDocumentDao.save(tempDocuments);
	}

	@Override
	public void delete(TempDocuments tempDocuments) {
		// TODO Auto-generated method stub
		this.tempDocumentDao.delete(tempDocuments);
	}

	@Override
	public List<TempDocuments> findAllTempDocuments() {
		// TODO Auto-generated method stub
		return this.tempDocumentDao.findAllTempDocuments();
	}

	@Override
	public TempDocuments findTempDocumentsByDocumentName(String documentName) {
		// TODO Auto-generated method stub
		return this.tempDocumentDao.findTempDocumentsByDocumentName(documentName);
	}
	
	
	
}
