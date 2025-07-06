package payment;

import java.util.Date;

public class CashPayment extends AbstractPayment {
    public CashPayment(int amount, Date date) {
		super(amount, date);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public void pay() {
        // 現金支払い処理
    }

    public String getMethodName() {
        return "Cash";
    }
}