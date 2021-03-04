package com.abinbev.admin.service.impl;

import java.io.StringWriter;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abinbev.admin.requestDto.MailDetailsDTO;
import com.abinbev.admin.service.EmailService;


/**
 * @author sruthy
 *
 */
@Transactional
@Service
public class EmailServiceImpl implements EmailService {
	
	
	@Autowired
	private JavaMailSender javaMailSender;

	
	/**
	 * @param url
	 * @param user
	 */
	private void constructEmail(String to, String htmlTemplate, MailDetailsDTO mailDetails) {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

				if (mailDetails.getMailType().equals("resetPassword")) {
					mimeMessage.setSubject(mailDetails.getSubject());
				} else if (mailDetails.getMailType().equals("welcomeMail")) {
					mimeMessage.setSubject("Welcome to EDUHEX");
				} else if (mailDetails.getMailType().equals("joiningMail")) {
					mimeMessage.setSubject(mailDetails.getSubject());
				}
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

				helper.setText(htmlTemplate, true);

			}
		};

	
		try {
			javaMailSender.send(preparator);
		} catch (MailException ex) {
			// simply log it and go on...
			System.err.println(ex.getMessage());
		}

	}

	@Override
	public void sendWelcomeMail(String email, String name) {

		Properties p = new Properties();
		p.setProperty("resource.loader", "class");
		p.setProperty("class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

		VelocityEngine velocity = new VelocityEngine();
		velocity.init(p);
		Template template = velocity.getTemplate("templates/welcomemail.vm");

		VelocityContext context = new VelocityContext();
		context.put("fullname", name);
		context.put("username", email);
		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		String htmlContent = writer.toString();

		MailDetailsDTO mailDetails = new MailDetailsDTO();
		mailDetails.setMailType("welcomeMail");
		mailDetails.setSubject("Welcome to Abinbev");
		
		constructEmail(email, htmlContent, mailDetails);

	}

	


}
