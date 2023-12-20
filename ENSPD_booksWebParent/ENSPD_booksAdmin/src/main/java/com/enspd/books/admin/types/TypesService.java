package com.enspd.books.admin.types;

import java.util.List;
import java.util.NoSuchElementException;

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

	public Types save(Types types) {
		return repo.save(types);
	}

	public Types get(Integer id) throws TypesNotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new TypesNotFoundException("Impossible de trouver ce type de livre avec ID " + id);
		}
	}

	public void delete(Integer id) throws TypesNotFoundException {
		Long countById = repo.countById(id);

		if (countById == null || countById == 0) {
			throw new TypesNotFoundException("Impossible de trouver ce type de livre avec ID " + id);
		}

		repo.deleteById(id);
	}
}
