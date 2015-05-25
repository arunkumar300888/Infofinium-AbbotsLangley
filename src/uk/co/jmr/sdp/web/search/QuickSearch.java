package uk.co.jmr.sdp.web.search;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.web.bind.annotation.RequestParam;

import uk.co.jmr.sdp.domain.Doctype;
import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.service.DoctypeService;
import uk.co.jmr.sdp.service.DocumentService;
import uk.co.jmr.sdp.web.util.SearchFilter;
import uk.co.jmr.sdp.web.util.Util;

public class QuickSearch {

	private long docTypeId;
	private String author;
	private String[] ebNoArr;
	private String[] keywordsArr;
	private String documentName;
	// private String dateCreatedFrom;
	// private String dateCreatedTo;
	private long disciplineId;
	private long siteId;
	private String relevantDateFrom;
	private String relevantDateTo;

	// public QuickSearch(long disciplineId, long siteId, long docTypeId,
	// String author, String ebNo, String keywords, String documentName,
	// String dateCreatedFrom, String dateCreatedTo,
	// String relevantDateFrom, String relevantDateTo) {
	public QuickSearch(long disciplineId, long siteId, long docTypeId, String author, String ebNo, String keywords,
		String documentName, String relevantDateFrom, String relevantDateTo) {

		super();

		this.docTypeId = docTypeId;
		this.author = author;
		this.ebNoArr = Util.split(ebNo);
		this.documentName = documentName;
		// this.dateCreatedFrom = dateCreatedFrom;
		// this.dateCreatedTo = dateCreatedTo;
		this.keywordsArr = Util.split(keywords);
		this.disciplineId = disciplineId;
		this.siteId = siteId;
		this.relevantDateFrom = relevantDateFrom;
		this.relevantDateTo = relevantDateTo;

	}

	public List<Document> searchDocs(DoctypeService docTypeService, DocumentService documentService) throws Exception {

		List<Document> searchedDocs = null;

		Doctype docType = docTypeService.findDoctypeById(docTypeId);

		// searchedDocs = documentService.findDocumentsForSearch(docType,
		// author,
		// ebNo, documentName, keywordsArr, dateCreatedFrom,
		// dateCreatedTo, relevantDateFrom, relevantDateTo);
		searchedDocs = documentService.findDocumentsForSearch(docType, author, ebNoArr, documentName, keywordsArr,
			relevantDateFrom, relevantDateTo);

		return searchedDocs;

	}

	public List<Document> filterSearchDocsBasedOnDiciplineAndSite(List<Document> searchedDocs) {

		SearchFilter filter = null;

		if ((disciplineId != -1 && siteId != -1)) {

			filter = new SearchFilter(searchedDocs);

			searchedDocs = filter.restrictListBasedOnAttributeValue(disciplineId, siteId);

		}
		else if (disciplineId != -1 || siteId != -1) {

			filter = new SearchFilter(searchedDocs);

			long id = (disciplineId != -1) ? disciplineId : siteId;

			searchedDocs = filter.restrictListBasedOnAttributeValue(id);

		}

		return searchedDocs;
	}
}
