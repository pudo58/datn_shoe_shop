package org.datn.app.core.service;

import lombok.RequiredArgsConstructor;
import org.datn.app.constant.OrderConstant;
import org.datn.app.constant.PaymentMethodConstant;
import org.datn.app.core.dto.OrderRequest;
import org.datn.app.core.entity.*;
import org.datn.app.core.repo.*;
import org.datn.app.util.GenerateString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Transactional(rollbackOn = RuntimeException.class)
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderDetailRepo orderDetailRepo;
    private final CartRepo cartRepo;
    private final OrderRepo orderRepo;
    private final VoucherRepo voucherRepo;
    private final UserRepo userRepo;
    private final TransactionRepo transactionRepo;
    private final ProductDetailRepo productDetailRepo;

    @Override
    public Order doInsert(Order order) {
        return orderRepo.save(order);
    }

    @Override
    public Order doUpdateById(Order order, Long aLong) {
        Order order1 = orderRepo.findById(aLong).get();
        order1.setAddress(order.getAddress());
        order1.setPhoneNumber(order.getPhoneNumber());
        return orderRepo.save(order1);
    }

    @Override
    public Order doDeleteById(Long aLong) {
        Order order = orderRepo.findById(aLong).get();
        orderRepo.delete(order);
        return null;
    }

    @Override
    public Order findById(Long aLong) {
        return orderRepo.findById(aLong).orElse(null);
    }

    @Override
    public List<Order> findAll() {
        return orderRepo.findAll();
    }

    @Override
    public Page<Order> findAll(Integer page, Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return orderRepo.findAll(pageable);
    }

    @Override
    public Order findByCode(String code) {
        return orderRepo.findByCode(code);
    }

    @Override
    public Order findByCodeAndStatus(String code, String status) {
        return orderRepo.findByCodeAndStatus(code, status);
    }

    @Override
    @Transactional(rollbackOn = RuntimeException.class)
    public ResponseEntity<?> doOrder(OrderRequest model) {
        List<Cart> cartList = cartRepo.findAllById(model.getCartIdList());
        User user = userRepo.findById(model.getUserId()).orElseThrow(
                () -> new RuntimeException("Người dùng không tồn tại")
        );
        AtomicReference<Double> total = new AtomicReference<>((double) 0);
        Order order = new Order();
        order.setAddress(model.getAddress());
        order.setPhoneNumber(model.getPhoneNumber());
        if (model.getPaymentMethod().equals(PaymentMethodConstant.BANK_TRANSFER)) {
            order.setPaymentMethod(PaymentMethodConstant.BANK_TRANSFER);
            order.setStatus(OrderConstant.PENDING_BANK_TRANSFER);
        } else if (model.getPaymentMethod().equals(PaymentMethodConstant.COD)) {
            order.setPaymentMethod(PaymentMethodConstant.COD);
            order.setStatus(OrderConstant.PENDING);
        }
        order.setNote(OrderConstant.ORDER_NEW);
        order.setUser(user);
        // TODO: Đặt hàng và thêm data vào bảng order_detail
        try {
            orderRepo.save(order);
            cartList.forEach(cart -> {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(order);
                Integer quantity = cart.getQuantity();
                if (quantity == null || quantity == 0) {
                    throw new RuntimeException("Số lượng sản phẩm không được để trống");
                }
                if (quantity > cart.getProductDetail().getQuantity()) {
                    throw new RuntimeException("Số lượng sản phẩm không đủ");
                }
                orderDetail.setProductDetail(cart.getProductDetail());
                orderDetail.setQuantity(quantity);
                orderDetail.setPrice(cart.getProductDetail().getProduct().getPrice());
                if (orderDetailRepo.save(orderDetail) != null) {
                    // TODO : Xóa cart sau khi đặt hàng thành công
                    total.updateAndGet(v -> v + orderDetail.getPrice() * orderDetail.getQuantity());
                    cartRepo.delete(cart);
                }
            });

            // TODO: Thêm data vào bảng Transaction

            try {
                Transaction transaction = new Transaction();
                transaction.setOrder(order);
                transaction.setAddress(model.getAddress());
                transaction.setPhoneNumber(model.getPhoneNumber());
                transaction.setCode(GenerateString.generateString(10));

                // TODO : Thêm phí ship

                if (total.get() > OrderConstant.DELIVERY_FEE_FREE_MIN) {
                    transaction.setDeliveryFee(OrderConstant.DELIVERY_FEE_FREE);
                } else {
                    transaction.setDeliveryFee(OrderConstant.DELIVERY_FEE);
                }
                transaction.setTotal(total.get() + transaction.getDeliveryFee());
                transaction.setVoucher(voucherRepo.findById(model.getVoucherId()).orElse(null));
                transactionRepo.save(transaction);
            } catch (Exception e) {
                throw new RuntimeException("Có lỗi xảy ra khi đặt hàng");
            }
            return ResponseEntity.ok("Đặt hàng thành công");
        } catch (Exception e) {
            throw new RuntimeException("Có lỗi xảy ra khi đặt hàng");
        }
    }

    @Override
    public ResponseEntity<?> approveOrder(Long id) {
        Order order = orderRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Đơn hàng không tồn tại")
        );
        // TODO: Duyệt đơn hàng
        synchronized (order) {
            if (order.getStatus().equals(OrderConstant.PENDING)
                    || order.getStatus().equals(OrderConstant.PENDING_BANK_TRANSFER)) {
                order.setStatus(OrderConstant.CONFIRMED);
                order.setCreated(new Date());
                orderRepo.save(order);
                List<OrderDetail> orderDetailList = order.getOrderDetails();
                // TODO: Trừ số lượng sản phẩm trong kho
                orderDetailList.forEach(orderDetail -> {
                    ProductDetail productDetail = orderDetail.getProductDetail();
                    productDetail.setQuantity(productDetail.getQuantity() - orderDetail.getQuantity());
                    productDetailRepo.save(productDetail);
                });
                return ResponseEntity.ok("Duyệt đơn hàng thành công");
            }
        }
        return ResponseEntity.ok("Đơn hàng đã được duyệt");
    }

    @Override
    public ResponseEntity<?> cancelOrder(Long id) {
        Order order = orderRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Đơn hàng không tồn tại")
        );
        synchronized (order) {
            if (order.getStatus().equals(OrderConstant.PENDING)
                    || order.getStatus().equals(OrderConstant.PENDING_BANK_TRANSFER)) {
                order.setStatus(OrderConstant.CANCELLED);
                orderRepo.save(order);
                return ResponseEntity.ok("Hủy đơn hàng thành công");
            }
        }
        return ResponseEntity.ok("Đơn hàng đã được hủy");
    }

    @Override
    public ResponseEntity<?> returnOrder(Long id) {
        Order order = orderRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Đơn hàng không tồn tại")
        );
        synchronized (order) {
            if (order.getStatus().equals(OrderConstant.CONFIRMED)) {
                order.setStatus(OrderConstant.EXCHANGED);
                orderRepo.save(order);
                List<OrderDetail> orderDetailList = order.getOrderDetails();
                orderDetailList.forEach(orderDetail -> {
                    ProductDetail productDetail = orderDetail.getProductDetail();
                    productDetail.setQuantity(productDetail.getQuantity() + orderDetail.getQuantity());
                    productDetailRepo.save(productDetail);
                });
                return ResponseEntity.ok("Trả hàng thành công");
            }
        }
        return ResponseEntity.ok("Đơn hàng đã được trả");
    }
}
