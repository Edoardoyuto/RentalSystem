package payment;

import java.util.Date;

public class ConvenienceStorePayment extends AbstractPayment {
    private String paymentCode;
    private Date deadline;

    public ConvenienceStorePayment(int amount, Date date, String paymentCode, Date deadline) {
        super(amount, date);
        this.paymentCode = paymentCode;
        this.deadline = deadline;
    }

    @Override
    public void pay() {
        System.out.println("コンビニで支払いました。");
        paid = true;
    }

    @Override
    public String getMethodName() {
        return "コンビニ支払い";
    }
}
