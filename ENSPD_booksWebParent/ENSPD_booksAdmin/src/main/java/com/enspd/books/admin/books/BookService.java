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

	public String checkUnique(Integer id, String name) {
		boolean isCreatingNew = (id == null || id == 0);
		Book bookByName = repo.findByName(name);

		if (isCreatingNew) {
			if (bookByName != null)
				return "Duplicate";
		} else {
			if (bookByName != null && bookByName.getId() != id) {
				return "Duplicate";
			}
		}

		return "OK";
	}
}
