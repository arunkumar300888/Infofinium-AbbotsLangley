package uk.co.jmr.sdp.service;

import uk.co.jmr.sdp.email.Attachment;

public interface EmailService {
	public void sendEmail(String[] to, String[] cc, String[] bcc, String subject, String body);

	public void sendEmail(String[] to, String[] cc, String[] bcc, String subject, String body, Attachment[] attachments);
}
