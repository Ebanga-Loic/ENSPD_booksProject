package com.enspd.books.article;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.enspd.books.article.BookRepository;
import com.enspd.books.common.entity.Book;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BookRepositoryTests {

	@Autowired
	BookRepository repo;

	@Test
	public void testFindByName() {
		String name = "Analyse structurelle";
		Book book = repo.findByName(name);

		assertThat(book).isNotNull();
	}
}
