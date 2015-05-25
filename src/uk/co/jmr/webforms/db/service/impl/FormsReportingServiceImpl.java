package uk.co.jmr.webforms.db.service.impl;

import com.ardhika.wfar.WfCase;
import com.ardhika.wfar.dao.CaseDao;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.sdp.dao.CaseUserFormsDao;
import uk.co.jmr.sdp.dao.DocumentDao;
import uk.co.jmr.sdp.domain.SecurityGroup;
import uk.co.jmr.sdp.web.util.DocListInput;
import uk.co.jmr.webforms.db.dao.FormsReportingDao;
import uk.co.jmr.webforms.db.service.FormsReportingService;

@Service("formsReportingService")
public class FormsReportingServiceImpl implements FormsReportingService {
	@Autowired
	private FormsReportingDao formsReportingDao;
	@Autowired
	private CaseUserFormsDao caseUserFormsDao;
	@Autowired
	private CaseDao caseDao;
	@Autowired
	private DocumentDao documentDao;
	

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WfCase> search(DocListInput docListInput,String securityGroup) {
		ArrayList<WfCase> results = new ArrayList<WfCase>();
		List<Long> resultList = formsReportingDao.search(docListInput,securityGroup);
		Iterator<Long> rit = resultList.iterator();
		while (rit.hasNext()) {
			Long caseId = rit.next();
			results.add(caseDao.findCaseById(caseId));
		}

		return results;

//		ArrayList<WfCase> results = new ArrayList<WfCase>();
//		List<Long> resultList = formsReportingDao.search(docListInput);
//		Iterator<Long> rit = resultList.iterator();
//		while (rit.hasNext()) {
//			long caseId = caseUserFormsDao.findCaseUserFormsByUserFormsId(rit.next()).getCaseId();
//			results.add(caseDao.findCaseById(caseId));
//		}
//		return results;
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WfCase> searchForms(DocListInput docListInput,Set<SecurityGroup> securityGroup) {
		ArrayList<WfCase> results = new ArrayList<WfCase>();
		List<Long> resultList = formsReportingDao.searchForms(docListInput,securityGroup);
		Iterator<Long> rit = resultList.iterator();
		while (rit.hasNext()) {
			Long caseId = rit.next();
			results.add(caseDao.findCaseById(caseId));
		}

		return results;

	}
}
