package org.datn.app.endpoint;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.dto.CartRequest;
import org.datn.app.core.entity.Cart;
import org.datn.app.core.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor

public class CartAndPaymentController {
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(CartRequest cartRequest) {
        return cartService.addToCart(cartRequest);
    }
    @DeleteMapping("/delete/{id}")
    public Cart deleteCart(Long id) {
        return cartService.doDeleteById(id);
    }
    @PostMapping("/update/{id}")
    public Cart updateCart(Cart cart, Long id) {
        return cartService.doUpdateById(cart, id);
    }
    @PostMapping("/deleteMulti")
    public ResponseEntity<String> deleteMultiCart(List<Long> cardIdList) {
        return cartService.deleteMultiCart(cardIdList);
    }
}
