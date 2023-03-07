package org.datn.app.core.service;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.entity.Voucher;
import org.datn.app.core.repo.VoucherRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional(rollbackOn = RuntimeException.class)
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService{
    private final VoucherRepo voucherRepo;
    @Override
    public Voucher doInsert(Voucher voucher) {
        return voucherRepo.save(voucher);
    }

    @Override
    public Voucher doUpdateById(Voucher voucher, Long aLong) {
        Voucher voucher1 = voucherRepo.findById(aLong).get();
        voucher1.setName(voucher.getName());
        voucher1.setImage(voucher.getImage());
        return voucherRepo.save(voucher1);
    }

    @Override
    public Voucher doDeleteById(Long aLong) {
        voucherRepo.deleteById(aLong);
        return null;
    }

    @Override
    public Voucher findById(Long aLong) {
        return voucherRepo.findById(aLong).orElse(null);
    }

    @Override
    public List<Voucher> findAll() {
        return voucherRepo.findAll();
    }

    @Override
    public Page<Voucher> findAll(Integer page, Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return voucherRepo.findAll(pageable);
    }
}
