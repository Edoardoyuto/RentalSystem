package service;

import java.util.List;
import java.util.Scanner;

import account.User;
import reservation.Reservation;
import vehicle.AbstractVehicle;

public interface IReservationService {
    List<AbstractVehicle> getAvailableVehicles();
    
	Reservation makeReservation(User user, Scanner scan);

	void cancelReservation(User user, Reservation reservation);

	void returnVehicle(User user, Scanner scan);

	void showHistory(User user);
}
