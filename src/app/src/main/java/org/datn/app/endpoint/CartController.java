package org.datn.app.endpoint;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.entity.Cart;
import org.datn.app.core.entity.extend.CartDTO;
import org.datn.app.core.service.CartService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    public Page<Cart> getAllCart(int page, int size) {
        return cartService.findAll(page, size);
    }

    @PostMapping("/getCartByUserId")
    public Page<Cart> getCartByUserId(@RequestBody CartDTO model) {
        return cartService.findByUserId(model);
    }


}
