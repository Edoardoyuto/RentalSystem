package payment;

import java.util.Date;

public class CashPayment extends AbstractPayment {
    public CashPayment(int amount, Date date) {
		super(amount, date);
		// TODO 自動生成されたコンストラクター・スタブ
	}

    public void pay() {
        System.out.println("現金で " + amount + " 円を支払いました。");
        this.date = new Date(); // 支払日を現在に
    }

    public String getMethodName() {
        return "Cash";
    }
}