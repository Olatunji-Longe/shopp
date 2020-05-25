package com.shopp.services;

import com.shopp.common.Translator;
import com.shopp.domain.Address;
import com.shopp.domain.Cart;
import com.shopp.domain.CartItem;
import com.shopp.domain.CheckoutState;
import com.shopp.domain.Credential;
import com.shopp.domain.Fee;
import com.shopp.domain.Order;
import com.shopp.exceptions.CheckoutStateException;
import com.shopp.repositories.CartItemRepository;
import com.shopp.repositories.CredentialRepository;
import com.shopp.repositories.FeeRepository;
import com.shopp.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional
public class CheckoutServiceImpl implements CheckoutService {

    private CartItemRepository cartItemRepository;
    private CredentialRepository credentialRepository;
    private FeeRepository feeRepository;
    private OrderRepository orderRepository;

    @Autowired
    private Translator translator;

    @Autowired
    public CheckoutServiceImpl(CartItemRepository cartItemRepository, OrderRepository orderRepository,
                               CredentialRepository credentialRepository, FeeRepository feeRepository) {
        this.cartItemRepository = cartItemRepository;
        this.credentialRepository = credentialRepository;
        this.orderRepository = orderRepository;
        this.feeRepository = feeRepository;
    }

    public Order checkoutOrder(Cart cart) throws EntityNotFoundException, CheckoutStateException {

        log.info("=== checkoutOrder === {}", cart.getId());

        if(cart.getId() == null) {
            throw new EntityNotFoundException(translator.translate("data.not-found.cart", cart.getId()));
        }

        String message;

        Credential credential = credentialRepository.findByUsername(UserServiceImpl.CURRENT_USER_NAME);
        if(credential == null){
            message = translator.translate("data.not-found.user.username", UserServiceImpl.CURRENT_USER_NAME);
            throw new EntityNotFoundException(message);
        }

        List<CartItem> cartItems = cartItemRepository.findAllByCartIdAndCheckoutStateAndActive(cart.getId(), CheckoutState.QUEUED, true);
        if(cartItems.isEmpty()){
            message = translator.translate("cart.empty.cart-items");
            throw new CheckoutStateException(message);
        }

        BigDecimal totalAmount = CartService.calculateTotalAmount(cartItems);

        Address sourceAddress = cart.getStore().getAddresses().get(0);
        Address destinationAddress = credential.getUser().getAddresses().get(0);

        List<Fee> fees = feeRepository.saveAll(FeeService.getRandomFees(totalAmount));
        BigDecimal totalFees = FeeService.calculateTotalFees(fees);

        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

        Order order = new Order();
        order.setCartItems(cartItems);
        order.setTotalAmount(totalAmount);
        order.setFees(fees);
        order.setTotalFees(totalFees);
        order.setNetTotal(totalAmount.add(totalFees));
        order.setSourceAddress(sourceAddress);
        order.setDestinationAddress(destinationAddress);
        order.setReference(generateCheckoutReference());
        order.setCreatedAt(now);
        order.setCreatedBy(credential.getUsername());
        order.setModifiedAt(now);
        order.setModifiedBy(credential.getUsername());
        return orderRepository.save(order);
    }

    private String generateCheckoutReference(){
        return RandomStringUtils.randomAlphanumeric(12).toUpperCase();
    }

}
