package account;

import java.util.ArrayList;
import java.util.List;

import reservation.Reservation;

public class User extends Account {
    public String username;
    private List<Reservation> reservationHistory;

    public User(String accountId, String email, String password, String name) {
        super(accountId, email, password);
        this.username = name;
        this.reservationHistory = new ArrayList<>();
    }

    public void add(Reservation reservation) {
        reservationHistory.add(reservation);
    }

    public List<Reservation> getReservationHistory() {
        return reservationHistory;
    }
}
