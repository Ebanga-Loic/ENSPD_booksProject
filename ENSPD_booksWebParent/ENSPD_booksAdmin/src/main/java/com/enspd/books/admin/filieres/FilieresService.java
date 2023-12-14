package com.enspd.books.admin.filieres;

import java.util.List;
import java.util.NoSuchElementException;

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
}
