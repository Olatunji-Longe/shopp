package com.shopp.controllers;

import com.shopp.responses.ResponseBuilder;
import com.shopp.responses.RestResponse;
import com.shopp.services.BookService;
import com.shopp.services.CartService;
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
@RequestMapping("/nav")
public class NavBarController {

    private BookService bookService;
    private CartService cartService;

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

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
            logger.warn(ex.getMessage());
            return responseBuilder.httpError(HttpStatus.NOT_FOUND, ex.getMessage());

        }catch(Exception ex){
            logger.error("error while attempting to count cart items for user", ex);
            return responseBuilder.httpError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

}
