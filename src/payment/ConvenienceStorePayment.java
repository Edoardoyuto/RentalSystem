package payment;

import java.util.Date;

public class ConvenienceStorePayment extends AbstractPayment {
    private String paymentCode;
    private Date deadline;

    public ConvenienceStorePayment(int amount, Date deadline) {
        super(amount, deadline); 
        this.deadline = deadline;
    }

    @Override
    public void pay() {
        if (paymentCode != null && deadline != null && deadline.after(new Date())) {
            System.out.println("コンビニ支払いで " + amount + " 円を支払いました。");
            this.date = new Date(); // 実際の支払日を設定
        } else {
            System.out.println("支払いコードまたは期限が無効です。");
        }
    }

    @Override
    public String getMethodName() {
        return "ConvenienceStore";
    }

    public void setPaymentCode(String code) {
        this.paymentCode = code;
    }

    public Date getDeadline() {
        return deadline;
    }
}
