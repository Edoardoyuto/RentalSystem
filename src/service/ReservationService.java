package service;

import java.util.Date;
import java.util.List;

import account.User;
import reservation.Reservation;
import vehicle.AbstractVehicle;

public class ReservationService implements IReservationService {

    private PricingCalculator pricingCalculator;
    private IVehicleManagementService vehicleManager; 
    
    public ReservationService(PricingCalculator pricingCalculator, IVehicleManagementService vehicleManager) {
        this.pricingCalculator = pricingCalculator;
        this.vehicleManager = vehicleManager;
    }

 
    public Reservation reserveVehicle(User user, int vehicleId, Date start, Date end) {
        AbstractVehicle vehicle = vehicleManager.findById(vehicleId);
        if (vehicle == null || !vehicle.isAvailable()) {
            throw new IllegalArgumentException("指定された車両は存在しないか、利用できません。");
        }

        int price = pricingCalculator.calcPrice(vehicle, start, end);

        Reservation reservation = new Reservation(
            user,
            vehicle,
            start,
            end,
            price
        );

        user.getReservationHistory().add(reservation);
        vehicle.markAsRented();

        return reservation;
    }


    @Override
    public List<AbstractVehicle> getAvailableVehicles() {
        return vehicleManager.getAvailableVehicles();
    }

    @Override
    public void returnVehicle(int reservationId) {
        // TODO: Reservation リストから検索して isReturned=true にする
    }
}
