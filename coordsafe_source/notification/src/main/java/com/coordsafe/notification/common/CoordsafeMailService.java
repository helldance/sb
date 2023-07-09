package com.coordsafe.notification.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.slf4j.*;
public class CoordsafeMailService {

	private static final Logger log = LoggerFactory.getLogger(CoordsafeMailService.class);

	private JavaMailSender javaMailSender;
	private SimpleMailMessage simpleMailMessage;
	VelocityEngine velocityEngine;
	String mailContent ;
	public void sendMail(String subject, String content, String[] bcc) {

		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();

			MimeMessageHelper messageHelper = new MimeMessageHelper(
					mimeMessage, true, "UTF-8");
			messageHelper.setFrom(simpleMailMessage.getFrom()); 
			messageHelper.setSubject(subject); 
			//messageHelper.setText(content); 
			messageHelper.setBcc("wanghongbin99@gmail.com");//.setTo("wanghongbin99@gmail.com"); 
			for(int i = 0; i <bcc.length;i ++){
				log.info("The Email address is " + bcc[i]);
			}
			//messageHelper.setBcc(bcc);
			messageHelper.setTo(bcc);
			
			 Map<String,String> model = new HashMap<String,String>();
	            model.put("subject", subject);
	            model.put("content", content);
	            log.info("Using Velocity to sent out text...");
				String text = VelocityEngineUtils.mergeTemplateIntoString(
	               velocityEngine, mailContent, model);
				messageHelper.setText(text, true);
				
			
			log.info(messageHelper.getMimeMessage().getContent().toString());

			javaMailSender.send(mimeMessage);

		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}


	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}


	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}


	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}


	public String getMailContent() {
		return mailContent;
	}


	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}
	
	/**
	 * Testing Mail Server connection 
	 * @param args
	 */
	 public void sendMailTest(String m_from,String m_to,String m_subject,String m_body){
	      try {
	            Session m_Session;
	            Message m_simpleMessage;
	            InternetAddress m_fromAddress;
	            InternetAddress m_toAddress;
	            Properties m_properties;

	            m_properties     = new Properties();
	            m_properties.put("mail.smtp.host", "mail.coordsafe.com.sg"); 
	            
	           // m_properties.put("mail.smtp.socketFactory.port", "587");
	           // m_properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
	          //m_properties.put("mail.smtp.starttls.enable", "true");
	            m_properties.put("mail.smtp.auth", "true");
	            m_properties.put("mail.smtp.port", "587");
	            

	            m_Session=Session.getDefaultInstance(m_properties,new Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication("Info@coordsafe.com.sg","coordsafe2013"); // username and the password
	                }
	            });

	            m_simpleMessage  =   new MimeMessage(m_Session);
	            m_fromAddress    =   new InternetAddress(m_from);
	            m_toAddress      =   new InternetAddress(m_to);

	            m_simpleMessage.setFrom(m_fromAddress);
	            m_simpleMessage.setRecipient(RecipientType.TO, m_toAddress);
	            m_simpleMessage.setSubject(m_subject);

	            m_simpleMessage.setContent(m_body, "text/html");

	            //m_simpleMessage.setContent(m_body,"text/plain");

	            Transport.send(m_simpleMessage);
	        } catch (MessagingException ex) {
	            ex.printStackTrace();
	        }
	    }
	    public static void main(String[] args) {
	    	CoordsafeMailService send_mail    =   new CoordsafeMailService();
	      String empName = "WANG KEMU";
	      String title ="<b>Hi !"+empName+"</b>";
	      send_mail.sendMailTest("info@coordsafe.com.sg", "wanghongbin99@gmail.com", "Please apply for leave for the following dates", title+"<br>by<br><b>HR<b>");
	    }

}
