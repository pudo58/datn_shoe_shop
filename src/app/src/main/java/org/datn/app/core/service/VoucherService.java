package org.datn.app.core.service;

import org.datn.app.core.entity.Voucher;
import org.datn.app.core.entity.extend.VoucherResponse;
import org.datn.app.core.service.base.ServiceCore;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface VoucherService extends ServiceCore<Voucher, Long> {
    Page<VoucherResponse> findAllVoucher(Integer page, Integer size);

    ResponseEntity<?> addVoucher(Voucher voucher);

    VoucherResponse findVoucherByID(Long id);

    void deleteVoucher(Long id);

    ResponseEntity<?> updateVoucher(Voucher voucher);
}
