package com.enspd.books.admin.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.enspd.books.common.entity.Student;

public interface StudentRepository extends PagingAndSortingRepository<Student, Integer> {

	@Query("SELECT c FROM Student c WHERE CONCAT(c.email, ' ', c.firstName, ' ', c.lastName, ' ', "
			+ "c.filieres.name) LIKE %?1%")
	public Page<Student> findAll(String keyword, Pageable pageable);

	@Query("UPDATE Student c SET c.enabled = ?2 WHERE c.id = ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);

	@Query("SELECT c FROM Student c WHERE c.email = ?1")
	public Student findByEmail(String email);

	@Query("SELECT c FROM Student c WHERE c.matricule = ?1")
	public Student findByMatricule(String matricule);

	public Long countById(Integer id);
}
