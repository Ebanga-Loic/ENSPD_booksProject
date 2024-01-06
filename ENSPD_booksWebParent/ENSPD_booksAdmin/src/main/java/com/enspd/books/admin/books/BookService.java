package com.enspd.books.admin.books;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enspd.books.common.entity.Book;

@Service
@Transactional
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

	public void updateBookEnabledStatus(Integer id, boolean enabled) {
		repo.updateEnabledStatus(id, enabled);
	}

	public void delete(Integer id) throws BookNotFoundException {
		Long countById = repo.countById(id);

		if (countById == null || countById == 0) {
			throw new BookNotFoundException("Impossible de trouver un livre avec ID" + id);
		}

		repo.deleteById(id);
	}

	public Book get(Integer id) throws BookNotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new BookNotFoundException("Impossible de trouver un livre avec ID" + id);
		}
	}
}
