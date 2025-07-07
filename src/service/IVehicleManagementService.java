package service;

import java.util.List;
import java.util.Scanner;

import account.Manager;
import vehicle.AbstractVehicle;

public interface IVehicleManagementService {
    void changeAvailability(Manager manager, int vehicleId, boolean available);
    void updateRentalPrice(Manager manager, int vehicleId, int newPrice);
    List<AbstractVehicle> getAvailableVehicles();
    void removeVehicle(Manager manager, int vehicleId);
    AbstractVehicle findById(int vehicleId);

    // 入力付き拡張メソッド
    void registerVehicleWithInput(Manager manager, Scanner scan);
    void changeAvailabilityWithInput(Manager manager, Scanner scan);
    void removeVehicleWithInput(Manager manager, Scanner scan);
    void updateRentalPriceWithInput(Manager manager, Scanner scan); // ← ★追加
	void registerVehicle(AbstractVehicle car1);
}
