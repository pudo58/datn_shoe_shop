package org.datn.app.core.service;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.dto.CartRequest;
import org.datn.app.core.entity.Cart;
import org.datn.app.core.entity.Order;
import org.datn.app.core.entity.ProductDetail;
import org.datn.app.core.entity.User;
import org.datn.app.core.entity.extend.CartDTO;
import org.datn.app.core.repo.CartRepo;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional(rollbackOn = RuntimeException.class)
@RequiredArgsConstructor

public class CartServiceImpl implements CartService {
    private final CartRepo cartRepo;
    private final UserService userService;
    private final ProductDetailService productDetailService;
    private final ProductService productService;
    private final OrderService orderService;
    private final OrderDetailService orderDetailService;

    @Override
    public Cart findByUserIdAndAndProductDetailId(Long userId, Long productDetailId) {
        User user = userService.findById(userId);
        for(Cart cart : user.getCarts()) {
            if(cart.getProductDetail().getId().equals(productDetailId))
                return cart;
        }
        return null;
    }

    @Override
    public synchronized ResponseEntity<String> deleteMultiCart(List<Long> cardIdList) {
        List<Cart> cartList = cartRepo.findAllById(cardIdList);
        if (cartList.isEmpty())
            return ResponseEntity.badRequest().body("Không tìm thấy sản phẩm trong giỏ hàng");
        synchronized (cartList) {
            cartRepo.deleteAll(cartList);
        }
        return ResponseEntity.ok("Xóa sản phẩm đã chọn thành công");
    }

    @Override
    public Page<Cart> findByUserId(CartDTO model) {
        Pageable pageable = Pageable.ofSize(model.getSize().get()).withPage(model.getPage().get());
        return cartRepo.findByUserId(model.getUserId(), pageable);
    }

    @Override
    public Cart doInsert(Cart cart) {
        return null;
    }

    @Override
    public Cart doUpdateById(Cart cart, Long aLong) {
        Cart cart1 = cartRepo.findById(aLong).get();
        cart1.setQuantity(cart.getQuantity());
        return cartRepo.save(cart1);
    }

    @Override
    public Cart doDeleteById(Long aLong) {
        Cart cart = cartRepo.findById(aLong).orElse(null);
        cartRepo.delete(cart);
        return null;
    }

    @Override
    public Cart findById(Long aLong) {
        return cartRepo.findById(aLong).orElse(null);
    }

    @Override
    public List<Cart> findAll() {
        return cartRepo.findAll();
    }

    @Override
    public Page<Cart> findAll(Integer page, Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return cartRepo.findAll(pageable);
    }

    /*
     *   Thêm sản phẩm vào giỏ hàng
     *   @param productDetailId
     *   @param quantity
     *   @param userId
     *   @return ResponseEntity<String>
     */
    @Override
    public Map<String,Object> addToCart(CartRequest cartRequest) {
        Long productDetailId = cartRequest.getProductDetailId();
        Long userId = cartRequest.getUserId();
        Integer quantity = cartRequest.getQuantity();
        ProductDetail productDetail = productDetailService.findById(productDetailId);
        User user = userService.findById(userId);
        Map<String,Object> map = new HashMap<>();
        if (productDetail == null){
            map.put("message","Không tìm thấy sản phẩm");
            map.put("status", HttpStatus.BAD_REQUEST.value());
            return map;
        }
        if (productDetail.getQuantity() < quantity){
            map.put("message","Số lượng sản phẩm không đủ");
            map.put("status", HttpStatus.BAD_REQUEST.value());
            return map;
        }
        if (productDetail.getQuantity() == 0) {
            map.put("message","Sản phẩm đã hết hàng");
            map.put("status", HttpStatus.BAD_REQUEST.value());
            return map;
        }
        if (user == null) {
            map.put("message","Không tìm thấy người dùng");
            map.put("status", HttpStatus.BAD_REQUEST.value());
            return map;
        }
        if (quantity.toString().isEmpty()) {
            map.put("message","Số lượng sản phẩm không được để trống");
            map.put("status", HttpStatus.BAD_REQUEST.value());
            return map;
        }
        if (!quantity.toString().matches("[0-9]+")) {
            map.put("message","Số lượng sản phẩm phải là số");
            map.put("status", HttpStatus.BAD_REQUEST.value());
            return map;
        }
        User user1 = userService.findById(userId);
        Cart cart = new Cart();
        cart.setUser(user1);
        cart.setProductDetail(productDetail);
        cart = cartRepo.findOne(Example.of(cart)).orElse(null);
        if (cart == null) {
            Cart cart1 = new Cart();
            cart1.setQuantity(quantity);
            cart1.setProductDetail(productDetail);
            cart1.setPrice(productDetail.getProduct().getPrice() * quantity);
            cart1.setUser(user1);
            cartRepo.save(cart1);
            map.put("message","Thêm sản phẩm "
                    + cart1.getProductDetail().getProduct().getName()
                    + " vào giỏ hàng thành công");
            map.put("status", HttpStatus.OK.value());
            return map;
        }else{
            synchronized (cart) {
                if (cart != null) {
                    if(cart.getQuantity() + quantity > productDetail.getQuantity()) {
                        map.put("message", "Số lượng sản phẩm trong giỏ hàng vượt quá số lượng sản phẩm trong kho");
                        map.put("status", HttpStatus.BAD_REQUEST.value());
                        return map;
                    }
                    cart.setQuantity(cart.getQuantity() + quantity);
                    cartRepo.save(cart);
                    map.put("message", "Thêm sản phẩm "
                            + cart.getProductDetail().getProduct().getName()
                            + " vào giỏ hàng thành công");
                    map.put("status", HttpStatus.OK.value());
                    return map;
                }
            }
        }
        return map;
    }
}
