package vehicle;

public abstract class AbstractVehicle implements IVehicle {
    protected int id;
    static int vheicleNumber = 0;
    protected String name;
    protected boolean isAvailable;
    protected int rentalPrice;
    
    AbstractVehicle(String name,boolean isAvailable,int rentalPrice){
    	this.id=vheicleNumber;
    	this.name=name;
    	this.isAvailable=isAvailable;
    	this.rentalPrice=rentalPrice;
    	vheicleNumber++;
    }
  
	public int getId() { return id; }
    public String getName() { return name; }
    public boolean isAvailable() { return isAvailable; }
    public int getRentalPrice() { return rentalPrice; }
    public void markAsRented() { isAvailable = false; }
    public void markAsReturned() { isAvailable = true; }
    public abstract String getDisplayInfo();
    public void setRentalPrice(int price) {rentalPrice = price;}
}