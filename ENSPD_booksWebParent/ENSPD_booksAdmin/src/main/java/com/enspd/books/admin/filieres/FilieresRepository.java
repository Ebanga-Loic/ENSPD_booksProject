package com.enspd.books.admin.filieres;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.enspd.books.common.entity.Filieres;

public interface FilieresRepository extends PagingAndSortingRepository<Filieres, Integer> {

	@Query("SELECT f FROM Filieres f WHERE f.name is NOT NULL")
	public List<Filieres> findFilieres(Sort sort);

	public Filieres findByName(String name);

	public Filieres findByAlias(String alias);

}
