package com.enspd.books.admin.books;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.enspd.books.common.entity.Book;

public interface BookRepository extends PagingAndSortingRepository<Book, Integer> {

	public Book findByName(String name);

	@Query("UPDATE Book b SET b.enabled = ?2 WHERE b.id = ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);

	public Long countById(Integer id);

	@Query("SELECT p FROM Book p WHERE p.name LIKE %?1% " + "OR p.fullDescription LIKE %?1% " + "OR p.auteur LIKE %?1% "
			+ "OR p.types.name LIKE %?1% " + "OR p.filieres.name LIKE %?1%")
	public Page<Book> findAll(String keyword, Pageable pageable);

}
