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
        this.paid = true;
        System.out.println("コンビニ支払いで支払いました。");
    }

    @Override
    public String getMethodName() {
        return "Convenience Store";
    }
}
