package com.coordsafe.notification.common;

import java.io.File;
import java.util.Properties;

import javax.mail.internet.MimeMessage;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class TestCoordsafeMailService {

	private JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
	
	private SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
	public static void main(String[] args){
		TestCoordsafeMailService mail = new TestCoordsafeMailService();
		mail.sendMail("Notification from Coordsafe", "Notification from Coordsafe as belowed..." , "wanghongbin99@gmail.com");
	}
	public void sendMail(String subject, String content, String to) {

		try {
			
			simpleMailMessage.setFrom("info@coordsafe.com.sg");
			
			javaMailSender.setHost("mail.coordsafe.com.sg");
			javaMailSender.setUsername("Info@coordsafe.com.sg");
			javaMailSender.setPassword("coordsafeinfo");
			Properties prop = new Properties();
			prop.put("mail.smtp.auth", "true"); 
			prop.put("mail.smtp.timeout", "25000");
			javaMailSender.setJavaMailProperties(prop);
			
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();

			MimeMessageHelper messageHelper = new MimeMessageHelper(
					mimeMessage, true, "UTF-8");
			messageHelper.setFrom("info@coordsafe.com.sg"); 
			messageHelper.setSubject(subject); 
			//messageHelper.setText(content); 
			messageHelper.setTo("wanghongbin99@gmail.com"); 
			
			messageHelper
					.setText(
							"<html>"
									+ "<body>"
									+ "<BR>"
									+ "<div align='center'>"
									+ "<img src=\"cid:imageid\"/>"
									+ "<BR>"
									+ "<h4>"
									+ content
									//+ "<a href='http://localhost:8080/fleet-lin/login'>http://www.coordsafe.com.sg</a>"
									+ "</h4>" + "</div>" + "</body>"
									+ "</html>", true);
			FileSystemResource image = new FileSystemResource(new File("C:/Users/public/Pictures/Sample Pictures/Chrysanthemum.jpg"));
			messageHelper.addInline("imageid", image);

/*	
			messageHelper.setText(
					"<html><head></head><body><h1>hello!!This is a notification from COORDSAFE</h1>"
							+ "<img src=\"cid:aaa\"/>" + 
							"</body></html>",
							true);

			FileSystemResource img = new FileSystemResource(new File("C:/Users/public/Pictures/Sample Pictures/Chrysanthemum.jpg"));

			messageHelper.addInline("aaa", img);
*/
			//ClassPathResource image = new ClassPathResource("images/body.png");

			javaMailSender.send(mimeMessage);
			System.out.println("sent out successfully...");
			
		} catch (Exception e) {
			System.out.println("email exception：" + e);
		}
	}




}
