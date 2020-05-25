package com.shopp.services;

import com.shopp.common.Translator;
import com.shopp.domain.Book;
import com.shopp.repositories.BookRepository;
import com.shopp.utils.CacheKeyGen;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class BookServiceImpl implements BookService {

    private static final String CACHE_NAME_BOOK_ITEM="book";
    private static final String CACHE_NAME_BOOK_LIST="book-list";

    @Autowired
    private Translator translator;

    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Long countAllBooks() {
        return bookRepository.count();
    }

    @Override
    @Cacheable(cacheNames = CACHE_NAME_BOOK_LIST, key = "T(com.shopp.services.BookService).getCacheKeyForList()")
    public List<Book> getAllBooks(){

        log.info("=== getAllBooks ===");

        return bookRepository.findAll();
    }

    @Override
    @Cacheable(cacheNames = CACHE_NAME_BOOK_ITEM, key = "T(com.shopp.services.BookService).getCacheKey(#bookId)")
    public Book getBook(Long bookId) throws EntityNotFoundException {

        log.info("=== getBook === bookId: {}", bookId);

        Optional<Book> bookOption = bookRepository.findById(bookId);
        if(bookOption.isPresent()){
            return bookOption.get();
        }
        String message = translator.translate("data.not-found.book", bookId);
        throw new EntityNotFoundException(message);
    }

}
