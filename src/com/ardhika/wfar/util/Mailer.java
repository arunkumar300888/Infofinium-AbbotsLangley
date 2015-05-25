package com.ardhika.wfar.util;

import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailer {
	static Properties props;

	static {
		props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
	}

	// public static void doMail(String subject, String message, List<String>
	// recipients) {
	public static void doMail(String subject, String message, Set<String> recipients) {

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication("sysdsjvwf@gmail.com", "dsjvwf123");
			}
		});
		try {

			InternetAddress[] addressTo = new InternetAddress[recipients.size()];
			for (int i = 0; i < recipients.size(); i++) {

				//System.out.println("Index:" + recipients.toArray()[i]);
				String emailId = (String) recipients.toArray()[i];
				addressTo[i] = new InternetAddress(emailId);

			}
			/*
			 * for(int i=0;i<recipients.size();i++) { addressTo[i] = new
			 * InternetAddress(recipients.get(i)); }
			 */
			Message info = new MimeMessage(session);
			info.setFrom(new InternetAddress("sysdsjvwf@gmail.com"));

			info.setRecipients(Message.RecipientType.TO, addressTo);
			info.setSubject(subject);
			info.setText(message);
			Transport.send(info);
			System.out.println("Email has been sent successfully....");

		}
		catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

}
