package com.shopp.controllers;

import com.shopp.domain.dto.BookDTO;
import com.shopp.domain.dto.EntityDTO;
import com.shopp.responses.ResponseBuilder;
import com.shopp.responses.RestResponse;
import com.shopp.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/books")
public class BookController {

    private BookService bookService;

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<RestResponse> getBooks() {
        ResponseBuilder responseBuilder = new ResponseBuilder();
        try{
            return ResponseEntity.ok(
                    responseBuilder
                            .data("books", BookDTO.list(bookService.getAllBooks()))
                            .build()
            );
        }catch(Exception ex){
            logger.error("error while attempting to get books", ex);
            return responseBuilder.httpError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());

        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse> getBook(@PathVariable("id") Long bookId) {
        ResponseBuilder responseBuilder = new ResponseBuilder();
        try{
            return ResponseEntity.ok(
                    responseBuilder
                            .data("book", bookService.getBook(bookId))
                            .build()
            );
        }catch(EntityNotFoundException ex){
            logger.warn(ex.getMessage());
            return responseBuilder.httpError(HttpStatus.NOT_FOUND, ex.getMessage());

        }catch(Exception ex){
            logger.error("error while attempting to get books", ex);
            return responseBuilder.httpError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

}
