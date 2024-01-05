package com.enspd.books.admin.books;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enspd.books.common.entity.Book;

@Service
public class BookService {

	@Autowired
	private BookRepository repo;

	public List<Book> listAll() {
		return (List<Book>) repo.findAll();
	}

	public Book save(Book book) {
		if (book.getId() == null) {
			book.setCreatedTime(new Date());
		}

		book.setUpdatedTime(new Date());

		return repo.save(book);
	}
}
