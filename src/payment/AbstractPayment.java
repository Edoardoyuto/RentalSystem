package payment;

import java.util.Date;

public abstract class AbstractPayment implements IPayment {
    protected int amount;
    protected Date date;
    protected boolean paid = false;

    public AbstractPayment(int amount, Date date) {
        this.amount = amount;
        this.date = date;
    }

    public int getAmount() { return amount; }
    public Date getDate() { return date; }
    public boolean isPaid() { return paid; }

    public void makeReceipt() {
        System.out.println("--- Receipt ---");
        System.out.println("Payment Method: " + getMethodName());
        System.out.println("Amount: " + amount);
        System.out.println("Date: " + date);
    }
}