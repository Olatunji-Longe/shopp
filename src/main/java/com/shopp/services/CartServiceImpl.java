package com.shopp.services;

import com.shopp.common.CacheHandler;
import com.shopp.common.Localizer;
import com.shopp.domain.Book;
import com.shopp.domain.Cart;
import com.shopp.domain.CartItem;
import com.shopp.domain.CheckoutState;
import com.shopp.exceptions.CheckoutStateException;
import com.shopp.exceptions.InvalidRequestException;
import com.shopp.repositories.BookRepository;
import com.shopp.repositories.CartItemRepository;
import com.shopp.repositories.CartRepository;
import com.shopp.requests.CartItemRequest;
import com.shopp.requests.CartRequest;
import com.shopp.utils.CacheKeyGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    private static final String CACHE_NAME_CART_ITEM ="shopping-cart-item";
    private static final String CACHE_NAME_CART_ITEM_LIST ="shopping-cart-item-list";

    @Autowired
    private Localizer localizer;

    private CartItemRepository cartItemRepository;
    private BookRepository bookRepository;
    private CartRepository cartRepository;
    private CacheHandler cacheHandler;

    @Autowired
    public CartServiceImpl(CartItemRepository cartItemRepository, BookRepository bookRepository,
                           CartRepository cartRepository, CacheHandler cacheHandler) {
        this.cartItemRepository = cartItemRepository;
        this.bookRepository = bookRepository;
        this.cartRepository = cartRepository;
        this.cacheHandler = cacheHandler;
    }

    public static String getCacheKey(Long cartId){
        return CacheKeyGen.key(String.format("cached-cart-%s", cartId));
    }

    public static String getCacheKey(Long cartId, Long bookId){
        return CacheKeyGen.key(String.format("cached-cart-%s-book-%s", cartId, bookId));
    }

    @Override
    public Long countCartItems(Long cartId) {
        return cartItemRepository.countAllByCartIdAndCheckoutStateAndActive(cartId, CheckoutState.QUEUED, true);
    }

    @Cacheable(cacheNames = CACHE_NAME_CART_ITEM_LIST, key = "T(com.shopp.services.CartServiceImpl).getCacheKey(#cartId)")
    public List<CartItem> getCartItems(Long cartId) throws EntityNotFoundException {

        logger.info("=== getCart === cartId: {}", cartId);

        Optional<Cart> cartOption = cartRepository.findById(cartId);
        if(cartOption.isPresent()){
            return cartItemRepository.findAllByCartIdAndCheckoutStateAndActive(cartId, CheckoutState.QUEUED, true);
        }
        String message = localizer.translate("data.not-found.cart", cartId);
        throw new EntityNotFoundException(message);
    }

    @Cacheable(cacheNames = CACHE_NAME_CART_ITEM, key = "T(com.shopp.services.CartServiceImpl).getCacheKey(#cartId, #bookId)")
    public CartItem getCartItem(Long cartId, Long bookId) throws EntityNotFoundException {

        logger.info("=== getCartItem === cartId: {} | bookId: {}", cartId, bookId);

        CartItem cartItem = cartItemRepository.findByCartIdAndBookIdAndCheckoutStateAndActive(cartId, bookId, CheckoutState.QUEUED, true);
        if(cartItem == null){
            String message = localizer.translate("data.not-found.cart-item", cartId, bookId);
            throw new EntityNotFoundException(message);
        }
        return cartItem;
    }

    @Caching(
        put = {@CachePut(cacheNames = CACHE_NAME_CART_ITEM, key = "T(com.shopp.services.CartServiceImpl).getCacheKey(#cartItemRequest.cartId, #cartItemRequest.bookId)")},
        evict = {@CacheEvict(cacheNames = CACHE_NAME_CART_ITEM_LIST, key = "T(com.shopp.services.CartServiceImpl).getCacheKey(#cartItemRequest.cartId)")}
    )
    public CartItem addBookToCart(CartItemRequest cartItemRequest)throws InvalidRequestException, EntityNotFoundException{

        logger.info("=== addBookToCart === {}", cartItemRequest.toString());

        String message;

        if(!cartItemRequest.isValid()){
            message = cartItemRequest.getMessages().toString();
            throw new InvalidRequestException(message);
        }

        Optional<Book> bookOption = bookRepository.findById(cartItemRequest.getBookId());
        if(!bookOption.isPresent()){
            message = localizer.translate("data.not-found.book", cartItemRequest.getBookId());
            throw new EntityNotFoundException(message);
        }

        Optional<Cart> cartOption = cartRepository.findById(cartItemRequest.getCartId());
        if(!cartOption.isPresent()){
            message = localizer.translate("data.not-found.cart", cartItemRequest.getCartId());
            throw new EntityNotFoundException(message);
        }

        CartItem cartItem = cartItemRepository.findByCartIdAndBookIdAndCheckoutStateAndActive(cartItemRequest.getCartId(), cartItemRequest.getBookId(), CheckoutState.QUEUED, true);
        if(cartItem == null){
            int quantity = cartItemRequest.getQuantity();
            if(cartItemRequest.getQuantity() > 0){
                cartItem = new CartItem();
                cartItem.setBook(bookOption.get());
                cartItem.setQuantity(quantity);
                cartItem.setCart(cartOption.get());
                cartItem.setCheckoutState(CheckoutState.QUEUED);
                return cartItemRepository.save(cartItem);
            }else{
                message = localizer.translate("cart-request.invalid.quantity", quantity);
                throw new InvalidRequestException(message);
            }
        }else{
            int quantity = cartItem.getQuantity() + cartItemRequest.getQuantity();
            if(quantity > 0){
                cartItem.setQuantity(quantity);
                return cartItemRepository.save(cartItem);
            }else{
                message = localizer.translate("cart-request.invalid.quantity.derived", cartItem.getQuantity(), cartItemRequest.getQuantity());
                throw new InvalidRequestException(message);
            }
        }
    }

    @Caching(
        evict = {
            @CacheEvict(cacheNames = CACHE_NAME_CART_ITEM, key = "T(com.shopp.services.CartServiceImpl).getCacheKey(#cartItemRequest.cartId, #cartItemRequest.bookId)"),
            @CacheEvict(cacheNames = CACHE_NAME_CART_ITEM_LIST, key = "T(com.shopp.services.CartServiceImpl).getCacheKey(#cartItemRequest.cartId)")
        }
    )
    public CartItem deleteBookFromCart(CartItemRequest cartItemRequest) throws InvalidRequestException, EntityNotFoundException {

        logger.info("=== deleteBookFromCart === {}", cartItemRequest.toString());

        String message;
        if(!cartItemRequest.isValid()){
            message = cartItemRequest.getMessages().toString();
            throw new InvalidRequestException(message);
        }

        CartItem cartItem = cartItemRepository.findByCartIdAndBookIdAndCheckoutStateAndActive(cartItemRequest.getCartId(), cartItemRequest.getBookId(), CheckoutState.QUEUED, true);
        if(cartItem == null){
            message = localizer.translate("data.not-found.cart-items.on-payload", cartItemRequest.toString());
            throw new EntityNotFoundException(message);
        }
        cartItemRepository.delete(cartItem);

        //mutate object for presentation even though it no longer exists
        cartItem.setCheckoutState(CheckoutState.DELETED);
        cartItem.setActive(false);

        return cartItem;
    }

    @CacheEvict(cacheNames = CACHE_NAME_CART_ITEM_LIST, key = "T(com.shopp.services.CartServiceImpl).getCacheKey(#cartRequest.cartId)")
    public List<CartItem> checkoutBooksFromCart(CartRequest cartRequest) throws InvalidRequestException, EntityNotFoundException {

        logger.info("=== checkoutBooksFromCart === {}", cartRequest.toString());

        return updateCheckoutStates(cartRequest, CartRequest.Action.checkout);
    }

    @CacheEvict(cacheNames = CACHE_NAME_CART_ITEM_LIST, key = "T(com.shopp.services.CartServiceImpl).getCacheKey(#cartRequest.cartId)")
    public List<CartItem> purgeBooksFromCart(CartRequest cartRequest) throws InvalidRequestException, EntityNotFoundException {

        logger.info("=== purgeBooksFromCart === {}", cartRequest.toString());

        return updateCheckoutStates(cartRequest, CartRequest.Action.reset);
    }

    @Override
    public BigDecimal getCartItemsSubTotal(Long cartId) {
        Float sum = cartItemRepository.findSubTotalByCartIdAndCheckoutStateAndActive(cartId, CheckoutState.QUEUED, true);
        return new BigDecimal(Float.toString(sum)).setScale(2, RoundingMode.HALF_DOWN);
    }

    private List<CartItem> updateCheckoutStates(CartRequest cartRequest, CartRequest.Action cartRequestAction) throws InvalidRequestException, EntityNotFoundException, CheckoutStateException {

        logger.info("=== updateCheckoutStates === action: {}, payload: {}", cartRequestAction, cartRequest.toString());

        String message;
        if(!cartRequest.isValid()){
            message = cartRequest.getMessages().toString();
            throw new InvalidRequestException(message);
        }

        Optional<Cart> cartOption = cartRepository.findById(cartRequest.getCartId());
        if(!cartOption.isPresent()) {
            message = localizer.translate("data.not-found.cart", cartRequest.getCartId());
            throw new EntityNotFoundException(message);
        }

        Set<String> cacheKeys = new HashSet<>();

        List<CartItem> cartItems = cartItemRepository.findAllByCartIdAndCheckoutStateAndActive(cartRequest.getCartId(), CheckoutState.QUEUED, true);
        for(CartItem cartItem : cartItems){

            if(!cartItem.getCheckoutState().equals(CheckoutState.QUEUED)){
                message = localizer.translate("cart-item.invalid.checkout-state", cartRequestAction, cartItem.getId(), cartItem.getCheckoutState(), CheckoutState.QUEUED);
                throw new CheckoutStateException(message);
            }

            if(cartRequestAction.equals(CartRequest.Action.checkout)){
                cartItem.setCheckoutState(CheckoutState.CHECKED_OUT);
            }

            if(cartRequestAction.equals(CartRequest.Action.reset)){
                cartItem.setCheckoutState(CheckoutState.ABANDONED);
            }

            // Grab all cache keys for each cartItem
            cacheKeys.add(getCacheKey(cartItem.getCart().getId(), cartItem.getBook().getId()));
        }

        if(!cartItems.isEmpty()){
            cartItems = cartItemRepository.saveAll(cartItems);
        }

        // Manually evict single cartItem cache
        if(!cacheKeys.isEmpty()){
            cacheHandler.evictCache(CACHE_NAME_CART_ITEM, cacheKeys);
        }

        return cartItems;
    }

}
