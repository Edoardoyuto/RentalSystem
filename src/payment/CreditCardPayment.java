package payment;

import java.util.Date;

public class CreditCardPayment extends AbstractPayment {
    public CreditCardPayment(int amount, Date date) {
		super(amount, date);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	String cardNumber;
    String expirationDate;
    String approvalCode;

    public void pay() {
        // クレジットカード支払い処理
    }

    public String getMethodName() {
        return "CreditCard";
    }
}