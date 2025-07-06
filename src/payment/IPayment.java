package payment;

import java.util.Date;

public interface IPayment {
    void pay();
    String getMethodName();
    int getAmount();
    Date getDate();
}