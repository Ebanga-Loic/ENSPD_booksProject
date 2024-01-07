package com.enspd.books.filiere;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.enspd.books.common.entity.Filieres;

public interface FiliereRepository extends CrudRepository<Filieres, Integer> {

	@Query("SELECT c FROM Filieres c WHERE c.enabled = true ORDER BY c.name ASC")
	public List<Filieres> findAllEnabled();

	@Query("SELECT c FROM Filieres c WHERE c.enabled = true AND c.alias = ?1")
	public Filieres findByAliasEnabled(String alias);
}
