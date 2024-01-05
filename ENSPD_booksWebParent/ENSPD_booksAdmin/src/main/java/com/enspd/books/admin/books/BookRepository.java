package com.enspd.books.admin.books;

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

}
