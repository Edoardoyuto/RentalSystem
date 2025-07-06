package vehicle;

public class Car extends AbstractVehicle {
	public boolean isManual;

    public Car(String name, boolean isAvailable, int rentalPrice,boolean isManual) {
		super(name, isAvailable, rentalPrice);
		this.isManual=isManual;
	}

	
	public String getDisplayInfo() {
        return "ID:"+id +" "+name + " (Manual: " + isManual + ")　一日当たりの価格；"+rentalPrice+"円";
    }
}