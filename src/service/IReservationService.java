package service;

import java.util.Date;
import java.util.List;

import account.User;
import reservation.Reservation;
import vehicle.AbstractVehicle;


public interface IReservationService {
    Reservation reserveVehicle(User user, int vehicleId, Date start, Date end);
    void returnVehicle(int reservationId);
    List<AbstractVehicle> getAvailableVehicles();
	Reservation getLatestUnpaidReservationForCurrentUser();
}