package payment;

import java.util.Date;

public class ConvenienceStorePayment extends AbstractPayment {
    public ConvenienceStorePayment(int amount, Date date) {
		super(amount, date);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	String paymentCode;
    String deadline;

    public void pay() {
        // コンビニ支払い処理
    }

    public String getMethodName() {
        return "ConvenienceStore";
    }
}
