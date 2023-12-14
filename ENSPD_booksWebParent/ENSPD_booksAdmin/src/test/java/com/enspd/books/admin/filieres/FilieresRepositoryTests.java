package com.enspd.books.admin.filieres;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.enspd.books.common.entity.Filieres;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class FilieresRepositoryTests {

	@Autowired
	private FilieresRepository repo;

	@Test
	public void testCreateRootFilieres() {
		Filieres filieres = new Filieres("Genie Automobile et Telecommunication");
		Filieres savedFilieres = repo.save(filieres);

		assertThat(savedFilieres.getId()).isGreaterThan(0);
	}

	@Test
	public void testGetFilieres() {
		Filieres filieres = repo.findById(2).get();
		System.out.println(filieres.getName());

		Filieres savedFilieres = repo.save(filieres);

		assertThat(savedFilieres.getId()).isGreaterThan(0);
	}
}
