package uk.co.jmr.webforms.db.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import uk.co.jmr.sdp.common.Utils;
import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.domain.SecurityGroup;
import uk.co.jmr.sdp.service.DocumentService;
import uk.co.jmr.sdp.web.util.DocListInput;
import uk.co.jmr.webforms.db.dao.FormsReportingDao;
import uk.co.jmr.webforms.db.pojo.FormData;
import uk.co.jmr.webforms.db.pojo.Forms;
import uk.co.jmr.webforms.db.pojo.UserForms;
import uk.co.jmr.webforms.db.service.FormDefsService;
import uk.co.jmr.webforms.db.service.FormsService;
import uk.co.jmr.webforms.db.service.UserFormsService;
import uk.co.jmr.webforms.db.service.impl.FormsServiceImpl;

@Repository("formsReportingDao")
public class FormsReportingDaoImpl implements FormsReportingDao {
	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	private UserFormsService userFormsService;
	
	@Autowired
	private DocumentService documentService;
	@Autowired
	private FormsService formsService;
	@Autowired
	private FormDefsService formDefsService;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> search(DocListInput docListInput,String securityGroup) {
		Session session = null;
		String p;
		List<Long> resultList = null;
		try {
			//System.out.println("========================== I'm here");
			StringBuilder sb = new StringBuilder();
			ArrayList<Object> params = new ArrayList<Object>();

			sb.append("select \n");
			sb.append("	 distinct a.ID CASE_ID \n");
			sb.append("from \n");
			sb.append("	 WF_CASE a, \n");
			//sb.append("	 CASE_USER_FORMS b, \n");
			sb.append(" DOCUMENTS b,  \n");
			sb.append("	 USER_FORMS c, \n");
			sb.append("	 FORMS d \n");
			/*sb.append("	form_data fd \n");*/
			sb.append("where \n");

			if (docListInput.getDocTypeId() != -1) {
				params.add(new Long(docListInput.getDocTypeId()));
				sb.append("	 d.FORM_DEF_ID = ? and \n");
			}

			p = docListInput.getDocumentName();
			if (p != null && p.length() > 0) {
				p = checkWildCard(sb, "b.DOCUMENT_NAME", p);
				sb.append(" and\n");
				params.add(p.toLowerCase());
			}
			
			p = docListInput.getKeywords();
			if (p != null && p.length() > 0) {
				p = checkWildCard(sb, "b.KEYWORDS", p);
				sb.append(" and\n");
				params.add(p.toLowerCase());
			}
			
			p = docListInput.getEbNo();
			if (p != null && p.length() > 0) {
				p = checkWildCard(sb, "b.EB_NUMBER", p);
				sb.append(" and\n");
				params.add(p.toLowerCase());
			}
			
			p = docListInput.getAuthor();
			if (p != null && p.length() > 0) {
				p = checkWildCard(sb, "b.AUTHOR", p);
				sb.append(" and\n");
				params.add(p.toLowerCase());
			}
			
			Date createdDateFrom=Utils.parseDate(new SimpleDateFormat("dd-MM-yyyy"),docListInput.getDateCreatedFrom());
			Date createdDateTo=Utils.parseDate(new SimpleDateFormat("dd-MM-yyyy"),docListInput.getDateCreatedTo());	

			Date fromDate = Utils.parseDate(new SimpleDateFormat("dd-MM-yyyy"), docListInput.getRelevantDateFrom());
			Date toDate = Utils.parseDate(new SimpleDateFormat("dd-MM-yyyy"), docListInput.getRelevantDateTo());
			if(createdDateFrom!=null || createdDateTo !=null){
				if (createdDateFrom == null) {
					sb.append("	 b.CREATED_DATE <= ? and \n");
					params.add(createdDateTo);
				}
				else if (createdDateTo == null) {
					sb.append("	 b.CREATED_DATE >= ? and \n");
					params.add(createdDateFrom);
				}
				else {
					 sb.append("	 b.CREATED_DATE between ? and ? and \n");
					 Calendar cal = Calendar.getInstance();
					 cal.setTime(createdDateTo);
					 cal.set(Calendar.HOUR_OF_DAY, 23);
					 cal.set(Calendar.MINUTE, 59);
					 cal.set(Calendar.SECOND, 59);
					 createdDateTo = cal.getTime();
					params.add(createdDateFrom);
					params.add(createdDateTo);
				}
			}
			
			if (fromDate != null || toDate != null) {
				if (fromDate == null) {
					sb.append("	 b.TARGET_DATE <= ? and \n");
					params.add(toDate);
				}
				else if (toDate == null) {
					sb.append("	 b.TARGET_DATE >= ? and \n");
					params.add(fromDate);
				}
				else {
					sb.append("	 b.TARGET_DATE between ? and ? and \n");
					params.add(fromDate);
					params.add(toDate);
				}
			}
			
			sb.append("	 d.ID = c.FORM_ID and \n"); 
			sb.append("	 c.ID = b.USER_FORM_ID and \n");
		//	sb.append("	 b.CASE_ID = a.ID");
			sb.append("	 b.CASE_ID = a.ID and \n");
		    sb.append("b.WIP='N' and \n");
		    //sb.append("b.DISCRIMINATOR='F' and (b.SECURITY_GROUP_ID in("+securityGroup+") or b.SECURITY_GROUP_ID is null) and (fd.text_value like '%"+docListInput.getSpecificData()+"%' or int_value like '%"+docListInput.getSpecificData()+"%'  )");
		   //sb.append("b.DISCRIMINATOR='F' and  b.SECURITY_GROUP_ID in("+securityGroup+") and (fd.text_value like '%"+docListInput.getSpecificData()+"%' or int_value like '%"+docListInput.getSpecificData()+"%'  )");
		   sb.append("b.DISCRIMINATOR='F' and  b.SECURITY_GROUP_ID in("+securityGroup+") ");
		    System.out.println(sb.toString());
			Object[] queryParams = params.toArray(new Object[0]);
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(sb.toString());
			for (int i = 0; i < queryParams.length; i++) {
				query.setParameter(i, queryParams[i]);
			}

			resultList = query.addScalar("CASE_ID", Hibernate.LONG).list();
			
		}
		catch (Exception e) {
			e.printStackTrace();
			if (session != null) {
				session.close();
			}
		}

		return resultList;
	}

	String checkWildCard(StringBuilder sb, String p, String v) {

		boolean isWildCardQuery = false;

		sb.append("	 lower(").append(p).append(") ");
		if (v.indexOf("%") != -1) {
			v = v.replaceAll("%", "%%");
		}

		if (v.indexOf("*") != -1) {
			v = v.replaceAll("\\*", "%");
			isWildCardQuery = true;
		}

		if (isWildCardQuery)
			sb.append("like ?");
		else
			sb.append("= ?");

		return v;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> searchForms(DocListInput docListInput,Set<SecurityGroup> securityGroup){
		Session session = null;
		List<Long> caseIds=null;
		List<String> resultList = null;
		
		try{
		
		StringBuilder sb = new StringBuilder();
		session = sessionFactory.openSession();
		
		
		sb.append("select distinct a.user_form_id USER_FORM_ID ");
		sb.append("FROM form_data a, user_forms b, forms c  where (a.text_value like '%");
		sb.append(docListInput.getSpecificData());
		sb.append("%' or a.int_value like '%");
		sb.append(docListInput.getSpecificData());
		sb.append("%')");
		if(docListInput.getDocTypeId() != -1){
			sb.append(" and c.form_def_id =");
			sb.append(docListInput.getDocTypeId());
			sb.append(" and b.form_id = c.id and a.user_form_id = b.id");
		}
		
		SQLQuery query = session.createSQLQuery(sb.toString());
		System.out.println(">>>>>>>>>>>>>>>>>"+query.toString());
		resultList = query.addScalar("USER_FORM_ID", Hibernate.STRING).list();
		System.out.println("result set::::"+ resultList.size());
		}catch (Exception e) {
			e.printStackTrace();
			if (session != null) {
				session.close();
			}
		}
		
		DetachedCriteria docCriteria = DetachedCriteria.forClass(Document.class);
		docCriteria.add(Restrictions.eq("discriminator", 'F'));
		if(!securityGroup.isEmpty()){
			docCriteria.add(Restrictions.in("securityGroup", securityGroup));
		}
		docCriteria.add(Restrictions.eq("wip", 'N'));
		if(resultList.size()  > 0){
			docCriteria.add(Restrictions.in("userFormId", resultList));
		}
		
		if(docListInput.getDateCreatedFrom().length()>0){
			Date createdDateFrom=Utils.parseDate(new SimpleDateFormat("dd-MM-yyyy"),docListInput.getDateCreatedFrom());
			Date createdDateTo=Utils.parseDate(new SimpleDateFormat("dd-MM-yyyy"),docListInput.getDateCreatedTo());	
			if(createdDateFrom!=createdDateTo)
			docCriteria.add(Restrictions.between("dateCreated", createdDateFrom, createdDateTo));
			else
				docCriteria.add(Restrictions.eq("dateCreated", createdDateFrom));
		}
		
		if(docListInput.getRelevantDateFrom().length()>0){
			
			Date fromDate = Utils.parseDate(new SimpleDateFormat("dd-MM-yyyy"), docListInput.getRelevantDateFrom());
			Date toDate = Utils.parseDate(new SimpleDateFormat("dd-MM-yyyy"), docListInput.getRelevantDateTo());
			if(fromDate!=toDate)
			docCriteria.add(Restrictions.between("targetDate", fromDate, toDate));
			else{
				docCriteria.add(Restrictions.eq("targetDate", fromDate));
			}
		}
		
		if(docListInput.getEbNo().length()>0){
			docCriteria.add(Restrictions.like("ebNo", docListInput.getEbNo(), MatchMode.ANYWHERE));
		}
		
		if(docListInput.getAuthor().length()>0){
			docCriteria.add(Restrictions.like("author", docListInput.getAuthor(), MatchMode.ANYWHERE));
		}
		
		if(docListInput.getKeywords().length()>0){
			docCriteria.add(Restrictions.like("keywords", docListInput.getKeywords(), MatchMode.ANYWHERE));
		}
		
		if(docListInput.getDocumentName().length()>0){
			docCriteria.add(Restrictions.like("name", docListInput.getDocumentName(), MatchMode.ANYWHERE));
		}
		System.out.println(docCriteria.getExecutableCriteria(session));	
		List<Document> docs=hibernateTemplate.findByCriteria(docCriteria);
		
		
		
//		System.out.println("docs "+docs.size());
//		List<UserForms> ufs=new ArrayList<UserForms>();
//		List<Long> caseIds=new ArrayList<Long>();
//		
//		for(Document d:docs){
//			ufs.add(userFormsService.findUserFormsById(Long.parseLong(d.getUserFormId())));
//		}
//		
//		System.out.println("ufs 1  "+ufs.size());
//		
//		if(docListInput.getDocTypeId()!=-1){
//			
//			List<Long> ufsId=new ArrayList<Long>();
//			for(UserForms uforms:ufs){
//				ufsId.add(uforms.getId());
//			}
//			System.out.println("ufsId   "+ufsId.size());
//		DetachedCriteria formsCriteria = DetachedCriteria.forClass(Forms.class);
//		formsCriteria.add(Restrictions.eq("formDefs", formDefsService.findFormDefsById(docListInput.getDocTypeId())));
//		List<Forms> forms = hibernateTemplate.findByCriteria(formsCriteria);
//		
//		System.out.println("forms "+forms.size());
//		DetachedCriteria userFormsCriteria = DetachedCriteria.forClass(UserForms.class);
//		if(ufsId.size() > 0){
//			userFormsCriteria.add(Restrictions.in("id", ufsId));
//		}
//		
//		userFormsCriteria.add(Restrictions.in("forms", forms));
//		
//		ufs=hibernateTemplate.findByCriteria(userFormsCriteria);
//		System.out.println("ufs 2  "+ufs.size());
//		}
//		DetachedCriteria formDataCriteria = DetachedCriteria.forClass(FormData.class);
//		formDataCriteria.add(Restrictions.in("userForms", ufs));
//		
//		if(docListInput.getSpecificData().length()>0){
//		formDataCriteria.add(Restrictions.or(Restrictions.like("intValue", Long.valueOf(docListInput.getSpecificData())),Restrictions.like("textValue", docListInput.getSpecificData())));
//		}
//		
//		//formDataCriteria.add(Restrictions.)
//		List<FormData> fds=hibernateTemplate.findByCriteria(formDataCriteria);
//		
		caseIds=new ArrayList<Long>();
		for(Document f:docs){
			caseIds.add(f.getCaseId());
		}

		
//		HashSet hs = new HashSet<>();
//		hs.addAll(caseIds);
//		caseIds.clear();
//		caseIds.addAll(hs);
		
		System.out.println("caseIds "+caseIds.size());
		return caseIds;
	}
}
