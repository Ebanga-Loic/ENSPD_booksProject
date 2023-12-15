package com.enspd.books.admin.filieres;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.enspd.books.common.entity.Filieres;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class FilieresServiceTests {

	@MockBean
	private FilieresRepository repo;

	@InjectMocks
	private FilieresService service;

	@Test
	public void testCheckUniqueInNewModeReturnDuplicateName() {
		Integer id = null;
		String name = "Genie Civil";
		String alias = "GC";

		Filieres filiere = new Filieres(id, name, alias);

		Mockito.when(repo.findByName(name)).thenReturn(filiere);
		Mockito.when(repo.findByAlias(alias)).thenReturn(null);

		String result = service.checkUnique(id, name, alias);

		assertThat(result).isEqualTo("DuplicateName");
	}

	@Test
	public void testCheckUniqueInNewModeReturnDuplicateAlias() {
		Integer id = null;
		String name = "Genie Civil";
		String alias = "GC";

		Filieres filiere = new Filieres(id, name, alias);

		Mockito.when(repo.findByName(name)).thenReturn(null);
		Mockito.when(repo.findByAlias(alias)).thenReturn(filiere);

		String result = service.checkUnique(id, name, alias);

		assertThat(result).isEqualTo("DuplicateAlias");
	}

	@Test
	public void testCheckUniqueInNewModeReturnOK() {
		Integer id = null;
		String name = "Genie Civil";
		String alias = "GC";

		Mockito.when(repo.findByName(name)).thenReturn(null);
		Mockito.when(repo.findByAlias(alias)).thenReturn(null);

		String result = service.checkUnique(id, name, alias);

		assertThat(result).isEqualTo("OK");
	}

	@Test
	public void testCheckUniqueInEditModeReturnDuplicateName() {
		Integer id = 1;
		String name = "Genie Civil";
		String alias = "GC";

		Filieres filiere = new Filieres(2, name, alias);

		Mockito.when(repo.findByName(name)).thenReturn(filiere);
		Mockito.when(repo.findByAlias(alias)).thenReturn(null);

		String result = service.checkUnique(id, name, alias);

		assertThat(result).isEqualTo("DuplicateName");
	}

	@Test
	public void testCheckUniqueInEditModeReturnDuplicateAlias() {
		Integer id = 1;
		String name = "Genie Civil";
		String alias = "GC";

		Filieres filiere = new Filieres(2, name, alias);

		Mockito.when(repo.findByName(name)).thenReturn(null);
		Mockito.when(repo.findByAlias(alias)).thenReturn(filiere);

		String result = service.checkUnique(id, name, alias);

		assertThat(result).isEqualTo("DuplicateAlias");
	}

	@Test
	public void testCheckUniqueInEditModeReturnOK() {
		Integer id = 1;
		String name = "Genie Civil";
		String alias = "GC";

		Filieres filiere = new Filieres(id, name, alias);

		Mockito.when(repo.findByName(name)).thenReturn(null);
		Mockito.when(repo.findByAlias(alias)).thenReturn(filiere);

		String result = service.checkUnique(id, name, alias);

		assertThat(result).isEqualTo("OK");
	}
}
