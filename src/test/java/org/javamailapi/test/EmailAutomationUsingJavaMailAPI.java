package org.javamailapi.test;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import com.sun.mail.imap.IMAPFolder;

import jakarta.mail.Address;
import jakarta.mail.AuthenticationFailedException;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Store;
import jakarta.mail.internet.MimeMessage.RecipientType;

public class EmailAutomationUsingJavaMailAPI {

	private static final Logger logger = LoggerFactory.getLogger(EmailAutomationUsingJavaMailAPI.class);

	@Test
	public void readInboundEmails() {
		// create session object
		Session session = this.getImapSession();
		try {
			// connect to message store
			Store store = session.getStore("imap");
			store.connect("imap.gmail.com", 993, "emailautomationtest23@gmail.com", "AIzaSyDfxhNZATfjUHwEQK75im89cKc_DG5jyIw");
//	store.connect(<HOSTNAME>, <PORT>, <USERNAME>, <PASSWORD>);
			// open the inbox folder
			IMAPFolder inbox = (IMAPFolder) store.getFolder("INBOX");
			inbox.open(Folder.READ_WRITE);
			// fetch messages
			Message[] messages = inbox.getMessages();
			// read messages
			for (int i = 0; i < messages.length; i++) {
				Message msg = messages[i];
				Address[] fromAddress = msg.getFrom();
				String from = fromAddress[0].toString();
				String subject = msg.getSubject();
				Address[] toList = msg.getRecipients(RecipientType.TO);
				Address[] ccList = msg.getRecipients(RecipientType.CC);
				String contentType = msg.getContentType();
			}
		} catch (AuthenticationFailedException e) {
			logger.error("Exception in reading EMails : " + e.getMessage());
		} catch (MessagingException e) {
			logger.error("Exception in reading EMails : " + e.getMessage());
		} catch (Exception e) {
			logger.error("Exception in reading EMails : " + e.getMessage());
		}
	}

	private Session getImapSession() {
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imap");
		props.setProperty("mail.debug", "true");
		props.setProperty("mail.imap.host", "imap.gmail.com");
		props.setProperty("mail.imap.port", "993");
		props.setProperty("mail.imap.ssl.enable", "true");
		props.setProperty("mail.imap.auth.mechanisms", "XOAUTH2");
		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(true);
		System.out.println("Seesion value is :"+ session);
		return session;
	}
}
