package org.datn.app.endpoint;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.entity.Voucher;
import org.datn.app.core.service.VoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/voucher")
public class VoucherController {
    private final VoucherService voucherService;

    @PostMapping("/add")
    public ResponseEntity<?> addVoucher(@RequestBody Voucher voucher) {

        return ResponseEntity.ok(voucherService.addVoucher(voucher));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateVoucher(@PathVariable Long id, @RequestBody Voucher voucher) {

        return ResponseEntity.ok(voucherService.doUpdateById(voucher, id));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteVoucher(@PathVariable Long id) {
        voucherService.deleteVoucher(id);
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

    @GetMapping("/findByCode/{code}")
    public Voucher findByCode(@PathVariable String code) {
        return voucherService.findByCode(code);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> data = new HashMap<>();
        data.put("message", ex.getMessage());
        data.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }
}
