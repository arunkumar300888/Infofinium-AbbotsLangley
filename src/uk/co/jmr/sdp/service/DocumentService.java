package uk.co.jmr.sdp.service;

import java.util.List;
import java.util.Set;

import uk.co.jmr.sdp.domain.CaseUserForms;
import uk.co.jmr.sdp.domain.Discipline;
import uk.co.jmr.sdp.domain.Doctype;
import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.domain.DocumentAttribute;
import uk.co.jmr.sdp.domain.SecurityGroup;
import uk.co.jmr.sdp.domain.dt.Attribute;
import uk.co.jmr.sdp.domain.dt.AttributeValue;

public interface DocumentService {
	List<Document> findAllDocuments();

	List<Document> findAllDocumentsByWip(char wip); // Delete Doc

	List<Document> findAllDocumentsByWipAndRevisionWithoutQuickUpl(char wip, boolean value, long caseId); // Reassign
																											// Owner

	List<Document> findByDiscipline(long id);

	List<Document> findByDocType(long id);

	Document findDocumentById(long id);

	Document findDocumentByDocNameAndPath(String documentName, String path);

	// List<Document> findDocumentsForSearch(
	// Doctype doctypeId, String author, String ebNo, String
	// documentName,String[] keywords,String dateCreatedFrom,String
	// dateCreatedTo,String relevantDateFrom,String relevantDateTo);
	// List<Document> findDocumentsForSearch(
	// Doctype doctypeId, String author, String ebNo, String
	// documentName,String[] keywords,String relevantDateFrom,String
	// relevantDateTo);
	List<Document> findDocumentsForSearch(Doctype doctypeId, String author, String[] ebNo, String documentName,
		String[] keywords, String relevantDateFrom, String relevantDateTo);
	
	List<Document> findDocumentsForSearchAll(String author, String[] ebNo, String documentName,
			String[] keywords, String raisedDateFrom, String raisedDateTo,Set<SecurityGroup> secGrps);
	
	List<Document> findDocumentsOnlySearch(String author, String[] ebNo, String documentName,
			String[] keywords, String raisedDateFrom, String raisedDateTo,String relevantDateFrom, String relevantDateTo,Doctype doctypeId);

	List<Document> findDocumentsForPath(String path);

	void save(Document document);

	void delete(long id);

	List<Document> findDocumentsForCase(List<Long> cases);

	void lock(long documentId);

	void unLock(long documentId);

	List<Document> findDocsByIds(Set<Long> docIds);

	Document findDocumentForCaseId(long caseId);

	void endWip(Document document);
	
	//For Forms On Documents
	Document findDocumentsByUserFormsIdAndCaseId(long userFormId, long caseId);
	Document findDocumentsByUserFormsId(String userFormId);
	
	List<Document> findDocumentsOnSimpleSearch(String value,Set<SecurityGroup> secGrps);
	
	Document findDocumentByDocName(String documentName);
	
	DocumentAttribute findDocumentAttributeByDocAttrValue(Document document,
			AttributeValue attributeValue);

	DocumentAttribute findDocumentAttributeByDocAttrId(Document document,
			Attribute attribute);
	
	Document findDocumentByUserFormIdAndWip(String userFormId);

	
}
