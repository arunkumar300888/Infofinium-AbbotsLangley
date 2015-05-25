package uk.co.jmr.sdp.service;

import java.util.List;

import uk.co.jmr.sdp.domain.DocumentTrail;

public interface DocumentTrailService {

	void save(DocumentTrail documentTrail);

	List<DocumentTrail> findDocTrailForDocument(long docId);
	List<DocumentTrail> findDocTrailByLastDownloadedDocument();
}
