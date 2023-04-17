package org.datn.app.endpoint;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.entity.Voucher;
import org.datn.app.core.service.VoucherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/voucher")
public class VoucherController {
    private final VoucherService voucherService;

    @PostMapping("/add")
    public ResponseEntity<?> addVoucher(@RequestBody Voucher voucher) {

        return ResponseEntity.ok(voucherService.doInsert(voucher));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateVoucher(@PathVariable Long id, @RequestBody Voucher voucher) {

        return ResponseEntity.ok(voucherService.doUpdateById(voucher, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteVoucher(@PathVariable Long id) {
        return ResponseEntity.ok(voucherService.doDeleteById(id));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getVoucher(@PathVariable Long id) {
        return ResponseEntity.ok(voucherService.findById(id));
    }

    @GetMapping("/page/{page}/{size}")
    public ResponseEntity<?> getAllVoucher(@PathVariable int page, @PathVariable int size) {
        return ResponseEntity.ok(voucherService.findAll(page, size));
    }


    @GetMapping("/findAll")
    public List<Voucher> findAllVoucher() {
        return voucherService.findAll();
    }
}
