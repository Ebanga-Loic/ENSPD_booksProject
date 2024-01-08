package com.enspd.books.student;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.enspd.books.common.entity.Filieres;
import com.enspd.books.common.entity.Student;
import com.enspd.books.filiere.FiliereRepository;

import net.bytebuddy.utility.RandomString;

@Service
@Transactional
public class StudentService {

	@Autowired
	private FiliereRepository filiereRepo;

	@Autowired
	private StudentRepository studentRepo;

	@Autowired
	PasswordEncoder passwordEncoder;

	public List<Filieres> listAllFilieres() {
		return filiereRepo.findAllByOrderByNameAsc();
	}

	public String checkUnique(Integer id, String email, String matricule) {
		boolean isCreatingNew = (id == null || id == 0);

		Student studentByEmail = studentRepo.findByEmail(email);

		if (isCreatingNew) {
			if (studentByEmail != null) {
				return "DuplicateEmail";
			} else {
				Student studentByMatricule = studentRepo.findByMatricule(matricule);
				if (studentByMatricule != null) {
					return "DuplicateMatricule";
				}
			}
		} else {
			if (studentByEmail != null && studentByEmail.getId() != id) {
				return "DuplicateEmail";
			}

			Student studentByMatricule = studentRepo.findByMatricule(matricule);
			if (studentByMatricule != null && studentByMatricule.getId() != id) {
				return "DuplicateMatricule";
			}

		}

		return "OK";
	}

	public void registerStudent(Student student) {
		encodePassword(student);
		student.setEnabled(false);
		student.setCreatedTime(new Date());

		String randomCode = RandomString.make(64);
		student.setVerificationCode(randomCode);

		studentRepo.save(student);

	}

	private void encodePassword(Student student) {
		String encodedPassword = passwordEncoder.encode(student.getPassword());
		student.setPassword(encodedPassword);
	}

	public boolean verify(String verificationCode) {
		Student student = studentRepo.findByVerificationCode(verificationCode);

		if (student == null || student.isEnabled()) {
			return false;
		} else {
			studentRepo.enable(student.getId());
			return true;
		}
	}

}
