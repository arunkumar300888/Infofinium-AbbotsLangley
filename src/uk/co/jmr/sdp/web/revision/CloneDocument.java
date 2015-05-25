package uk.co.jmr.sdp.web.revision;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.ardhika.wfar.WfCase;

import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.domain.DocumentAttribute;
import uk.co.jmr.sdp.domain.DocumentReference;
import uk.co.jmr.sdp.web.revision.exception.DocumentCloningException;
import uk.co.jmr.sdp.web.util.Util;

public class CloneDocument {

	private Document document;

	private Document newDocument;

	public CloneDocument(Document document) throws DocumentCloningException {

		super();

		if (document != null) {
			this.document = document;

		}
		else {
			throw new DocumentCloningException("provide valid arguments to create cloned document",
				new IllegalArgumentException());

		}
	}

	public Document createClonedDoc(String fileName, WfCase wfcase) throws DocumentCloningException {
		if (!Util.isNull(fileName, wfcase)) {
			cloneDocment(fileName, wfcase);
			cloneDocumentRefference();
			loadDocumentAttributes();
			cloneSecuritygroup();
			return this.newDocument;
		}

		throw new DocumentCloningException("argument fileName or wfcase is null. Please provide valid aruguments",
			new IllegalArgumentException());
	}

	private void cloneDocment(String fileName, WfCase newWFCase) {

		Document newDoc = new Document(document.getDoctype(), document.getFilePath(), document.getAuthor(), fileName,
			document.getKeywords(), newWFCase.getId(), new Date(), newWFCase.getWfAttribute("Target Date").getAttrDate(),
			document.getEbNo(), 'Y',document.getUserFormId(),document.getDiscriminator());
		this.newDocument = newDoc;
	}

	public void cloneDocumentRefference() {

		Set<DocumentReference> clonedDocumentReferences = new HashSet<DocumentReference>();

		for (DocumentReference documentReference : document.getDocumentReference()) {

			DocumentReference clonedDocumentReference = documentReference.getCloned();
			clonedDocumentReference.setId(0);
			clonedDocumentReference.setDocumentId(newDocument.getId());
			clonedDocumentReferences.add(clonedDocumentReference);

		}

		newDocument.setDocumentReference(clonedDocumentReferences);

	}

	private void loadDocumentAttributes() {

		Set<DocumentAttribute> documentAttributes = document.getDocumentAttributes();

		Set<DocumentAttribute> newDocumentAttributes = new HashSet<DocumentAttribute>();

		for (DocumentAttribute documentAttribute : documentAttributes) {

			DocumentAttribute documentAttributenew = null;

			if (documentAttribute.getAttribute().getOrder() == 1) {

				documentAttributenew = new DocumentAttribute(newDocument, documentAttribute.getAttribute(),
					documentAttribute.getAttributeValue());

			}
			else if (documentAttribute.getAttribute().getOrder() == 2) {

				documentAttributenew = new DocumentAttribute(newDocument, documentAttribute.getAttribute(),
					documentAttribute.getAttributeValue());

			}
			else if (documentAttribute.getAttribute().getOrder() == 3) {

				documentAttributenew = new DocumentAttribute(newDocument, documentAttribute.getAttribute(),
					documentAttribute.getAttributeValue());

			}
			else if (documentAttribute.getAttribute().getOrder() == 4) {

				documentAttributenew = new DocumentAttribute(newDocument, documentAttribute.getAttribute(),
					documentAttribute.getAttributeValue());

			}

			newDocumentAttributes.add(documentAttributenew);

		}

		newDocument.setDocumentAttributes(newDocumentAttributes);

	}

	private void cloneSecuritygroup() {

		newDocument.setSecurityGroup(document.getSecurityGroup());

	}

}
