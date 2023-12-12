package com.enspd.books.admin.user;

import org.springframework.data.repository.CrudRepository;

import com.enspd.books.common.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}
