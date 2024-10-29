package com.tech.altoubli.museum.art.payment;

import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.*;
import com.paypal.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PayPalHttpClient payPalClient;
    private final PayoutService payoutService;

    @Autowired
    public PaymentController(PayPalHttpClient payPalClient, PayoutService payoutService) {
        this.payPalClient = payPalClient;
        this.payoutService = payoutService;
    }

    // Endpoint to create an order
    @PostMapping("/create-order")
    public Map<String, String> createOrder(@RequestBody Map<String, String> request) {
        OrdersCreateRequest orderRequest = new OrdersCreateRequest();
        orderRequest.prefer("return=representation");
        orderRequest.requestBody(new OrderRequest()
                .checkoutPaymentIntent("CAPTURE")
                .purchaseUnits(Collections.singletonList(new PurchaseUnitRequest()
                        .amountWithBreakdown(new AmountWithBreakdown()
                                .currencyCode("USD")
                                .value(request.get("amount"))))));

        try {
            HttpResponse<Order> response = payPalClient.execute(orderRequest);
            return Collections.singletonMap("id", response.result().id());
        } catch (IOException e) {
            throw new RuntimeException("Error creating PayPal order", e);
        }
    }

    // Endpoint to capture the order and trigger payout if successful
    @PostMapping("/capture-order/{orderId}")
    public String captureOrder(@PathVariable String orderId) {
        OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);
        request.requestBody(new OrderRequest());

        try {
            HttpResponse<Order> response = payPalClient.execute(request);
            if ("COMPLETED".equals(response.result().status())) {
                // Calculate the 70% payout amount
                String subscriptionAmount = response.result().purchaseUnits().get(0).amountWithBreakdown().value();
                payoutService.initiatePayout(subscriptionAmount, "SUBSCRIBED_USER_PAYPAL_ID");
                return "Order captured and payout triggered!";
            }
            return "Order capture failed.";
        } catch (IOException e) {
            throw new RuntimeException("Error capturing PayPal order", e);
        }
    }
}
