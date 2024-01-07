package com.enspd.books.admin.filieres;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.enspd.books.common.entity.Filieres;
import com.enspd.books.common.exception.FiliereNotFoundException;

@Service
@Transactional
public class FilieresService {

	public static final int FILIERES_PER_PAGE = 4;

	@Autowired
	private FilieresRepository repo;

	public List<Filieres> listAll() {
		return (List<Filieres>) repo.findAll(Sort.by("name").ascending());
	}

	public Page<Filieres> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);

		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

		Pageable pageable = PageRequest.of(pageNum - 1, FILIERES_PER_PAGE, sort);

		if (keyword != null) {
			return repo.findAll(keyword, pageable);
		}

		return repo.findAll(pageable);
	}

	public Filieres save(Filieres filiere) {
		return repo.save(filiere);
	}

	public Filieres get(Integer id) throws FiliereNotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new FiliereNotFoundException("Aucune filiere avec ID n'a été trouvée " + id);
		}
	}

	public String checkUnique(Integer id, String name, String alias) {
		boolean isCreatingNew = (id == null || id == 0);

		Filieres filiereByName = repo.findByName(name);

		if (isCreatingNew) {
			if (filiereByName != null) {
				return "DuplicateName";
			} else {
				Filieres filiereByAlias = repo.findByAlias(alias);
				if (filiereByAlias != null) {
					return "DuplicateAlias";
				}
			}
		} else {
			if (filiereByName != null && filiereByName.getId() != id) {
				return "DuplicateName";
			}

			Filieres filiereByAlias = repo.findByAlias(alias);
			if (filiereByAlias != null && filiereByAlias.getId() != id) {
				return "DuplicateAlias";
			}

		}

		return "OK";
	}

	public void updateFiliereEnabledStatus(Integer id, boolean enabled) {
		repo.updateEnabledStatus(id, enabled);
	}

	public void delete(Integer id) throws FiliereNotFoundException {
		Long countById = repo.countById(id);
		if (countById == null || countById == 0) {
			throw new FiliereNotFoundException("Aucune catégorie avec ID n'a été trouvée " + id);
		}

		repo.deleteById(id);
	}
}
