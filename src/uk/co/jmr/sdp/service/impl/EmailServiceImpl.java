package uk.co.jmr.sdp.service.impl;

import java.util.List;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import uk.co.jmr.sdp.email.Attachment;
import uk.co.jmr.sdp.service.EmailService;

@Service("emailService")
public class EmailServiceImpl implements EmailService {
	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void sendEmail(String[] to, String[] cc, String[] bcc, String subject, String body) {

		SimpleMailMessage message = new SimpleMailMessage();
		if (to == null || to.length == 0)
			return;

		message.setTo(to);
		if (cc != null && cc.length > 0)
			message.setCc(cc);

		if (bcc != null && bcc.length > 0)
			message.setBcc(bcc);

		message.setSubject(subject);
		message.setText(body);
		message.setFrom("formsportal@bentleywood.harrow.sch.uk");
		mailSender.send(message);
	}

	@Override
	public void sendEmail(String[] to, String[] cc, String[] bcc, String subject, String body, Attachment[] attachments) {

		if (to == null || to.length == 0)
			return;

		mailSender.send(new MessagePreparator(to, cc, bcc, subject, body, attachments));
	}
}

class MessagePreparator implements MimeMessagePreparator {
	String[] to;
	String[] cc;
	String[] bcc;
	String subject;
	String body;
	Attachment[] attachments;

	MessagePreparator(String[] to, String[] cc, String[] bcc, String subject, String body, Attachment[] attachments) {

		this.to = to;
		this.cc = cc;
		this.bcc = bcc;
		this.subject = subject;
		this.body = body;
		this.attachments = attachments;
	}

	@Override
	public void prepare(MimeMessage mm) throws Exception {

		MimeMessageHelper message = new MimeMessageHelper(mm, true, "UTF-8");
		message.setTo(to);
		if (cc != null && cc.length > 0)
			message.setCc(cc);

		if (bcc != null && bcc.length > 0)
			message.setBcc(bcc);

		message.setSubject(subject);
		message.setText(body);
		message.setFrom("formsportal@bentleywood.harrow.sch.uk");
		if (attachments == null)
			return;

		for (int i = 0; i < attachments.length; i++) {
			if (attachments[i] == null)
				continue;

			if (attachments[i].isInline()) {
				message.addInline("CONTENT-" + i, attachments[i].getIns(), attachments[i].getMimeType());
			}
			else {
				message.addAttachment(attachments[i].getName(), attachments[i].getIns(), attachments[i].getMimeType());
			}
		}
	}
}
