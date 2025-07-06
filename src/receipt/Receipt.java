package receipt;

import payment.IPayment;

public class Receipt implements IReceipt {
    private IPayment payment;

    public Receipt(IPayment payment) {
        this.payment = payment;
    }

    public void makeReceipt() {
        // 領収書の生成処理（例：出力や保存）
    }

    public String getPaymentInfo() {
        return payment.getMethodName() + ": " + payment.getAmount();
    }
}
