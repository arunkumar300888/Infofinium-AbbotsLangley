package uk.co.jmr.sdp.web.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.domain.DocumentAttribute;

/**
 * @author Administrator
 * 
 */
public class SearchFilter {

	private List<Document> documents;

	/**
	 * @param documents
	 */
	public SearchFilter(List<Document> documents) {

		super();
		this.documents = documents;
	}

	/**
	 * @param attri_value_id
	 * @return
	 */
	public List<Document> restrictListBasedOnAttributeValue(long attri_value_id) {

		List<Document> documentsList_attriValue = new ArrayList<Document>();

		for (Document document : documents) {

			Set<DocumentAttribute> docattri_set = document.getDocumentAttributes();

			for (DocumentAttribute documentAttribute : docattri_set) {

				if (documentAttribute.getAttributeValue().getId() == attri_value_id) {

					documentsList_attriValue.add(document);

				}

			}

		}

		return documentsList_attriValue;
	}

	/**
	 * @param attri_value_id
	 * @param siteId
	 * @return
	 */
	public List<Document> restrictListBasedOnAttributeValue(long attri_value_id, long siteId) {

		List<Document> documentsList_attriValue = new ArrayList<Document>();
		List<Document> documentList_siteValue = new ArrayList<Document>();

		for (Document document : documents) {

			Set<DocumentAttribute> docattri_set = document.getDocumentAttributes();

			for (DocumentAttribute documentAttribute : docattri_set) {

				if (documentAttribute.getAttributeValue().getId() == attri_value_id) {

					documentsList_attriValue.add(document);

				}
				else if (documentAttribute.getAttributeValue().getId() == siteId) {

					documentList_siteValue.add(document);
				}

			}

		}

		if (!documentsList_attriValue.isEmpty() && !documentList_siteValue.isEmpty()) {

			documentsList_attriValue.retainAll(documentList_siteValue);

			return documentsList_attriValue;

		}

		return new ArrayList<Document>();
	}
	
	
	
	/** 
	 *  @param project_attri_value_id
	 *  @param category_attri_value_id
	 *  @param discipline_attri_value_id
	 *  @param site_attri_value_id
	 *  @return
	 */
	
	public List<Document> restrictListBasedOnAttributeValue(long project_attri_value_id,long category_attri_value_id,long discipline_attri_value_id ,long site_attri_value_id) {
		List<Document> documentsList_projectValue = new ArrayList<Document>();
		List<Document> documentsList_categoryValue = new ArrayList<Document>();
		List<Document> documentsList_disciplineValue = new ArrayList<Document>();
		List<Document> documentList_siteValue = new ArrayList<Document>();

		for (Document document : documents) {

			Set<DocumentAttribute> docattri_set = document.getDocumentAttributes();

			for (DocumentAttribute documentAttribute : docattri_set) {

				if (documentAttribute.getAttributeValue().getId() == project_attri_value_id) {

					documentsList_projectValue.add(document);
				}
				else if (documentAttribute.getAttributeValue().getId() == category_attri_value_id) {

					documentsList_categoryValue.add(document);
				}
				else if (documentAttribute.getAttributeValue().getId() == discipline_attri_value_id) {

					documentsList_disciplineValue.add(document);
				}
				else if (documentAttribute.getAttributeValue().getId() == site_attri_value_id) {

					documentList_siteValue.add(document);
				}
				

			}

		}

		if (!documentsList_projectValue.isEmpty() && !documentsList_categoryValue.isEmpty() && !documentsList_disciplineValue.isEmpty() && !documentList_siteValue.isEmpty()) {

			documentsList_projectValue.retainAll(documentsList_categoryValue);
			documentsList_projectValue.retainAll(documentsList_disciplineValue);
			documentsList_projectValue.retainAll(documentList_siteValue);
			return documentsList_projectValue;
		}

		return new ArrayList<Document>();
	}

}
