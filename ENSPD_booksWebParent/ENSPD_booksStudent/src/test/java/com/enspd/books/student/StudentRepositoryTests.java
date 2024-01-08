package com.enspd.books.student;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.enspd.books.common.entity.Filieres;
import com.enspd.books.common.entity.Student;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class StudentRepositoryTests {

	@Autowired
	private StudentRepository repo;
	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testCreateStudent1() {
		Integer filiereId = 2;
		Filieres filiere = entityManager.find(Filieres.class, filiereId);

		Student student = new Student();
		student.setFilieres(filiere);
		student.setFirstName("David");
		student.setLastName("Fountaine");
		student.setPassword("password123");
		student.setEmail("david.s.fountaine@gmail.com");
		student.setPhoneNumber("312-462-7518");
		student.setCreatedTime(new Date());

		Student savedStudent = repo.save(student);

		assertThat(savedStudent).isNotNull();
		assertThat(savedStudent.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateStudent2() {
		Integer filiereId = 3; // India
		Filieres filiere = entityManager.find(Filieres.class, filiereId);

		Student student = new Student();
		student.setFilieres(filiere);
		student.setFirstName("Sanya");
		student.setLastName("Lad");
		student.setPassword("password456");
		student.setEmail("sanya.lad2020@gmail.com");
		student.setPhoneNumber("02224928052");
		student.setCreatedTime(new Date());

		Student savedStudent = repo.save(student);

		assertThat(savedStudent).isNotNull();
		assertThat(savedStudent.getId()).isGreaterThan(0);
	}

	@Test
	public void testListStudents() {
		Iterable<Student> student = repo.findAll();
		student.forEach(System.out::println);

		assertThat(student).hasSizeGreaterThan(1);
	}

	@Test
	public void testUpdateStudent() {
		Integer studentId = 1;
		String lastName = "Stanfield";

		Student student = repo.findById(studentId).get();
		student.setLastName(lastName);
		student.setEnabled(true);

		Student updatedStudent = repo.save(student);
		assertThat(updatedStudent.getLastName()).isEqualTo(lastName);
	}

	@Test
	public void testGetStudent() {
		Integer studentId = 2;
		Optional<Student> findById = repo.findById(studentId);

		assertThat(findById).isPresent();

		Student student = findById.get();
		System.out.println(student);
	}

	@Test
	public void testDeleteStudent() {
		Integer studentId = 2;
		repo.deleteById(studentId);

		Optional<Student> findById = repo.findById(studentId);
		assertThat(findById).isNotPresent();
	}

	@Test
	public void testFindByEmail() {
		String email = "david.s.fountaine@gmail.com";
		Student student = repo.findByEmail(email);

		assertThat(student).isNotNull();
		System.out.println(student);
	}
	
	@Test
	public void testFindByMatricule() {
		String matricule = "123456";
		Student student = repo.findByMatricule(matricule);

		assertThat(student).isNotNull();
		System.out.println(student);
	}

	@Test
	public void testFindByVerificationCode() {
		String code = "code_123";
		Student student = repo.findByVerificationCode(code);

		assertThat(student).isNotNull();
		System.out.println(student);
	}

	@Test
	public void testEnableStudent() {
		Integer studentId = 1;
		repo.enable(studentId);

		Student student = repo.findById(studentId).get();
		assertThat(student.isEnabled()).isTrue();
	}
}
