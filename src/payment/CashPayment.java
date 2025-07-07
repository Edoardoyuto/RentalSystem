package payment;

import java.util.Date;

public class CashPayment extends AbstractPayment {

    public CashPayment(int amount, Date date) {
        super(amount, date);
    }

    @Override
    public void pay() {
        System.out.println("現金で支払いました。");
        paid = true;
    }

    @Override
    public String getMethodName() {
        return "現金";
    }
}
