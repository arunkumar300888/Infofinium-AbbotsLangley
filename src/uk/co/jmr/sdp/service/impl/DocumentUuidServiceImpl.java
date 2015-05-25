package uk.co.jmr.sdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.sdp.dao.DocumentUuidDao;
import uk.co.jmr.sdp.domain.DocumentUUID;
import uk.co.jmr.sdp.service.DocumentUuidService;

@Service("documentUuidService")
public class DocumentUuidServiceImpl implements DocumentUuidService {
	@Autowired
	private DocumentUuidDao documentUuidDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public long save(DocumentUUID docUuid) {

		// TODO Auto-generated method stub
		return documentUuidDao.save(docUuid);
	}

}
