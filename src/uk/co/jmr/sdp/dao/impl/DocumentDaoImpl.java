package uk.co.jmr.sdp.dao.impl;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.DocumentDao;
import uk.co.jmr.sdp.domain.CaseUserForms;
import uk.co.jmr.sdp.domain.Discipline;
import uk.co.jmr.sdp.domain.Doctype;
import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.domain.DocumentAttribute;
import uk.co.jmr.sdp.domain.SecurityGroup;
import uk.co.jmr.sdp.domain.dt.Attribute;
import uk.co.jmr.sdp.domain.dt.AttributeValue;

@Repository("documentDao")
public class DocumentDaoImpl implements DocumentDao {
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<Document> findAllDocuments() {

		return hibernateTemplate.find("from Document");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Document> findAllDocumentsByWip(char wip) {

		return hibernateTemplate.find("from Document d where d.wip=?", wip);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Document> findAllDocumentsByWipAndRevisionWithoutQuickUpl(char wip, boolean value, long caseId) {

		return hibernateTemplate
			.find("from Document d where d.wip=? and d.revisionable=? and not d.caseId=? and not d.discriminator=?", wip, value, caseId,'F');
	}

	@Override
	public Document findDocumentById(long id) {

		return hibernateTemplate.get(Document.class, id);
	}

	@Override
	public void save(Document document) {

		if (document.getId() == -1) {
			document.setDateCreated(new Date());
		}
		hibernateTemplate.saveOrUpdate(document);
	}

	@Override
	public void delete(Document document) {

		hibernateTemplate.delete(document);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Document> findByDiscipline(Discipline discipline) {

		return hibernateTemplate.find("from Document d where d.discipline=?", discipline);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Document> findByDocType(Doctype docType) {

		return hibernateTemplate.find("from Document d where d.doctype=?", docType);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Document findDocumentByDocNameAndPath(String documentName, String path) {

		// System.out.println("***** In Dao");
		List<Document> docs = hibernateTemplate.find("from Document as d where d.name=? and d.filePath=?", documentName, path);
		// System.out.println("***** Out");
		if (docs.size() >= 1)
			return docs.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Document> findDocumentsForPath(String path) {

		List<Document> docs = hibernateTemplate.find("from Document as d where d.filePath=?", path);
		return docs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Document> findDocumentsForSearch(Doctype doctypeId, String author, String[] ebNo, String documentName,
		String[] keywords, String relevantDateFrom, String relevantDateTo) {

		// System.out.println("***** From Date:" + dateCreatedFrom);
		// System.out.println("***** To Create Date:" + dateCreatedTo);
		// Date createdDateFrom = null;
		// Date createdDateTo = null;
		Date relevantdateFrom = null;
		Date relevantdateTo = null;
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		try {
			// if (dateCreatedFrom == "" && dateCreatedTo == "") {
			// System.out.println("Inside IF");
			// dateCreatedFrom = null;
			// dateCreatedTo = null;
			// } else
			//
			// {
			// System.out.println("Inside Else");
			// createdDateFrom = df.parse(dateCreatedFrom);
			// createdDateTo = df.parse(dateCreatedTo);
			// Calendar cal = Calendar.getInstance();
			// cal.setTime(createdDateTo);
			// cal.set(Calendar.HOUR_OF_DAY, 23);
			// cal.set(Calendar.MINUTE, 59);
			// cal.set(Calendar.SECOND, 59);
			// createdDateTo = cal.getTime();
			//
			// }
			if (relevantDateFrom == "" && relevantDateTo == "") {
				//System.out.println("Inside IF");
				relevantdateFrom = null;
				relevantdateTo = null;
			}
			else {
				//System.out.println("Inside Else");
				relevantdateFrom = df.parse(relevantDateFrom);
				// relevantdateTo = df.parse(relevantDateTo);
				relevantdateTo = df.parse(relevantDateTo);
				Calendar cal = Calendar.getInstance();
				cal.setTime(relevantdateTo);
				cal.set(Calendar.HOUR_OF_DAY, 23);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);
				relevantdateTo = cal.getTime();
			}
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(Document.class).add(Restrictions.conjunction());
		DetachedCriteria dd = null;
		if (author != null && author.length() != 0 && author.trim().length() != 0)
			criteria = criteria.add(Restrictions.like("author", author, MatchMode.ANYWHERE));
		if (documentName != null && documentName.length() != 0 && documentName.trim().length() != 0)
			criteria = criteria.add(Restrictions.like("name", documentName, MatchMode.ANYWHERE));
		// if (ebNo != null && ebNo.length() != 0 && ebNo.trim().length() != 0)
		// criteria = criteria.add(Restrictions.eq("ebNo", ebNo));
		// criteria =
		// criteria.add(Restrictions.like("ebNo",ebNo,MatchMode.ANYWHERE));
		if (ebNo != null && ebNo.length > 1) {
			Disjunction disjunction = Restrictions.disjunction();
			for (String key : ebNo) {
				if (key != null && key.length() != 0 && key.trim().length() != 0) {
					disjunction.add(Restrictions.like("ebNo", key, MatchMode.ANYWHERE));
				}
				criteria.add(disjunction);
			}
		}
		else if ((ebNo != null && ebNo.length == 1)) {
			if (ebNo[0] != null && ebNo[0].length() != 0 && ebNo[0].trim().length() != 0) {
				criteria = criteria.add(Restrictions.like("ebNo", ebNo[0], MatchMode.ANYWHERE));
			}
		}
		// if (keywords != null && keywords.length() != 0
		// && keywords.trim().length() != 0)
		// criteria = criteria.add(Restrictions.like("keywords", keywords,
		// MatchMode.ANYWHERE));
		if (keywords != null && keywords.length > 1) {
			Disjunction disjunction = Restrictions.disjunction();
			for (String key : keywords) {
				if (key != null && key.length() != 0 && key.trim().length() != 0) {
					disjunction.add(Restrictions.like("keywords", key, MatchMode.ANYWHERE));
				}
				criteria.add(disjunction);
			}
		}
		else if ((keywords != null && keywords.length == 1)) {
			if (keywords[0] != null && keywords[0].length() != 0 && keywords[0].trim().length() != 0) {
				criteria = criteria.add(Restrictions.like("keywords", keywords[0], MatchMode.ANYWHERE));
			}
		}
		if (doctypeId != null)
			criteria = criteria.add(Restrictions.eq("doctype", doctypeId));
		if (relevantdateFrom != null && relevantdateTo != null) {
			// DetachedCriteria criteria1 = criteria.add(Restrictions.between(
			// "dateCreated", relevantdateFrom, relevantdateTo));
			//
			// DetachedCriteria criteria2 = criteria.add(Restrictions.between(
			// "targetDate", relevantdateFrom, relevantdateTo));
			criteria.add(Restrictions.disjunction().add(Restrictions.between("dateCreated", relevantdateFrom, relevantdateTo))
				.add(Restrictions.between("targetDate", relevantdateFrom, relevantdateTo)));
			// criteria = criteria.add(Restrictions.eq("caseId", new Long(0)));
		}
		criteria.add(Restrictions.eq("discriminator", 'D'));
		criteria = criteria.add(Restrictions.eq("wip", 'N'));
		//System.out.println("Criteria-> " + criteria);
		return hibernateTemplate.findByCriteria(criteria);
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Document> findDocumentsOnlySearch(String author, String[] ebNo, String documentName,
			String[] keywords, String dateCreatedFrom, String dateCreatedTo,String relevantDateFrom, String relevantDateTo,Doctype doctypeId) {
			    Date createdDateFrom = null;
				Date createdDateTo = null;
				Date relevantdateFrom = null;
				Date relevantdateTo = null;
				SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				try {
					 if (dateCreatedFrom == "" && dateCreatedTo == "") {
					// System.out.println("Inside IF");
					 dateCreatedFrom = null;
					 dateCreatedTo = null;
					 } else {
					 createdDateFrom = df.parse(dateCreatedFrom);
					 createdDateTo = df.parse(dateCreatedTo);
					 Calendar cal = Calendar.getInstance();
					 cal.setTime(createdDateTo);
					 cal.set(Calendar.HOUR_OF_DAY, 23);
					 cal.set(Calendar.MINUTE, 59);
					 cal.set(Calendar.SECOND, 59);
					 createdDateTo = cal.getTime();
					 }
					if (relevantDateFrom == "" && relevantDateTo == "") {
						//System.out.println("Inside IF");
						relevantdateFrom = null;
						relevantdateTo = null;
					}
					else {
						relevantdateFrom = df.parse(relevantDateFrom);
						relevantdateTo = df.parse(relevantDateTo);
//						Calendar cal = Calendar.getInstance();
//						cal.setTime(relevantdateTo);
//						cal.set(Calendar.HOUR_OF_DAY, 23);
//						cal.set(Calendar.MINUTE, 59);
//						cal.set(Calendar.SECOND, 59);
//						relevantdateTo = cal.getTime();
					}
				}
				catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DetachedCriteria criteria = DetachedCriteria.forClass(Document.class).add(Restrictions.conjunction());
				DetachedCriteria dd = null;
				if (author != null && author.length() != 0 && author.trim().length() != 0)
					criteria = criteria.add(Restrictions.like("author", author, MatchMode.ANYWHERE));
				if (documentName != null && documentName.length() != 0 && documentName.trim().length() != 0)
					criteria = criteria.add(Restrictions.like("name", documentName, MatchMode.ANYWHERE));
				if (ebNo != null && ebNo.length > 1) {
					Disjunction disjunction = Restrictions.disjunction();
					for (String key : ebNo) {
						if (key != null && key.length() != 0 && key.trim().length() != 0) {
							disjunction.add(Restrictions.like("ebNo", key, MatchMode.ANYWHERE));
						}
						criteria.add(disjunction);
					}
				}
				else if ((ebNo != null && ebNo.length == 1)) {
					if (ebNo[0] != null && ebNo[0].length() != 0 && ebNo[0].trim().length() != 0) {
						criteria = criteria.add(Restrictions.like("ebNo", ebNo[0], MatchMode.ANYWHERE));
					}
				}
				if (keywords != null && keywords.length > 1) {
					Disjunction disjunction = Restrictions.disjunction();
					for (String key : keywords) {
						if (key != null && key.length() != 0 && key.trim().length() != 0) {
							disjunction.add(Restrictions.like("keywords", key, MatchMode.ANYWHERE));
						}
						criteria.add(disjunction);
					}
				}
				else if ((keywords != null && keywords.length == 1)) {
					if (keywords[0] != null && keywords[0].length() != 0 && keywords[0].trim().length() != 0) {
						criteria = criteria.add(Restrictions.like("keywords", keywords[0], MatchMode.ANYWHERE));
					}
				}
				if (doctypeId != null)
					criteria = criteria.add(Restrictions.eq("doctype", doctypeId));
				if (dateCreatedFrom != null && dateCreatedTo != null)
					criteria = criteria.add(Restrictions.between("dateCreated",
							createdDateFrom, createdDateTo));
				if (relevantdateFrom != null && relevantdateTo != null)
					criteria = criteria.add(Restrictions.between("targetDate",
							relevantdateFrom, relevantdateTo));
				criteria = criteria.add(Restrictions.eq("wip", 'N'));
				criteria=criteria.add(Restrictions.eq("discriminator",'D'));
				return hibernateTemplate.findByCriteria(criteria);
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Document> findDocumentsForSearchAll(String author, String[] ebNo, String documentName,
		String[] keywords, String raisedDateFrom, String raisedDateTo,Set<SecurityGroup> secGrps) {		
		 Date createdDateFrom = null;
		 Date createdDateTo = null;
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		try {
			 if (raisedDateFrom == "" && raisedDateTo == "") {
			 raisedDateFrom = null;
			 raisedDateTo = null;
			 } 
			 else{
			 createdDateFrom = df.parse(raisedDateFrom);
			 createdDateTo = df.parse(raisedDateTo);
			 Calendar cal = Calendar.getInstance();
			 cal.setTime(createdDateTo);
			 cal.set(Calendar.HOUR_OF_DAY, 23);
			 cal.set(Calendar.MINUTE, 59);
			 cal.set(Calendar.SECOND, 59);
			 createdDateTo = cal.getTime();
			 }
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(Document.class).add(Restrictions.conjunction());
		DetachedCriteria dd = null;
		if (author != null && author.length() != 0 && author.trim().length() != 0)
			criteria = criteria.add(Restrictions.like("author", author, MatchMode.ANYWHERE));
		if (documentName != null && documentName.length() != 0 && documentName.trim().length() != 0)
			criteria = criteria.add(Restrictions.like("name", documentName, MatchMode.ANYWHERE));
		if (ebNo != null && ebNo.length > 1) {
			Disjunction disjunction = Restrictions.disjunction();
			for (String key : ebNo) {
				if (key != null && key.length() != 0 && key.trim().length() != 0) {
					disjunction.add(Restrictions.like("ebNo", key, MatchMode.ANYWHERE));
				}
				criteria.add(disjunction);
			}
		}
		else if ((ebNo != null && ebNo.length == 1)) {
			if (ebNo[0] != null && ebNo[0].length() != 0 && ebNo[0].trim().length() != 0) {
				criteria = criteria.add(Restrictions.like("ebNo", ebNo[0], MatchMode.ANYWHERE));
			}
		}
		if (keywords != null && keywords.length > 1) {
			Disjunction disjunction = Restrictions.disjunction();
			for (String key : keywords) {
				if (key != null && key.length() != 0 && key.trim().length() != 0) {
					disjunction.add(Restrictions.like("keywords", key, MatchMode.ANYWHERE));
				}
				criteria.add(disjunction);
			}
		}
		else if ((keywords != null && keywords.length == 1)) {
			if (keywords[0] != null && keywords[0].length() != 0 && keywords[0].trim().length() != 0) {
				criteria = criteria.add(Restrictions.like("keywords", keywords[0], MatchMode.ANYWHERE));
			}
		}

		if (raisedDateFrom != null && raisedDateTo  != null)
			criteria = criteria.add(Restrictions.between("dateCreated",
					createdDateFrom, createdDateTo));
	
		criteria.add(Restrictions.eq("discriminator", 'D'));
		criteria = criteria.add(Restrictions.eq("wip", 'N'));
		List<Document> searchedDocs=hibernateTemplate.findByCriteria(criteria);
		//System.out.println("Criteria-> " + criteria);
		
		DetachedCriteria criteria2 = DetachedCriteria.forClass(Document.class).add(Restrictions.conjunction());
		DetachedCriteria dd2 = null;
		if (author != null && author.length() != 0 && author.trim().length() != 0)
			criteria2 = criteria2.add(Restrictions.like("author", author, MatchMode.ANYWHERE));
		if (documentName != null && documentName.length() != 0 && documentName.trim().length() != 0)
			criteria2 = criteria2.add(Restrictions.like("name", documentName, MatchMode.ANYWHERE));
		if (ebNo != null && ebNo.length > 1) {
			Disjunction disjunction = Restrictions.disjunction();
			for (String key : ebNo) {
				if (key != null && key.length() != 0 && key.trim().length() != 0) {
					disjunction.add(Restrictions.like("ebNo", key, MatchMode.ANYWHERE));
				}
				criteria2.add(disjunction);
			}
		}
		else if ((ebNo != null && ebNo.length == 1)) {
			if (ebNo[0] != null && ebNo[0].length() != 0 && ebNo[0].trim().length() != 0) {
				criteria2 = criteria2.add(Restrictions.like("ebNo", ebNo[0], MatchMode.ANYWHERE));
			}
		}
		if (keywords != null && keywords.length > 1) {
			Disjunction disjunction = Restrictions.disjunction();
			for (String key : keywords) {
				if (key != null && key.length() != 0 && key.trim().length() != 0) {
					disjunction.add(Restrictions.like("keywords", key, MatchMode.ANYWHERE));
				}
				criteria2.add(disjunction);
			}
		}
		else if ((keywords != null && keywords.length == 1)) {
			if (keywords[0] != null && keywords[0].length() != 0 && keywords[0].trim().length() != 0) {
				criteria2 = criteria2.add(Restrictions.like("keywords", keywords[0], MatchMode.ANYWHERE));
			}
		}

		if (raisedDateFrom != null && raisedDateTo  != null)
			criteria2 = criteria2.add(Restrictions.between("dateCreated",
					createdDateFrom, createdDateTo));
	
		criteria2.add(Restrictions.eq("discriminator", 'F'));
		if(!secGrps.isEmpty()){
		criteria2.add(Restrictions.in("securityGroup", secGrps));
		}
		//criteria2.add(Restrictions.or(Restrictions.in("securityGroup", secGrps),Restrictions.isNull("securityGroup")));
		//criteria2.add(Restrictions.isNull("securityGroup"));
		criteria2 = criteria2.add(Restrictions.eq("wip", 'N'));
		List<Document> searchedDocuments1=hibernateTemplate.findByCriteria(criteria2);
		searchedDocs.addAll(0, searchedDocuments1);
		return searchedDocs;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Document> findDocumentsForCase(List<Long> caseIds) {

		StringBuffer caseIdsBuf = new StringBuffer();
		ListIterator<Long> it = caseIds.listIterator();
		while (it.hasNext()) {
			caseIdsBuf.append(it.next());
			if (it.hasNext())
				caseIdsBuf.append(",");
		}
		String caseIdText = caseIdsBuf.toString();
		if (caseIdText.trim().length() == 0)
			return new ArrayList<Document>();
		String query = "from Document d where d.caseId in (" + caseIdsBuf + ")";
		List<Document> documents = hibernateTemplate.find(query);
		return documents;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Document findDocumentForCase(long caseId) {

		List<Document> docs = hibernateTemplate.find("from Document as d where d.caseId=?", caseId);
		if (docs.size() >= 1)
			return docs.get(0);
		return null;
	}

	@Override
	public List<Document> findDocsByIds(Set<Long> docIds) {

		DetachedCriteria criteria = DetachedCriteria.forClass(Document.class);
		criteria.add(Restrictions.in("id", docIds));
		return hibernateTemplate.findByCriteria(criteria);
	}
	
	@Override
	public Document findDocumentsByUserFormsIdAndCaseId(long userFormId, long caseId) {

		@SuppressWarnings("unchecked")
		List<Document> docUserForms = hibernateTemplate.find("from Document where user_form_id=? and case_id=?",
			userFormId, caseId);
		if (docUserForms.size() >= 1)
			return docUserForms.get(0);
		return null;
	}
	
	@Override
	public Document findDocumentsByUserFormsId(String userFormId) {
		@SuppressWarnings("unchecked")
		List<Document> docUserForms = hibernateTemplate.find("from Document as d  where d.userFormId=?",userFormId);
		if (docUserForms.size() >= 1)
			return docUserForms.get(0);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Document> findDocumentsOnSimpleSearch(String value,Set<SecurityGroup> secGrps) {
		
		//Fetching document list as per the search
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Document.class); 
		
        Disjunction disCriteria = Restrictions.disjunction();
       
        disCriteria.add(Restrictions.like("author", value, MatchMode.ANYWHERE));
        disCriteria.add(Restrictions.like("keywords", value, MatchMode.ANYWHERE));
        disCriteria.add(Restrictions.like("name", value, MatchMode.ANYWHERE));
        disCriteria.add(Restrictions.like("reassignOwner", value, MatchMode.ANYWHERE));
        disCriteria.add(Restrictions.like("ebNo",value, MatchMode.ANYWHERE));
        
        criteria.add(disCriteria);
        criteria.add(Restrictions.eq("wip", 'N'));
        criteria.add(Restrictions.eq("discriminator",'D'));
        List<Document> simpleSearchList = hibernateTemplate.findByCriteria(criteria);
        
      //Fetching form list as per the Security Group and search criteria
         criteria = DetachedCriteria.forClass(Document.class); 
		
         disCriteria = Restrictions.disjunction();
         
        disCriteria.add(Restrictions.like("author", value, MatchMode.ANYWHERE));
        disCriteria.add(Restrictions.like("keywords", value, MatchMode.ANYWHERE));
        disCriteria.add(Restrictions.like("name", value, MatchMode.ANYWHERE));
        disCriteria.add(Restrictions.like("reassignOwner", value, MatchMode.ANYWHERE));
        disCriteria.add(Restrictions.like("ebNo",value, MatchMode.ANYWHERE));
        criteria.add(disCriteria);
        criteria.add(Restrictions.eq("wip", 'N'));
        criteria.add(Restrictions.eq("discriminator",'F'));
        if(!secGrps.isEmpty()){
        criteria.add(Restrictions.in("securityGroup",secGrps));
        }
        //criteria.add(Restrictions.or(Restrictions.in("securityGroup", secGrps),Restrictions.isNull("securityGroup")));
        
        List<Document> simpleSearchList1 = hibernateTemplate.findByCriteria(criteria);
        
       //Adding forms list to document list
        simpleSearchList.addAll(0, simpleSearchList1);
        
		return simpleSearchList;
		
	}
	
	@Override
	public Document findDocumentByDocName(String documentName) {
		List<Document> docs = hibernateTemplate.find("from Document as d where d.name=?", documentName);
		// System.out.println("***** Out");
		if (docs.size() >= 1)
			return docs.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DocumentAttribute findDocumentAttributeByDocAttrValue(Document document,
			AttributeValue attributeValue) {
		// TODO Auto-generated method stub
		List<DocumentAttribute> docsAttr = hibernateTemplate.find("from DocumentAttribute where document=? and attributeValue=?",document,attributeValue);
		if (docsAttr.size() >= 1)
			return docsAttr.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DocumentAttribute findDocumentAttributeByDocAttrValue(
			Document document, Attribute attribute) {
		// TODO Auto-generated method stub
		List<DocumentAttribute> docsAttr = hibernateTemplate.find("from DocumentAttribute where document=? and attribute=?",document,attribute);
		if (docsAttr.size() >= 1)
			return docsAttr.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Document findDocumentByUserFormIdAndWip(String userFormId) {
		// TODO Auto-generated method stub
		List<Document> documents = hibernateTemplate.find("from Document where userFormId=? and wip=?",userFormId,'N');
		if (documents.size() >= 1)
			return documents.get(0);
		return null;
	}
}
