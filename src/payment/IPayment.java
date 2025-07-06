package payment;

public interface IPayment {
    void pay();
    String getMethodName();
    int getAmount();
    java.util.Date getDate();
    void makeReceipt();
}
