package com.enspd.books.admin.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.enspd.books.common.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

}
