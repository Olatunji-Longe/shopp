package com.shopp.controllers;

import com.shopp.domain.dto.CartDTO;
import com.shopp.exceptions.CheckoutStateException;
import com.shopp.exceptions.InvalidRequestException;
import com.shopp.requests.CartItemRequest;
import com.shopp.requests.CartRequest;
import com.shopp.responses.ResponseBuilder;
import com.shopp.responses.RestResponse;
import com.shopp.services.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@Slf4j
@Transactional
@RestController
@RequestMapping("/cart")
public class CartController {

    private CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<RestResponse> getCart(@PathVariable("cartId") Long cartId) {
        ResponseBuilder responseBuilder = new ResponseBuilder();
        try{
            return ResponseEntity.ok(
                    responseBuilder
                            .data("cart", CartDTO.of(cartService.getCartItems(cartId)))
                            .build()
            );
        }catch(EntityNotFoundException ex){
            log.warn(ex.getMessage());
            return responseBuilder.httpError(HttpStatus.NOT_FOUND, ex.getMessage());

        }catch(Exception ex){
            log.error("error while attempting to get cart", ex);
            return responseBuilder.httpError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @GetMapping("/{cartId}/book/{bookId}")
    public ResponseEntity<RestResponse> getCartItem(@PathVariable("cartId") Long cartId, @PathVariable("bookId") Long bookId) {
        ResponseBuilder responseBuilder = new ResponseBuilder();
        try{
            return ResponseEntity.ok(
                    responseBuilder
                            .data("cartItem", cartService.getCartItem(cartId, bookId).toDTO())
                            .build()
            );
        }catch(EntityNotFoundException ex){
            log.warn(ex.getMessage());
            return responseBuilder.httpError(HttpStatus.NOT_FOUND, ex.getMessage());

        }catch(Exception ex){
            log.error("error while attempting to get cart item", ex);
            return responseBuilder.httpError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @PutMapping("/book/quantity/update")
    public ResponseEntity<RestResponse> addToCart(@RequestBody CartItemRequest payload) {
        ResponseBuilder responseBuilder = new ResponseBuilder();
        try{
            return ResponseEntity.ok(
                    responseBuilder
                            .data("cartItem", cartService.addBookToCart(payload).toDTO())
                            .data("cartItemCount", cartService.countCartItems(payload.getCartId()))
                            .data("subTotal", cartService.getCartItemsSubTotal(payload.getCartId()))
                            .build()
            );
        }catch(InvalidRequestException ex){
            log.warn(ex.getMessage());
            return responseBuilder.httpError(HttpStatus.BAD_REQUEST, ex.getMessage());

        }catch(EntityNotFoundException ex){
            log.warn(ex.getMessage());
            return responseBuilder.httpError(HttpStatus.NOT_FOUND, ex.getMessage());

        }catch(Exception ex){
            log.error("error while attempting to update cart item quantity", ex);
            return responseBuilder.httpError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @DeleteMapping("/book")
    public ResponseEntity<RestResponse> deleteFromCart(@RequestBody CartItemRequest payload) {
        ResponseBuilder responseBuilder = new ResponseBuilder();
        try{
            return ResponseEntity.ok(
                    responseBuilder
                            .data("cartItem", cartService.deleteBookFromCart(payload).toDTO())
                            .data("cartItemCount", cartService.countCartItems(payload.getCartId()))
                            .data("subTotal", cartService.getCartItemsSubTotal(payload.getCartId()))
                            .build()
            );
        }catch(InvalidRequestException ex){
            log.warn(ex.getMessage());
            return responseBuilder.httpError(HttpStatus.BAD_REQUEST, ex.getMessage());

        }catch(EntityNotFoundException ex){
            log.warn(ex.getMessage());
            return responseBuilder.httpError(HttpStatus.NOT_FOUND, ex.getMessage());

        }catch(Exception ex){
            log.error("error while attempting to delete book from cart", ex);
            return responseBuilder.httpError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<RestResponse> checkoutCartItems(@RequestBody CartRequest payload) {
        ResponseBuilder responseBuilder = new ResponseBuilder();
        try{
            return ResponseEntity.ok(
                    responseBuilder
                            .data("order", cartService.checkoutBooksFromCart(payload).toDTO())
                            .build()
            );
        }catch(InvalidRequestException | CheckoutStateException ex){
            log.warn(ex.getMessage());
            return responseBuilder.httpError(HttpStatus.BAD_REQUEST, ex.getMessage());

        }catch(EntityNotFoundException ex){
            log.warn(ex.getMessage());
            return responseBuilder.httpError(HttpStatus.NOT_FOUND, ex.getMessage());

        } catch(Exception ex){
            log.error("error while attempting to checkout books from cart", ex);
            return responseBuilder.httpError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @PatchMapping("/reset")
    public ResponseEntity<RestResponse> resetCart(@RequestBody CartRequest payload) {
        ResponseBuilder responseBuilder = new ResponseBuilder();
        try{
            return ResponseEntity.ok(
                    responseBuilder
                            .data("cart", CartDTO.of(cartService.purgeBooksFromCart(payload)))
                            .build()
            );
        }catch(InvalidRequestException | CheckoutStateException ex){
            log.warn(ex.getMessage());
            return responseBuilder.httpError(HttpStatus.BAD_REQUEST, ex.getMessage());

        }catch(EntityNotFoundException ex){
            log.warn(ex.getMessage());
            return responseBuilder.httpError(HttpStatus.NOT_FOUND, ex.getMessage());

        }catch(Exception ex){
            log.error("error while attempting to reset cart", ex);
            return responseBuilder.httpError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

}
