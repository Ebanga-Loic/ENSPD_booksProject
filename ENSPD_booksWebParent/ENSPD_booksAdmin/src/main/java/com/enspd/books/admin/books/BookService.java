package com.enspd.books.admin.books;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.enspd.books.common.entity.Book;

@Service
@Transactional
public class BookService {
	public static final int BOOKS_PER_PAGE = 5;

	@Autowired
	private BookRepository repo;

	public List<Book> listAll() {
		return (List<Book>) repo.findAll();
	}

	public Page<Book> listByPage(int pageNum, String sortField, String sortDir, String keyword, Integer filiereId) {
		Sort sort = Sort.by(sortField);

		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

		Pageable pageable = PageRequest.of(pageNum - 1, BOOKS_PER_PAGE, sort);

		if (keyword != null && !keyword.isEmpty()) {
			if (filiereId != null && filiereId > 0) {
				String filiereIdMatch = String.valueOf(filiereId);
				return repo.searchInFiliere(filiereId, filiereIdMatch, keyword, pageable);
			}

			return repo.findAll(keyword, pageable);
		}

		if (filiereId != null && filiereId > 0) {
			String filiereIdMatch = String.valueOf(filiereId);
			return repo.findAllInFiliere(filiereId, filiereIdMatch, pageable);
		}

		return repo.findAll(pageable);
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
