package org.datn.app.core.service;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.entity.Order;
import org.datn.app.core.repo.OrderRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Transactional(rollbackOn = RuntimeException.class)
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderDetailService orderDetailService;
    private final OrderRepo orderRepo;
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
        return orderRepo.findByCodeAndStatus(code,status);
    }
}
