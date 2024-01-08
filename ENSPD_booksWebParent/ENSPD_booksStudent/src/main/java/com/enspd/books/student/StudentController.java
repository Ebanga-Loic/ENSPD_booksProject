package com.enspd.books.student;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.enspd.books.Utility;
import com.enspd.books.common.entity.Filieres;
import com.enspd.books.common.entity.Student;
import com.enspd.books.setting.EmailSettingBag;
import com.enspd.books.setting.SettingService;

@Controller
public class StudentController {
	@Autowired
	private StudentService studentService;

	@Autowired
	private SettingService settingService;

	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		List<Filieres> listFilieres = studentService.listAllFilieres();

		model.addAttribute("listFilieres", listFilieres);
		model.addAttribute("pageTitle", "Inscription de l'étudiant");
		model.addAttribute("student", new Student());

		return "register/register_form";
	}

	@PostMapping("/create_customer")
	public String createStudent(Student student, Model model, HttpServletRequest request)
			throws UnsupportedEncodingException, MessagingException {
		studentService.registerStudent(student);
		sendVerificationEmail(request, student);

		model.addAttribute("pageTitle", "Inscription réussie !");

		return "/register/register_success";
	}

	private void sendVerificationEmail(HttpServletRequest request, Student student)
			throws UnsupportedEncodingException, MessagingException {
		EmailSettingBag emailSettings = settingService.getEmailSettings();
		JavaMailSenderImpl mailSender = Utility.prepareMailSender(emailSettings);

		String toAddress = student.getEmail();
		String subject = emailSettings.getCustomerVerifySubject();
		String content = emailSettings.getCustomerVerifyContent();

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(emailSettings.getFromAddress(), emailSettings.getSenderName());
		helper.setTo(toAddress);
		helper.setSubject(subject);

		content = content.replace("[[name]]", student.getFullName());

		String verifyURL = Utility.getSiteURL(request) + "/verify?code=" + student.getVerificationCode();

		content = content.replace("[[URL]]", verifyURL);

		helper.setText(content, true);

		mailSender.send(message);

		System.out.println("Adresser à : " + toAddress);
		System.out.println("Vérifier l'URL : " + verifyURL);
	}

	@GetMapping("/verify")
	public String verifyAccount(@Param("code") String code, Model model) {
		boolean verified = studentService.verify(code);

		return "register/" + (verified ? "verify_success" : "verify_fail");
	}

}
