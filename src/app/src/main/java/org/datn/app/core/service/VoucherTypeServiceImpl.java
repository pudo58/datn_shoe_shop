package org.datn.app.core.service;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.entity.VoucherType;
import org.datn.app.core.repo.VoucherTypeRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional(rollbackOn = RuntimeException.class)
@RequiredArgsConstructor
public class VoucherTypeServiceImpl implements VoucherTypeService{
    private final VoucherTypeRepo voucherTypeRepo;
    @Override
    public VoucherType doInsert(VoucherType voucherType) {
        return voucherTypeRepo.save(voucherType);
    }

    @Override
    public VoucherType doUpdateById(VoucherType voucherType, Long aLong) {
        VoucherType voucherType1 = voucherTypeRepo.findById(aLong).get();
        voucherType1.setName(voucherType.getName());
        return voucherTypeRepo.save(voucherType1);
    }

    @Override
    public VoucherType doDeleteById(Long aLong) {
        voucherTypeRepo.deleteById(aLong);
        return null;
    }

    @Override
    public VoucherType findById(Long aLong) {
        return voucherTypeRepo.findById(aLong).orElse(null);
    }

    @Override
    public List<VoucherType> findAll() {
        return voucherTypeRepo.findAll();
    }

    @Override
    public Page<VoucherType> findAll(Integer page, Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return voucherTypeRepo.findAll(pageable);
    }
}
