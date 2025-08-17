package com.example.billingApp.service;

import com.example.billingApp.io.Order.OrderRequest;
import com.example.billingApp.io.Order.OrderResponse;
import com.example.billingApp.io.Payment.PaymentVerificationRequest;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest orderRequest);

    void deleteOrder(String orderId);

    List<OrderResponse> getLatestOrders();

    OrderResponse verifyPayment(PaymentVerificationRequest request);

    Double sumSalesByDate(LocalDate date);

    Long countByOrderDate(LocalDate date);

    List<OrderResponse> findRecentOrders();

}
