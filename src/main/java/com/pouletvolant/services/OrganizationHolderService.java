package com.pouletvolant.services;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.pouletvolant.models.Application;
import com.pouletvolant.models.Employer;
import com.pouletvolant.models.Offer;
import com.pouletvolant.models.OrganizationHolder;
import com.pouletvolant.models.Student;

@Service
public class OrganizationHolderService {

	@Autowired
	private JavaMailSender mailSender;

	@Transactional
	public void notifyOfNewApplication(Offer offer, Application application) throws MessagingException, IOException {
		OrganizationHolder holder = offer.getOrganization().getHolder();
		if (holder instanceof Employer) {
			// TODO create a notification for the user when the notifications system is up.
		}

		Student student = application.getStudent();
		MimeMessage msg = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg); // TODO when there is a way to get the CV from the student, join it to the message
		helper.setTo(holder.getEmail());
		helper.setSubject("Nouvelle application pour offre " + offer.getTitle());
		// @formatter:off
		helper.setText("<h1>" + student.getName() + "</h1>"
				+ "<p>A appliqué pour votre offre <b>" + offer.getTitle() + "</b>. "
				+ "Vous pouvez contacter l'applicant(e) à l'adresse de courriel suivante: <a href=\"mailto:" + student.getEmail() + "\">" + student.getEmail() + "</a></p>", true);
		// @formatter:on
		mailSender.send(msg);
	}
}
