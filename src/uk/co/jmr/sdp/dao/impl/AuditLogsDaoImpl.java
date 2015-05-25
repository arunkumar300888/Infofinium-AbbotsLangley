package uk.co.jmr.sdp.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.AuditLogsDao;
import uk.co.jmr.sdp.domain.AuditLogs;


@Repository("auditLogsDao")
public class AuditLogsDaoImpl implements AuditLogsDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void save(AuditLogs auditLogs) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(auditLogs);
	}
}
