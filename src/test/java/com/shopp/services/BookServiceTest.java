package com.shopp.services;

import com.shopp.domain.Book;
import com.shopp.repositories.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

public class BookServiceTest extends ServiceUnitTest {

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Test
    void testGetAllBooks(){

        // given
        given(bookRepository.findAll()).willReturn(Arrays.asList(
                Book.builder().id(1L).title("t1").author("a1").language("l1").year(2001).price(BigDecimal.valueOf(100)).build(),
                Book.builder().id(1L).title("t2").author("a2").language("l2").year(2002).price(BigDecimal.valueOf(200)).build(),
                Book.builder().id(1L).title("t3").author("a3").language("l3").year(2003).price(BigDecimal.valueOf(300)).build(),
                Book.builder().id(1L).title("t4").author("a4").language("l4").year(2004).price(BigDecimal.valueOf(400)).build()
        ));

        // when
        List<Book> books = bookService.getAllBooks();


        // then
        then(bookRepository)
                .should()
                .findAll();

        //assertions
        Assertions.assertFalse(books.isEmpty());
        books.forEach(bookInstance -> Assertions.assertTrue(
                bookInstance.getId() != null &&
                        bookInstance.getTitle() != null &&
                        bookInstance.getAuthor() != null &&
                        bookInstance.getLanguage() != null &&
                        bookInstance.getYear() != null &&
                        bookInstance.getPrice() != null
        ));
    }

    @Test
    void testGetBookById(){

        // given
        Long bookId = 1L;
        given(bookRepository.findById(bookId)).willReturn(
                Optional.of(Book.builder().id(bookId).title("t1").author("a1").language("l1").year(2001).price(BigDecimal.valueOf(100)).build())
        );

        // when
        Book book = bookService.getBook(bookId);

        // then
        Assertions.assertNotNull(book);
        Assertions.assertEquals(bookId, book.getId());
    }

    @Test
    void testCountAllBooks(){

        // given
        given(bookRepository.count()).willReturn(4L);

        // when
        Long count = bookService.countAllBooks();

        // then
        Assertions.assertEquals(4, count);
    }

}
