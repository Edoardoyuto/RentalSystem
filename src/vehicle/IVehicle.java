package vehicle;

public interface IVehicle {
    int getId();
    String getName();
    boolean isAvailable();
    int getRentalPrice();
    void markAsRented();
    void markAsReturned();
    String getDisplayInfo();
    void setRentalPrice(int price);
}