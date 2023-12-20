package com.enspd.books.admin.types;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.enspd.books.common.entity.Types;

@Service
public class TypesService {

	public static final int TYPES_PER_PAGE = 10;

	@Autowired
	private TypesRepository repo;

	public List<Types> listAll() {
		return (List<Types>) repo.findAll();
	}

	public Page<Types> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);

		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

		Pageable pageable = PageRequest.of(pageNum - 1, TYPES_PER_PAGE, sort);

		if (keyword != null) {
			return repo.findAll(keyword, pageable);
		}

		return repo.findAll(pageable);
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

	public String checkUnique(Integer id, String name) {
		boolean isCreatingNew = (id == null || id == 0);
		Types typeByName = repo.findByName(name);

		if (isCreatingNew) {
			if (typeByName != null)
				return "Duplicate";
		} else {
			if (typeByName != null && typeByName.getId() != id) {
				return "Duplicate";
			}
		}

		return "OK";
	}
}
