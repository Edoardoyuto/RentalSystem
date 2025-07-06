package account;

import java.util.ArrayList;
import java.util.List;

import reservation.Reservation;

public class User extends Account {
    private String username;
    private List<Reservation> reservationHistory;

    public User(String id, String email, String password, String username) {
        super(id, email, password);
        this.username = username;
        this.reservationHistory = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public List<Reservation> getReservationHistory() {
        return reservationHistory;
    }

    public void addReservation(Reservation reservation) {
        reservationHistory.add(reservation);
    }
}
