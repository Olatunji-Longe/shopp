package com.shopp.services;

import com.shopp.domain.Book;
import com.shopp.utils.CacheKeyGen;

import java.util.List;

public interface BookService {

    Long countAllBooks();
    List<Book> getAllBooks();
    Book getBook(Long bookId);

    static String getCacheKey(Long bookId){
        return CacheKeyGen.key(String.format("cached-book-%s", bookId));
    }

    static String getCacheKeyForList(){
        return CacheKeyGen.key("cached-books");
    }

}
