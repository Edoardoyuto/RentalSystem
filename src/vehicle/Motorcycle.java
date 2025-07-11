package vehicle;

public class Motorcycle extends AbstractVehicle {
    public Motorcycle(String name, boolean isAvailable, int rentalPrice,int displacement) {
		super(name, isAvailable, rentalPrice);
		this.displacement=displacement;
	}

	public int displacement;

    public String getDisplayInfo() {
        return "ID:"+id +" "+name + " (" + displacement + "cc)一日当たりの価格；"+rentalPrice+"円";
    }
}