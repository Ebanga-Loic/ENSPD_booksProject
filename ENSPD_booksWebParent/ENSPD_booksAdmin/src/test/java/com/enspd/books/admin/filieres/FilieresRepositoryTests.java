package com.enspd.books.admin.filieres;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import com.enspd.books.common.entity.Filieres;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class FilieresRepositoryTests {

	@Autowired
	private FilieresRepository repo;

	@Test
	public void testCreateFilieres() {
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
	
	@Test 
	public void testListFilieres() {
		List<Filieres> filieres = (List<Filieres>) repo.findFilieres(Sort.by("name").ascending());

		for (Filieres filiere : filieres) {
			if (filiere.getName() != null) {
				System.out.println("--" +filiere.getName());
			}
		}
	}
	
	@Test
	public void testFindByName() {
		String name = "Genie Civil";
		Filieres filiere = repo.findByName(name);
		
		assertThat(filiere).isNotNull();
		assertThat(filiere.getName()).isEqualTo(name);
	}
	
	
	@Test
	public void testFindByAlias() {
		String alias = "GC";
		Filieres filiere = repo.findByAlias(alias);
		
		assertThat(filiere).isNotNull();
		assertThat(filiere.getAlias()).isEqualTo(alias);
	}
}
