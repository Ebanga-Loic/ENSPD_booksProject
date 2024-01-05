package com.enspd.books.admin.books;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.enspd.books.common.entity.Book;
import com.enspd.books.common.entity.Filieres;
import com.enspd.books.common.entity.Types;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class BookRepositoryTests {

	@Autowired
	private BookRepository repo;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testCreateProduct() {
		Types types = entityManager.find(Types.class, 1);
		Filieres filieres = entityManager.find(Filieres.class, 4);

		Book book = new Book();
		book.setName("The Pragmatic Programmer: Your Journey to Mastery");
		book.setAuteur("Dave Thomas et Andy Hunt");
		book.setFullDescription(
				"Un guide pratique pour les programmeurs qui aborde divers aspects du développement logiciel, de la gestion de projet à la performance du code.");

		book.setTypes(types);
		book.setFilieres(filieres);

		book.setEnabled(true);
		book.setInStock(true);

		book.setCreatedTime(new Date());
		book.setUpdatedTime(new Date());

		Book savedBook = repo.save(book);

		assertThat(savedBook).isNotNull();
		assertThat(savedBook.getId()).isGreaterThan(0);
	}

	@Test
	public void testListAllBooks() {
		Iterable<Book> iterableBooks = repo.findAll();

		iterableBooks.forEach(System.out::println);
	}

	@Test
	public void testGetBook() {
		Integer id = 1;
		Book book = repo.findById(id).get();
		System.out.println(book);

		assertThat(book).isNotNull();
	}

	@Test
	public void testUpdateBook() {
		Integer id = 1;
		Book book = repo.findById(id).get();
		book.setInStock(false);

		repo.save(book);

		Book updatedBook = entityManager.find(Book.class, id);

		assertThat(updatedBook.isInStock()).isEqualTo(false);
	}

	@Test
	public void testDeleteBook() {
		Integer id = 1;
		repo.deleteById(id);

		Optional<Book> result = repo.findById(id);

		assertThat(!result.isPresent());
	}

	@Test
	public void testSaveBookWithDetails() {
		Integer bookId = 1;
		Book book = repo.findById(bookId).get();

		book.addDetail("Nombre pages", "150");
		book.addDetail("Numéro de téléphone", "652452678");
		book.addDetail("Mention", "Honorable");

		Book savedBook = repo.save(book);
		assertThat(savedBook.getDetails()).isNotEmpty();
	}
}
