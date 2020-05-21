package com.shopp.services;

import com.shopp.domain.Book;

import java.util.List;

public interface BookService {

    Long countAllBooks();
    List<Book> getAllBooks();
    Book getBook(Long bookId);

}
