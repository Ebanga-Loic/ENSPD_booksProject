package com.enspd.books.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enspd.books.common.entity.Filieres;
import com.enspd.books.common.entity.Student;
import com.enspd.books.filiere.FiliereRepository;

@Service
public class StudentService {

	@Autowired
	private FiliereRepository filiereRepo;
	@Autowired
	private StudentRepository studentRepo;

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
}
