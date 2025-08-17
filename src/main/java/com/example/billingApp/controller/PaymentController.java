package com.example.billingApp.controller;

import com.example.billingApp.io.Order.OrderResponse;
import com.example.billingApp.io.Payment.PaymentRequest;
import com.example.billingApp.io.Payment.PaymentVerificationRequest;
import com.example.billingApp.io.Payment.RazorpayOrderResponse;
import com.example.billingApp.service.OrderService;
import com.example.billingApp.service.RazorpayService;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final RazorpayService razorpayService;
    private final OrderService orderService;

    @PostMapping("/create-order")
    @ResponseStatus(HttpStatus.CREATED)
    public RazorpayOrderResponse createRazorpayOrder(@RequestBody PaymentRequest request) throws RazorpayException {
        return razorpayService.createOrder(request.getAmount(), request.getCurrency());
    }

    @PostMapping("/verify")
    public OrderResponse verifyPayment(@RequestBody PaymentVerificationRequest request)  {
        return orderService.verifyPayment(request);
    }

}
