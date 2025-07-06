package vehicle;

public class Car extends AbstractVehicle {
	public boolean isManual;

    public Car(int id, String name, boolean isAvailable, int rentalPrice,boolean isManual) {
		super(id, name, isAvailable, rentalPrice);
		this.isManual=isManual;
	}

	
	public String getDisplayInfo() {
        return "ID:"+id +" "+name + " (Manual: " + isManual + ")";
    }
}