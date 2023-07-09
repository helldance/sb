package com.coordsafe.notification.common;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;


public class MailService {
	
	@Autowired
	JavaMailSenderImpl mailSenderInfo;
	
	
	public static void main(String[] args) throws Exception {
		
		MailService svr = new MailService();
		svr.sentMail();
	}
	
	
	public void sentMail() throws MessagingException{
		

		
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();


		senderImpl.setHost("mail.coordsafe.com.sg");
		//senderImpl.setPort(143);

		MimeMessage mailMessage = senderImpl.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true);

		messageHelper.setTo("wanghongbin99@gmail.com");
		messageHelper.setFrom("info@coordsafe.com.sg");
		messageHelper.setSubject("Notification Mail！" + new Date());
		messageHelper.setText(
				"<html><head></head><body><h1>hello!!This is a notification from COORDSAFE</h1>"
						+ "<img src=\"cid:aaa\"/></body></html>", true);

		FileSystemResource img = new FileSystemResource(new File("C:/Users/public/Pictures/Sample Pictures/Chrysanthemum.jpg"));

		messageHelper.addInline("aaa", img);

		senderImpl.setUsername("Info@coordsafe.com.sg"); 
		senderImpl.setPassword("coordsafeinfo"); 
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true"); 
		prop.put("mail.smtp.timeout", "25000");
		senderImpl.setJavaMailProperties(prop);


		senderImpl.send(mailMessage);

		System.out.println("Sent out successfully..");
	
		
	}
}