package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.sdp.dao.DocumentTrailDao;
import uk.co.jmr.sdp.domain.DocumentTrail;
import uk.co.jmr.sdp.service.DocumentTrailService;

@Repository("documentTrailService")
public class DocumentTrailServiceImpl implements DocumentTrailService {

	@Autowired
	private DocumentTrailDao documentTrailDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(DocumentTrail documentTrail) {

		documentTrailDao.save(documentTrail);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DocumentTrail> findDocTrailForDocument(long docId) {

		return this.documentTrailDao.findDocTrailForDocument(docId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DocumentTrail> findDocTrailByLastDownloadedDocument() {
		
		return this.documentTrailDao.findDocTrailByLastDownloadedDocument();
	}
}
