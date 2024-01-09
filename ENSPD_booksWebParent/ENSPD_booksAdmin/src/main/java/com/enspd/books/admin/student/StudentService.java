package com.enspd.books.admin.student;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.enspd.books.admin.filieres.FilieresRepository;
import com.enspd.books.common.entity.Filieres;
import com.enspd.books.common.entity.Student;
import com.enspd.books.common.exception.StudentNotFoundException;

@Service
@Transactional
public class StudentService {
	public static final int STUDENTS_PER_PAGE = 10;

	@Autowired
	private StudentRepository studentRepo;
	@Autowired
	private FilieresRepository filiereRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public Page<Student> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

		Pageable pageable = PageRequest.of(pageNum - 1, STUDENTS_PER_PAGE, sort);

		if (keyword != null) {
			return studentRepo.findAll(keyword, pageable);
		}

		return studentRepo.findAll(pageable);
	}

	public void updateStudentEnabledStatus(Integer id, boolean enabled) {
		studentRepo.updateEnabledStatus(id, enabled);
	}

	public Student get(Integer id) throws StudentNotFoundException {
		try {
			return studentRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new StudentNotFoundException("Impossible de trouver des étudiants avec ID " + id);
		}
	}

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

	public void save(Student studentInForm) {
		if (!studentInForm.getPassword().isEmpty()) {
			String encodedPassword = passwordEncoder.encode(studentInForm.getPassword());
			studentInForm.setPassword(encodedPassword);
		} else {
			Student studentInDB = studentRepo.findById(studentInForm.getId()).get();
			studentInForm.setPassword(studentInDB.getPassword());
		}
		studentRepo.save(studentInForm);
	}

	public void delete(Integer id) throws StudentNotFoundException {
		Long count = studentRepo.countById(id);
		if (count == null || count == 0) {
			throw new StudentNotFoundException("Impossible de trouver des étudiants avec ID " + id);
		}

		studentRepo.deleteById(id);
	}

}
