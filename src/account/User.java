package account;

import java.util.ArrayList;
import java.util.List;

import reservation.Reservation;

public class User extends Account {
    private String username;
    private List<Reservation> reservationHistory;
    private String id;
    private static int userCount = 1;

    public User(String email, String password, String username) {
        super("User" + userCount, email, password);
        this.id = "User" + userCount;
        this.username = username;
        this.reservationHistory = new ArrayList<>();
        userCount++;
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

    public String getID() {
        return id;
    }
}
