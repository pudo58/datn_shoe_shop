package org.datn.app.core.service;

import com.sun.deploy.config.Config;
import lombok.RequiredArgsConstructor;
import org.datn.app.constant.OrderConstant;
import org.datn.app.constant.PaymentMethodConstant;
import org.datn.app.core.dto.OrderRequest;
import org.datn.app.core.entity.*;
import org.datn.app.core.repo.*;
import org.datn.app.util.EncryptString;
import org.datn.app.util.GenerateString;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
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
    public Map<String,Object> doOrder(OrderRequest model) {
        if(orderRepo.findByUserId(model.getUserId()).size() > 0){
            throw new RuntimeException("Bạn đã có đơn hàng đang chờ xử lý");
        }
        List<Cart> cartList = cartRepo.findAllById(model.getCartIdList());
        User user = userRepo.findById(model.getUserId()).orElseThrow(
                () -> new RuntimeException("Người dùng không tồn tại")
        );
        AtomicReference<Double> total = new AtomicReference<>((double) 0);
        Order order = new Order();
        order.setAddress(model.getAddress());
        order.setPhoneNumber(model.getPhoneNumber());
        order.setName(model.getName());
        order.setEmail(model.getEmail());
        order.setCode(GenerateString.generateString(10));
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
                orderDetail.setPrice(cart.getPrice());
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
                e.printStackTrace();
                throw new RuntimeException("Có lỗi xảy ra khi đặt hàng");
            }
            Map<String, Object> data = new HashMap<>();
            data.put("message", "Đặt hàng thành công");
            data.put("status", HttpStatus.OK.value());
            return data;
        } catch (Exception e) {
            e.printStackTrace();
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

    @Override
    public ResponseEntity<?> test(HttpServletRequest req, HttpServletResponse response) throws UnsupportedEncodingException {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_OrderInfo = req.getParameter("vnp_OrderInfo");
        String orderType = req.getParameter("ordertype");
        String vnp_TxnRef = new Random().nextInt(1000000000) + "";
        String vnp_IpAddr = req.getRemoteAddr();
        String vnp_TmnCode = "LMLNPHDC";

        int amount = Integer.parseInt(req.getParameter("amount")) * 100;
        Map vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        String bank_code = req.getParameter("bankcode");
        if (bank_code != null && !bank_code.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bank_code);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = req.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", "http://localhost:8000/api/callback");
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        //Add Params of 2.1.0 Version
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        //Billing
        vnp_Params.put("vnp_Bill_Mobile", req.getParameter("txt_billing_mobile"));
        vnp_Params.put("vnp_Bill_Email", req.getParameter("txt_billing_email"));
        String fullName = (req.getParameter("txt_billing_fullname")).trim();
        if (fullName != null && !fullName.isEmpty()) {
            int idx = fullName.indexOf(' ');
            String firstName = fullName.substring(0, idx);
            String lastName = fullName.substring(fullName.lastIndexOf(' ') + 1);
            vnp_Params.put("vnp_Bill_FirstName", firstName);
            vnp_Params.put("vnp_Bill_LastName", lastName);

        }
        vnp_Params.put("vnp_Bill_Address", req.getParameter("txt_inv_addr1"));
        vnp_Params.put("vnp_Bill_City", req.getParameter("txt_bill_city"));
        vnp_Params.put("vnp_Bill_Country", req.getParameter("txt_bill_country"));
        if (req.getParameter("txt_bill_state") != null && !req.getParameter("txt_bill_state").isEmpty()) {
            vnp_Params.put("vnp_Bill_State", req.getParameter("txt_bill_state"));
        }
        // Invoice
        vnp_Params.put("vnp_Inv_Phone", req.getParameter("txt_inv_mobile"));
        vnp_Params.put("vnp_Inv_Email", req.getParameter("txt_inv_email"));
        vnp_Params.put("vnp_Inv_Customer", req.getParameter("txt_inv_customer"));
        vnp_Params.put("vnp_Inv_Address", req.getParameter("txt_inv_addr1"));
        vnp_Params.put("vnp_Inv_Company", req.getParameter("txt_inv_company"));
        vnp_Params.put("vnp_Inv_Taxcode", req.getParameter("txt_inv_taxcode"));
        vnp_Params.put("vnp_Inv_Type", req.getParameter("cbo_inv_type"));
        //Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = EncryptString.encryptSHA512("ICPAWVEQOBTCRAEAQXHISNEANCVDILVG" , hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html"+ "?" + queryUrl;
        JSONObject jo = new JSONObject();
        jo.append("code", "00");
        jo.append("message", "success");
        jo.append("data", paymentUrl);
        return new ResponseEntity<>(jo.toString(), HttpStatus.OK);
    }
}
