package payment;

import java.util.Date;

public class CashPayment extends AbstractPayment {
    public CashPayment(int amount, Date date) {
        super(amount, date);
    }

    @Override
    public void pay() {
        this.paid = true;
        System.out.println("現金で支払いました。");
    }

    @Override
    public String getMethodName() {
        return "Cash";
    }
}