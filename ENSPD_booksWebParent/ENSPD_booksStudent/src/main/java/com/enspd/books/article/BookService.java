package com.enspd.books.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.enspd.books.common.entity.Book;

@Service
public class BookService {
	public static final int BOOKS_PER_PAGE = 10;

	@Autowired
	private BookRepository repo;

	public Page<Book> listByFiliere(int pageNum, Integer categoryId) {
		String categoryIdMatch =String.valueOf(categoryId);
		Pageable pageable = PageRequest.of(pageNum - 1, BOOKS_PER_PAGE);

		return repo.listByFiliere(categoryId, categoryIdMatch, pageable);

	}
}
