package com.enspd.books.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.enspd.books.common.entity.Role;
import com.enspd.books.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	@Autowired
	private UserRepository repo;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userNamHM = new User("nam@codejava.net", "nam2020", "Nam", "Ha Minh");
		userNamHM.addRole(roleAdmin);

		User savedUser = repo.save(userNamHM);

		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateNewUserWithTwoRoles() {
		User userRavi = new User("ravi@gmail.com", "ravi2020", "Ravi", "Kumar");
		Role roleAdmin = new Role(1);
		Role roleCadre = new Role(2);

		userRavi.addRole(roleAdmin);
		userRavi.addRole(roleCadre);

		User savedUser = repo.save(userRavi);

		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}

	@Test
	public void testGetUserById() {
		User userNam = repo.findById(1).get();
		System.out.println(userNam);
		assertThat(userNam).isNotNull();
	}

	@Test
	public void testUpdateUserDetails() {
		User userNam = repo.findById(1).get();
		userNam.setEnabled(true);
		userNam.setEmail("namjavaprogrammer@gmail.com");

		repo.save(userNam);
	}

	@Test
	public void testUpdateUserRoles() {
		User userRavi = repo.findById(2).get();
		Role roleAdmin = new Role(1);

		userRavi.getRoles().remove(roleAdmin);

		repo.save(userRavi);
	}

	@Test
	public void testDeleteUser() {
		Integer userId = 2;
		repo.deleteById(userId);

	}

	@Test
	public void testGetUserByEmail() {
		String email = "ravi@gmail.com";
		User user = repo.getUserByEmail(email);

		assertThat(user).isNotNull();
	}
	
	@Test
	public void testCountById() {
		Integer id = 1;
		Long countById = repo.countById(id);
		
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
}
