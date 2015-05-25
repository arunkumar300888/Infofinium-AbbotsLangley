package uk.co.jmr.sdp.dao;

import java.util.List;

import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.domain.DocumentTrail;
import uk.co.jmr.sdp.web.util.UserInfo;

public interface DocumentTrailDao {

	void save(DocumentTrail documentTrail);

	List<DocumentTrail> findDocTrailForDocument(long docId);
	List<DocumentTrail> findDocTrailByLastDownloadedDocument();
}
