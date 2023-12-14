package com.enspd.books.admin.filieres;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enspd.books.common.entity.Filieres;

@Service
public class FilieresService {
	@Autowired
	private FilieresRepository repo;

	public List<Filieres> listAll() {
		return (List<Filieres>) repo.findAll();
	}
}
