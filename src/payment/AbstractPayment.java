package payment;

import java.util.Date;

import receipt.IReceipt;
import receipt.Receipt;

public abstract class AbstractPayment implements IPayment {
    protected int amount;
    protected Date date;
    protected boolean paid = false;

    public AbstractPayment(int amount, Date date) {
        this.amount = amount;
        this.date = date;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public boolean isPaid() {
        return paid;
    }

    @Override
    public void makeReceipt() {
        IReceipt receipt = new Receipt(this); // Receipt に委譲
        receipt.makeReceipt();
    }
}
