package com.enspd.books.admin.types;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.enspd.books.common.entity.Types;

public interface TypesRepository extends PagingAndSortingRepository<Types, Integer> {

	public Long countById(Integer id);

	public Types findByName(String name);

	@Query("SELECT t FROM Types t WHERE t.name LIKE %?1%")
	public Page<Types> findAll(String keyword, Pageable pageable);

	@Query("SELECT NEW Types(t.id, t.name) FROM Types t ORDER BY t.name ASC")
	public List<Types> findAll();

}
