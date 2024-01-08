package com.enspd.books.student;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.enspd.books.common.entity.Student;

public interface StudentRepository extends CrudRepository<Student, Integer> {

	@Query("SELECT c FROM Student c WHERE c.email = ?1")
	public Student findByEmail(String email);

	@Query("SELECT c FROM Student c WHERE c.verificationCode = ?1")
	public Student findByVerificationCode(String code);

	@Query("UPDATE Student c SET c.enabled = true WHERE c.id = ?1")
	@Modifying
	public void enable(Integer id);
}
