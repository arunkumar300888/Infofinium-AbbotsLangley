package uk.co.jmr.sdp.web.revision;

import java.text.SimpleDateFormat;
import java.util.Date;

import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.web.util.Util;

public class AbandonedDocument {

	private Document document;
	private static final String ABANDONED = "A";

	public AbandonedDocument(Document document) {

		super();
		this.document = document;
	}

	public Document setDocumentAbandoned() {

		renameDocument(document.getName());
		document.setRevisionable(false);
		document.setAbandon(true);
		return document;
	}

	private void renameDocument(String docName) {

		String extention = Util.getType(docName);

		StringBuilder tempFileName = new StringBuilder(Util.removeFileExtention(docName));
		tempFileName.append("-" + ABANDONED + "-").append(fileNamedateFormat()).append(extention);

		document.setName(tempFileName.toString());

	}

	private String fileNamedateFormat() {

		SimpleDateFormat simple = new SimpleDateFormat("ddMMyyyy-HHmmss");

		return simple.format(new Date());

	}

}
