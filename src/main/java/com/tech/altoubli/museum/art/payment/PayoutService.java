package com.tech.altoubli.museum.art.payment;

import com.paypal.http.HttpResponse;
import com.paypal.http.exceptions.HttpException;
import com.paypal.payouts.*;
import com.paypal.core.PayPalHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PayoutService {

    private final PayPalHttpClient payPalClient;

    @Autowired
    public PayoutService(PayPalHttpClient payPalClient) {
        this.payPalClient = payPalClient;
    }

    public void initiatePayout(String subscriptionAmount, String receiverPayPalId) {
        // Calculate 70% of the subscription amount for payout
        double totalAmount = Double.parseDouble(subscriptionAmount);
        double payoutAmount = totalAmount * 0.7;

        // Construct the PayoutsPostRequest directly
        PayoutsPostRequest request = new PayoutsPostRequest();

        // Create the sender batch header
        SenderBatchHeader batchHeader = new SenderBatchHeader()
                .senderBatchId("batch_" + System.currentTimeMillis())
                .emailSubject("You have received a payout!");

        // Set up a single payout item with the calculated amount
        List<PayoutItem> items = new ArrayList<>();
        PayoutItem payoutItem = new PayoutItem()
                .recipientType("EMAIL")
                .amount(new Currency()
                        .currency("USD")
                        .value(String.format("%.2f", payoutAmount)))
                .receiver(receiverPayPalId);

        items.add(payoutItem);

        // Add the batch header and items to the request
        request.requestBody(new PayoutBatch()
                .senderBatchHeader(batchHeader)
                .items(items));

        // Execute the payout request
        try {
            HttpResponse<CreatePayoutResponse> response = payPalClient.execute(request);
            if (response.statusCode() == 201) {
                System.out.println("Payout successful!");
            } else {
                System.out.println("Payout failed with status: " + response.statusCode());
            }
        } catch (IOException | HttpException e) {
            throw new RuntimeException("Error initiating PayPal payout", e);
        }
    }
}
