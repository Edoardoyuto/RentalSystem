package service;

import account.User;
import reservation.Reservation;

public interface IPaymentService {
    boolean executePayment(User user, Reservation reservation);
}