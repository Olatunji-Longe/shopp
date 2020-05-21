package com.shopp.services;

import com.shopp.common.Localizer;
import com.shopp.domain.Book;
import com.shopp.repositories.BookRepository;
import com.shopp.utils.CacheKeyGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private static final String CACHE_NAME_BOOK_ITEM="book";
    private static final String CACHE_NAME_BOOK_LIST="book-list";

    @Autowired
    private Localizer localizer;

    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public static String getCacheKey(Long bookId){
        return CacheKeyGen.key(String.format("cached-book-%s", bookId));
    }

    public static String getCacheKeyForList(){
        return CacheKeyGen.key("cached-books");
    }

    @Override
    public Long countAllBooks() {
        return bookRepository.count();
    }

    @Override
    @Cacheable(cacheNames = CACHE_NAME_BOOK_LIST, key = "T(com.shopp.services.BookServiceImpl).getCacheKeyForList()")
    public List<Book> getAllBooks(){

        logger.info("=== getAllBooks ===");

        return bookRepository.findAll();
    }

    @Override
    @Cacheable(cacheNames = CACHE_NAME_BOOK_ITEM, key = "T(com.shopp.services.BookServiceImpl).getCacheKey(#bookId)")
    public Book getBook(Long bookId) throws EntityNotFoundException {

        logger.info("=== getBook === bookId: {}", bookId);

        Optional<Book> bookOption = bookRepository.findById(bookId);
        if(bookOption.isPresent()){
            return bookOption.get();
        }
        String message = localizer.translate("data.not-found.book", bookId);
        throw new EntityNotFoundException(message);
    }

}
