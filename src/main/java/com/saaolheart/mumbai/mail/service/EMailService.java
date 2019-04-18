package com.saaolheart.mumbai.mail.service;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.saaolheart.mumbai.mail.domain.MailContentDomain;

@Service
public class EMailService {

	

	@Autowired
    private JavaMailSender emailSender;
	
	public void sendReciept(MailContentDomain mailDomain)  {
		 MimeMessage message = emailSender.createMimeMessage();
		    MimeMessageHelper helper = null;
		   
		    try {
		    	helper = new MimeMessageHelper(message, true);
			    mailDomain.setSentTo("mohit.bansal@gep.com");
		    	 helper.setTo(mailDomain.getSentTo());
				    helper.setSubject(mailDomain.getSubject());
				helper.setText(mailDomain.getMailBody());
				  FileSystemResource file 
			      = new FileSystemResource(new File(mailDomain.getFilePath()));
			    helper.addAttachment("Invoice", file);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(Exception e) {
				e.printStackTrace();
			}
		         
		  
		    emailSender.send(message);
	}
}
