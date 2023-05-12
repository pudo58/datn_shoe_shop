package org.datn.app.core.service;

import org.datn.app.core.dto.ProductSearchRequest;
import org.datn.app.core.entity.Product;
import org.datn.app.core.entity.Voucher;
import org.datn.app.core.entity.extend.ProductDTO;
import org.datn.app.core.entity.extend.ProductResponse;
import org.datn.app.core.entity.extend.VoucherResponse;
import org.datn.app.core.service.base.ServiceCore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface VoucherService extends ServiceCore<Voucher,Long> {
    Page<VoucherResponse> findAllVoucher(Integer page, Integer size);
    ResponseEntity<?> addVoucher(Voucher voucher);
    VoucherResponse findVoucherByID(Long id);
    void deleteVoucher(Long id);
    ResponseEntity<?> updateVoucher(Voucher voucher);
}
