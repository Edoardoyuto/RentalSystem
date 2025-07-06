package payment;

import java.util.Date;

public abstract class AbstractPayment implements IPayment {
    protected int amount;
    protected Date date;
    
    AbstractPayment(int amount,Date date){
    	this.amount = amount;
    	this.date = date;
    }

    public int getAmount() { return amount; }
    public Date getDate() { return date; }
}