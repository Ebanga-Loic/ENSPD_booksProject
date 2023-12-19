package com.enspd.books.admin.types;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.enspd.books.common.entity.Filieres;
import com.enspd.books.common.entity.Types;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class TypesRepositoryTests {

	@Autowired
	private TypesRepository repo;

	@Test
	public void testCreateTypes1() {
		Filieres GC = new Filieres(1);
		Types memoire = new Types("Mémoire");
		memoire.getFilieres().add(GC);

		Types savedType = repo.save(memoire);

		assertThat(savedType).isNotNull();
		assertThat(savedType.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateTypes2() {
		Filieres GP = new Filieres(9);
		Filieres GESI = new Filieres(4);

		Types oeuvre = new Types("Oeuvre");
		oeuvre.getFilieres().add(GP);
		oeuvre.getFilieres().add(GESI);

		Types savedTypes = repo.save(oeuvre);

		assertThat(savedTypes).isNotNull();
		assertThat(savedTypes.getId()).isGreaterThan(0);
	}

	@Test
	public void testFindAll() {
		Iterable<Types> types = repo.findAll();
		types.forEach(System.out::println);

		assertThat(types).isNotEmpty();
	}

	@Test
	public void testGetById() {
		Types types = repo.findById(1).get();

		assertThat(types.getName()).isEqualTo("Mémoire");
	}

	@Test
	public void testUpdateName() {
		String newName = "Dictionnaires";
		Types oeuvre = repo.findById(2).get();
		oeuvre.setName(newName);

		Types savedType = repo.save(oeuvre);
		assertThat(savedType.getName()).isEqualTo(newName);
	}

	@Test
	public void testDelete() {
		Integer id = 2;
		repo.deleteById(id);

		Optional<Types> result = repo.findById(id);

		assertThat(result.isEmpty());
	}
}
