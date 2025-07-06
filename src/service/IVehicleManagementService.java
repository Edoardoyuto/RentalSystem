package service;

import java.util.List;

import account.Manager;
import vehicle.AbstractVehicle;

public interface IVehicleManagementService {
    void registerVehicle(Manager manager, AbstractVehicle vehicle);
    void changeAvailability(Manager manager, int vehicleId, boolean available);
    void updateRentalPrice(Manager manager, int vehicleId, int newPrice);
    List<AbstractVehicle> getAvailableVehicles();
    void removeVehicle(Manager manager, int vehicleId);
    AbstractVehicle findById(int vehicleId);
}
