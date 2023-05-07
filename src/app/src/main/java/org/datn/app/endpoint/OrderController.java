package org.datn.app.endpoint;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.dto.OrderRequest;
import org.datn.app.core.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/doOrder")
    public ResponseEntity<?> doOrder(@RequestBody OrderRequest model) {
        return ResponseEntity.ok(orderService.doOrder(model));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> data = new HashMap<>();
        data.put("message", ex.getMessage());
        data.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }
}
