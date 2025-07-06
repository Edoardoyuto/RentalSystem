package reservation;

import java.util.Date;

import account.Account;
import payment.IPayment;
import vehicle.AbstractVehicle;
import vehicle.IVehicle;

public class Reservation {
    public int reservationId;
    public Account accout;
    public IVehicle vehicle;
    public Date startDate;
    public Date endDate;
    public int price;
    public boolean isReturned;
    public IPayment payment;

    public Reservation(Account account, AbstractVehicle vehicle, Date start, Date end, int price) {
		this.account = account;
		
	}

	
	public int getDuration() {
        long diff = endDate.getTime() - startDate.getTime();
        return (int)(diff / (1000 * 60 * 60 * 24));
    }
	
	public int getPrice() {
		return price;
	}

    public void setPayment(IPayment payment) {
        this.payment = payment;
    }

    public IPayment getPayment() {
        return this.payment;
    }

    public void markReturned() {
        this.isReturned = true;
        vehicle.markAsReturned();
    }


	public boolean isReturned() {
		return isReturned;
	}
	
	public void showReservation() {
		System.out.println("_____________");
		System.out.println("車両:"+vehicle.getDisplayInfo());
		System.out.println("期間"+startDate+"～"+endDate);
		System.out.print("値段："+price);
		System.out.println("_____________");
	}
}