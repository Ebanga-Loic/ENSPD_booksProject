package com.enspd.books.admin.types;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enspd.books.common.entity.Types;

@Service
public class TypesService {
	@Autowired
	private TypesRepository repo;

	public List<Types> listAll() {
		return (List<Types>) repo.findAll();
	}
}
