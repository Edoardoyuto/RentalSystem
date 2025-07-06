package service;

import java.util.ArrayList;
import java.util.List;

import account.Manager;
import vehicle.AbstractVehicle;

public class VehicleManager implements IVehicleManagementService {

    private List<AbstractVehicle> vehicles;

    public VehicleManager() {
        this.vehicles = new ArrayList<>();
    }

    public void registerVehicle(Manager manager, AbstractVehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void changeAvailability(Manager manager, int vehicleId, boolean available) {
        for (AbstractVehicle v : vehicles) {
            if (v.getId() == vehicleId) {
                if (available) v.markAsReturned();
                else v.markAsRented();
                break;
            }
        }
    }

    public void updateRentalPrice(Manager manager, int vehicleId, int newPrice) {
        for (AbstractVehicle v : vehicles) {
            if (v.getId() == vehicleId) {
                v.setRentalPrice(newPrice);
                break;
            }
        }
    }

    @Override
    public List<AbstractVehicle> getAvailableVehicles() {
        List<AbstractVehicle> available = new ArrayList<>();
        for (AbstractVehicle v : vehicles) {
            if (v.isAvailable()) {
                available.add(v);
            }
        }
        return available;
    }

    @Override
    public void removeVehicle(Manager manager, int vehicleId) {
        vehicles.removeIf(v -> v.getId() == vehicleId);
    }
    
    public AbstractVehicle findById(int vehicleId) {
        for (AbstractVehicle v : vehicles) {
            if (v.getId() == vehicleId) {
                return v;
            }
        }
        return null;
    }
    
}
