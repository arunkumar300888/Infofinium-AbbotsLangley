package uk.co.jmr.sdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.AuditLogsDao;
import uk.co.jmr.sdp.domain.AuditLogs;
import uk.co.jmr.sdp.service.AuditLogsService;

@Service("auditLogsService")
public class AuditLogsServiceImpl implements  AuditLogsService {

	@Autowired
	private AuditLogsDao auditLogsDao;

	@Override
	public void save(AuditLogs auditLogs) {
		// TODO Auto-generated method stub
		this.auditLogsDao.save(auditLogs);
	}
}
