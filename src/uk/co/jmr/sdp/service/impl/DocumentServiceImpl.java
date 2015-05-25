package uk.co.jmr.sdp.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.sdp.dao.DisciplineDao;
import uk.co.jmr.sdp.dao.DoctypeDao;
import uk.co.jmr.sdp.dao.DocumentDao;
import uk.co.jmr.sdp.domain.CaseUserForms;
import uk.co.jmr.sdp.domain.Discipline;
import uk.co.jmr.sdp.domain.Doctype;
import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.domain.DocumentAttribute;
import uk.co.jmr.sdp.domain.SecurityGroup;
import uk.co.jmr.sdp.domain.dt.Attribute;
import uk.co.jmr.sdp.domain.dt.AttributeValue;
import uk.co.jmr.sdp.service.DocumentService;

@Service("documentService")
public class DocumentServiceImpl implements DocumentService {
	@Autowired
	private DocumentDao documentDao;
	@Autowired
	private DisciplineDao disciplineDao;
	@Autowired
	private DoctypeDao doctypeDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Document> findAllDocuments() {

		return this.documentDao.findAllDocuments();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Document> findAllDocumentsByWip(char wip) {

		return this.documentDao.findAllDocumentsByWip(wip);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Document> findByDiscipline(long id) {

		Discipline discipline = this.disciplineDao.findDisciplineById(id);
		return this.documentDao.findByDiscipline(discipline);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Document> findByDocType(long id) {

		Doctype doctype = this.doctypeDao.findDoctypeById(id);
		return this.documentDao.findByDocType(doctype);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Document> findDocumentsForPath(String path) {

		return this.documentDao.findDocumentsForPath(path);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Document findDocumentById(long id) {

		return this.documentDao.findDocumentById(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Document document) {

		this.documentDao.save(document);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(long id) {

		Document document = this.documentDao.findDocumentById(id);
		this.documentDao.delete(document);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Document findDocumentByDocNameAndPath(String documentName, String path) {

		return this.documentDao.findDocumentByDocNameAndPath(documentName, path);
	}

	// @Override
	// public List<Document> findDocumentsForSearch(
	// Doctype doctypeId, String author, String ebNo, String
	// documentName,String[] keywords, String dateCreatedFrom,String
	// dateCreatedTo, String relevantDateFrom, String relevantDateTo) {
	// return this.documentDao.findDocumentsForSearch( doctypeId, author,
	// ebNo,documentName,keywords,dateCreatedFrom,dateCreatedTo,relevantDateFrom,relevantDateTo);
	// }
	// @Override
	// public List<Document> findDocumentsForSearch(
	// Doctype doctypeId, String author, String ebNo, String
	// documentName,String[] keywords, String relevantDateFrom, String
	// relevantDateTo) {
	// return this.documentDao.findDocumentsForSearch( doctypeId, author,
	// ebNo,documentName,keywords,relevantDateFrom,relevantDateTo);
	// }
	
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Document> findDocumentsForSearch(Doctype doctypeId, String author, String[] ebNo, String documentName,
		String[] keywords, String relevantDateFrom, String relevantDateTo) {

		return this.documentDao.findDocumentsForSearch(doctypeId, author, ebNo, documentName, keywords, relevantDateFrom,
			relevantDateTo);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Document> findDocumentsForSearchAll(String author, String[] ebNo, String documentName,
		String[] keywords, String raisedDateFrom, String raisedDateTo,Set<SecurityGroup> secGrps) {
		return this.documentDao.findDocumentsForSearchAll(author, ebNo, documentName, keywords, raisedDateFrom,
			raisedDateTo,secGrps);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Document> findDocumentsOnlySearch(String author, String[] ebNo, String documentName,
		String[] keywords, String raisedDateFrom, String raisedDateTo,String relevantDateFrom, String relevantDateTo,Doctype doctypeId) {
		return this.documentDao.findDocumentsOnlySearch(author, ebNo, documentName, keywords, raisedDateFrom,
			raisedDateTo,relevantDateFrom,relevantDateTo,doctypeId);
	}
	
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Document> findDocumentsForCase(List<Long> caseId) {

		return this.documentDao.findDocumentsForCase(caseId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void lock(long documentId) {

		Document document = this.documentDao.findDocumentById(documentId);
		document.setLocked("YES");
		this.documentDao.save(document);
		//System.out.println("in serImpl-> " + document.getLocked());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void unLock(long documentId) {

		Document document = this.documentDao.findDocumentById(documentId);
		document.setLocked("NO");
		this.documentDao.save(document);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Document findDocumentForCaseId(long caseId) {

		return this.documentDao.findDocumentForCase(caseId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Document> findDocsByIds(Set<Long> docIds) {

		return documentDao.findDocsByIds(docIds);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void endWip(Document document) {

		document.setWip('N');
		this.documentDao.save(document);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Document> findAllDocumentsByWipAndRevisionWithoutQuickUpl(char wip, boolean value, long caseId) {

		return this.documentDao.findAllDocumentsByWipAndRevisionWithoutQuickUpl(wip, value, caseId);
	}
	
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Document findDocumentsByUserFormsIdAndCaseId(long userFormId,long caseId) {

		return this.documentDao.findDocumentsByUserFormsIdAndCaseId(userFormId,caseId);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Document findDocumentsByUserFormsId(String userFormId) {

		return this.documentDao.findDocumentsByUserFormsId(userFormId);
	}
	
	@Override
	public List<Document> findDocumentsOnSimpleSearch(String value,Set<SecurityGroup> secGrps) {
		
		return this.documentDao.findDocumentsOnSimpleSearch(value,secGrps);
	}
	
	@Override
	public Document findDocumentByDocName(String documentName) {
		// TODO Auto-generated method stub
		return this.documentDao.findDocumentByDocName(documentName);
	}

	@Override
	public DocumentAttribute findDocumentAttributeByDocAttrValue(
			Document document, AttributeValue attributeValue) {
		// TODO Auto-generated method stub
		return this.documentDao.findDocumentAttributeByDocAttrValue(document, attributeValue);
	}
	
	@Override
	public DocumentAttribute findDocumentAttributeByDocAttrId(
			Document document, Attribute attribute) {
		// TODO Auto-generated method stub
		return this.documentDao.findDocumentAttributeByDocAttrValue(document, attribute);
	}

	@Override
	public Document findDocumentByUserFormIdAndWip(String userFormId) {
		// TODO Auto-generated method stub
		return this.documentDao.findDocumentByUserFormIdAndWip(userFormId);
	}

}
