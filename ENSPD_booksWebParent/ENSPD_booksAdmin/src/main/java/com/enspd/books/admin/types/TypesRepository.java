package com.enspd.books.admin.types;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.enspd.books.common.entity.Types;

public interface TypesRepository extends PagingAndSortingRepository<Types, Integer> {

	public Long countById(Integer id);
	
	public Types findByName(String name);

}
