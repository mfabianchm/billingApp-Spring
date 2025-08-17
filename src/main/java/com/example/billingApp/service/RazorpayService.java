package com.example.billingApp.service;

import com.example.billingApp.io.Payment.RazorpayOrderResponse;
import com.razorpay.RazorpayException;


import java.io.IOException;

public interface RazorpayService {

    RazorpayOrderResponse createOrder(Double amount, String currency) throws RazorpayException;
}
