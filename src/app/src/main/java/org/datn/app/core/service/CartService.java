package org.datn.app.core.service;

import org.datn.app.core.dto.CartRequest;
import org.datn.app.core.entity.Cart;
import org.datn.app.core.entity.extend.CartDTO;
import org.datn.app.core.service.base.ServiceCore;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService extends ServiceCore<Cart,Long> {
    ResponseEntity<String> addToCart(CartRequest cartRequest);
    Cart findByUserIdAndAndProductDetailId(Long userId, Long productDetailId);
    ResponseEntity<String> deleteMultiCart(List<Long> cardIdList);
    Page<Cart> findByUserId(CartDTO model);
}
