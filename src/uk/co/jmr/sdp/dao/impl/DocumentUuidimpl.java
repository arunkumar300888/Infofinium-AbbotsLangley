package uk.co.jmr.sdp.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.DocumentUuidDao;
import uk.co.jmr.sdp.domain.DocumentUUID;

@Repository("documentUuidDao")
public class DocumentUuidimpl implements DocumentUuidDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public long save(DocumentUUID docUuid) {

		// TODO Auto-generated method stub
		return (Long) hibernateTemplate.save(docUuid);
	}

}
