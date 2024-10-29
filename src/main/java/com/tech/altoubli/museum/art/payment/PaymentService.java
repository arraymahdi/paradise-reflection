package com.tech.altoubli.museum.art.payment;

import com.paypal.core.PayPalHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final PayPalHttpClient payPalClient;

    @Autowired
    public PaymentService(PayPalHttpClient payPalClient) {
        this.payPalClient = payPalClient;
    }
}
