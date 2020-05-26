package com.shopp.controllers;

import com.shopp.responses.ResponseBuilder;
import com.shopp.responses.RestResponse;
import com.shopp.services.BookService;
import com.shopp.services.CartService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RestController
@RequestMapping("/nav")
public class NavBarController {

    private BookService bookService;
    private CartService cartService;

    @Autowired
    public NavBarController(BookService bookService, CartService cartService){
        this.bookService = bookService;
        this.cartService = cartService;
    }

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<RestResponse> getNavData(@PathVariable("cartId") Long cartId) {
        ResponseBuilder responseBuilder = new ResponseBuilder();
        try{
            return ResponseEntity.ok(
                    responseBuilder
                            .data("cartId", cartId)
                            .data("bookCount", bookService.countAllBooks())
                            .data("cartItemCount", cartService.countCartItems(cartId))
                            .build()
            );
        }catch(EntityNotFoundException ex){
            log.warn(ex.getMessage());
            return responseBuilder.httpError(HttpStatus.NOT_FOUND, ex.getMessage());

        }catch(Exception ex){
            log.error("error while attempting to count cart items for user", ex);
            return responseBuilder.httpError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

}
