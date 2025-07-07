package receipt;

import payment.IPayment;

public class Receipt implements IReceipt {
    private final IPayment payment;

    public Receipt(IPayment payment) {
        this.payment = payment;
    }

    @Override
    public void makeReceipt() {
        System.out.println("=== レシート ===");
        System.out.println(getPaymentInfo());
        System.out.println("================");
    }

    @Override
    public String getPaymentInfo() {
        return "支払い方法: " + payment.getMethodName() + "\n" +
               "支払金額: " + payment.getAmount() + " 円\n" +
               "支払日: " + payment.getDate();
    }
}
