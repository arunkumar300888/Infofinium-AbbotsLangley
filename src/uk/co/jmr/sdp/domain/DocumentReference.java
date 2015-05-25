package uk.co.jmr.sdp.domain;

import uk.co.jmr.sdp.domain.Document;

public class DocumentReference implements Cloneable {

	private long id;
	private long documentId;
	private Document referenceDocument;

	public DocumentReference(long id, long documentId, Document referenceDocument) {

		super();
		this.id = id;
		this.documentId = documentId;
		this.referenceDocument = referenceDocument;
	}

	public Document getReferenceDocument() {

		return referenceDocument;
	}

	public void setReferenceDocument(Document referenceDocument) {

		this.referenceDocument = referenceDocument;
	}

	public DocumentReference() {

		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {

		return id;
	}

	public void setId(long id) {

		this.id = id;
	}

	public long getDocumentId() {

		return documentId;
	}

	public void setDocumentId(long documentId) {

		this.documentId = documentId;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((referenceDocument == null) ? 0 : referenceDocument.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		// System.out.println("Object:" +obj);

		if (obj != null && obj instanceof DocumentReference) {

			DocumentReference other = (DocumentReference) obj;
			if (referenceDocument.getId() == other.referenceDocument.getId()) {

				return true;
			}
		}

		return false;
	}

	public DocumentReference getCloned() {

		return (DocumentReference) clone();

	}

	@Override
	protected Object clone() {

		try {
			return super.clone();
		}
		catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

}
