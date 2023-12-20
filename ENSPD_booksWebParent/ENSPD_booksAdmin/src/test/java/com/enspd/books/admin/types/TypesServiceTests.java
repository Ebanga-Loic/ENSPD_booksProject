package com.enspd.books.admin.types;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.enspd.books.common.entity.Types;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class TypesServiceTests {

	@MockBean
	private TypesRepository repo;

	@InjectMocks
	private TypesService service;

	@Test
	public void testCheckUniqueInNewModeReturnDuplicate() {
		Integer id = null;
		String name = "Oeuvre";
		Types type = new Types(name);

		Mockito.when(repo.findByName(name)).thenReturn(type);

		String result = service.checkUnique(id, name);
		assertThat(result).isEqualTo("Duplicate");
	}

	@Test
	public void testCheckUniqueInNewModeReturnOK() {
		Integer id = null;
		String name = "Oeuvre";

		Mockito.when(repo.findByName(name)).thenReturn(null);

		String result = service.checkUnique(id, name);
		assertThat(result).isEqualTo("OK");
	}

	@Test
	public void testCheckUniqueInEditModeReturnDuplicate() {
		Integer id = 1;
		String name = "Oeuvre";
		Types type = new Types(id, name);

		Mockito.when(repo.findByName(name)).thenReturn(type);

		String result = service.checkUnique(2, "Oeuvre");
		assertThat(result).isEqualTo("Duplicate");
	}

	@Test
	public void testCheckUniqueInEditModeReturnOK() {
		Integer id = 1;
		String name = "Oeuvre";
		Types type = new Types(id, name);

		Mockito.when(repo.findByName(name)).thenReturn(type);

		String result = service.checkUnique(id, "Oeuvre");
		assertThat(result).isEqualTo("OK");
	}
}
