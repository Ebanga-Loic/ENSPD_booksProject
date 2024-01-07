package com.enspd.books.article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.enspd.books.common.entity.Book;

public interface BookRepository extends PagingAndSortingRepository<Book, Integer> {

	@Query("SELECT p FROM Book p WHERE p.enabled = true " + "AND (p.filieres.id = ?1 OR p.filieres.name LIKE %?2%)"
			+ " ORDER BY p.name ASC")
	public Page<Book> listByFiliere(Integer categoryId, String categoryIDMatch, Pageable pageable);
}
