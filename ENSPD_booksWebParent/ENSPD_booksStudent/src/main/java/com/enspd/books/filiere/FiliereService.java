package com.enspd.books.filiere;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enspd.books.common.entity.Filieres;
import com.enspd.books.common.exception.FiliereNotFoundException;

@Service
public class FiliereService {

	@Autowired
	private FiliereRepository repo;

	public List<Filieres> listFilieres() {
		List<Filieres> listFilieres = new ArrayList<>();

		List<Filieres> listEnabledFilieres = repo.findAllEnabled();

		listEnabledFilieres.forEach(filieres -> {
			String filiere = filieres.getAlias();
			if (filiere != null) {
				listFilieres.add(filieres);
			}
		});

		return listFilieres;
	}

	public Filieres getFiliere(String alias) throws FiliereNotFoundException {

		Filieres filiere = repo.findByAliasEnabled(alias);
		if (filiere == null) {
			throw new FiliereNotFoundException("Impossible de trouver une filière avec cet alias" + alias);
		}

		return filiere;
	}
	
}
