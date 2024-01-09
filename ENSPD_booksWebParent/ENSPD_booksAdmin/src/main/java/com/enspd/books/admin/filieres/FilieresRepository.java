package com.enspd.books.admin.filieres;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.enspd.books.common.entity.Filieres;
import com.enspd.books.common.entity.User;

public interface FilieresRepository extends PagingAndSortingRepository<Filieres, Integer> {

	@Query("SELECT f FROM Filieres f WHERE f.name is NOT NULL")
	public List<Filieres> findFilieres(Sort sort);

	@Query("SELECT f FROM Filieres f WHERE f.name is NOT NULL")
	public Page<Filieres> findFilieres(Pageable pageable);

	public Long countById(Integer id);

	public Filieres findByName(String name);

	public Filieres findByAlias(String alias);

	@Query("UPDATE Filieres f SET f.enabled = ?2 WHERE f.id = ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);

	@Query("SELECT f FROM Filieres f WHERE CONCAT(f.id, ' ', f.name, ' '," + " f.alias) LIKE %?1%")
	public Page<Filieres> findAll(String keyword, Pageable pageable);
	
	public List<Filieres> findAllByOrderByNameAsc();

}
