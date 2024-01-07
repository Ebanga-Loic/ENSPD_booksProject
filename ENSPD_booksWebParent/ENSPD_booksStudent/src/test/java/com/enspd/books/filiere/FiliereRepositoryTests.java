package com.enspd.books.filiere;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.enspd.books.common.entity.Filieres;
import com.enspd.books.filiere.FiliereRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class FiliereRepositoryTests {

	@Autowired
	private FiliereRepository repo;

	@Test
	public void testListEnabledCategories() {
		List<Filieres> filieres = repo.findAllEnabled();
		filieres.forEach(filiere -> {
			System.out.println(filiere.getName() + " (" + filiere.isEnabled() + ")");
		});
	}
}
