package service;

import java.util.Date;
import java.util.Map;

import payment.CashPayment;
import payment.ConvenienceStorePayment;
import payment.CreditCardPayment;
import payment.IPayment;

public class PaymentService {

    public IPayment createPayment(String methodType, int amount, Map<String, Object> options) {
        Date date = new Date(); // 支払日を現在時刻とする

        switch (methodType) {
            case "1":
                return new CreditCardPayment(
                    amount,
                    date
                );

            case "2":
                return new CashPayment(amount, date);

            case "3":
                return new ConvenienceStorePayment(
                    amount,
                    date
                );

            default:
                throw new IllegalArgumentException("Unsupported payment method: " + methodType);
        }
    }

    public void processPayment(IPayment payment) {
        payment.pay();
    }
}
