package uk.co.jmr.sdp.dao;

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

public interface DocumentDao {
	List<Document> findAllDocuments();

	List<Document> findAllDocumentsByWip(char wip);

	List<Document> findAllDocumentsByWipAndRevisionWithoutQuickUpl(char wip, boolean value, long caseId); // Reassign
																											// Owner

	List<Document> findByDiscipline(Discipline discipline);

	List<Document> findByDocType(Doctype docType);

	Document findDocumentById(long id);

	Document findDocumentByDocNameAndPath(String documentName, String path);
	
	Document findDocumentByDocName(String documentName);
	
	Document findDocumentByUserFormIdAndWip(String userFormId);
	
	

	List<Document> findDocumentsForPath(String path);

	// List<Document> findDocumentsForSearch(Doctype doctypeId,String
	// author,String ebNo,String documentName,String[] keywords,String
	// dateCreatedFrom,String dateCreatedTo,String relevantDateFrom, String
	// relevantDateTo);
	// List<Document> findDocumentsForSearch(Doctype doctypeId,String
	// author,String ebNo,String documentName,String[] keywords,String
	// relevantDateFrom, String relevantDateTo);
	List<Document> findDocumentsForSearch(Doctype doctypeId, String author, String[] ebNo, String documentName,
		String[] keywords, String relevantDateFrom, String relevantDateTo);
	
	List<Document> findDocumentsForSearchAll(String author, String[] ebNo, String documentName,
			String[] keywords, String relevantDateFrom, String relevantDateTo,Set<SecurityGroup> secGrps);
	
	List<Document> findDocumentsOnlySearch(String author, String[] ebNo, String documentName,
			String[] keywords, String raisedDateFrom, String raisedDateTo,String relevantDateFrom, String relevantDateTo,Doctype doctypeId);
	

	void save(Document document);

	void delete(Document document);

	List<Document> findDocumentsForCase(List<Long> caseId);

	Document findDocumentForCase(long caseId);

	List<Document> findDocsByIds(Set<Long> docIds);
	
	Document findDocumentsByUserFormsIdAndCaseId(long userFormId, long caseId);
	Document findDocumentsByUserFormsId(String userFormId);
	
	List<Document> findDocumentsOnSimpleSearch(String value,Set<SecurityGroup> secGrps);

	DocumentAttribute findDocumentAttributeByDocAttrValue(Document document,
			AttributeValue attributeValue);

	DocumentAttribute findDocumentAttributeByDocAttrValue(Document document,
			Attribute attribute);
}
