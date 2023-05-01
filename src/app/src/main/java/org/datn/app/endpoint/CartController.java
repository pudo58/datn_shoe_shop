package org.datn.app.endpoint;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.dto.CartRequest;
import org.datn.app.core.entity.Cart;
import org.datn.app.core.entity.extend.CartDTO;
import org.datn.app.core.service.CartService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/getAllPage/{page}/{size}")
    public Page<Cart> getAllCart(int page, int size) {
        return cartService.findAll(page, size);
    }

    @PostMapping("/getCartByUserId")
    public Page<Cart> getCartByUserId(@RequestBody CartDTO model) {
        return cartService.findByUserId(model);
    }

    @PostMapping("/addToCart")
    public Map<String, Object> addToCart(@RequestBody CartRequest model) {
        return cartService.addToCart(model);
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
