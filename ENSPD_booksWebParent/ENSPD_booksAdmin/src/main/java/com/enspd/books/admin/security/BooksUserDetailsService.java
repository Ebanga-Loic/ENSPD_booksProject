package com.enspd.books.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.enspd.books.admin.user.UserRepository;
import com.enspd.books.common.entity.User;

public class BooksUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.getUserByEmail(email);
		if (user != null) {
			return new BooksUserDetails(user);
		}

		throw new UsernameNotFoundException("Impossible de trouver un utilisateur avec une adresse e-mail : " + email);
	}

}
