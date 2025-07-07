package payment;

import java.util.Date;

public class CreditCardPayment extends AbstractPayment {
    private String cardNumber;
    private String expirationDate;
    private String approvalCode;

    public CreditCardPayment(int amount, Date date, String cardNumber, String expirationDate, String approvalCode) {
        super(amount, date);
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.approvalCode = approvalCode;
    }

    @Override
    public void pay() {
        System.out.println("クレジットカードで支払いました。");
        paid = true;
    }

    @Override
    public String getMethodName() {
        return "クレジットカード";
    }
}
