package com.enspd.books.admin.books;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.enspd.books.common.entity.Book;

public interface BookRepository extends PagingAndSortingRepository<Book, Integer> {

}
