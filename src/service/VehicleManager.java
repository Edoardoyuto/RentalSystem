package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import account.Manager;
import vehicle.AbstractVehicle;
import vehicle.Car;
import vehicle.Motorcycle;

public class VehicleManager implements IVehicleManagementService {

    private List<AbstractVehicle> vehicles;

    public VehicleManager() {
        this.vehicles = new ArrayList<>();
    }

    @Override
    public void changeAvailability(Manager manager, int vehicleId, boolean available) {
        for (AbstractVehicle v : vehicles) {
            if (v.getId() == vehicleId) {
                if (available) v.markAsReturned();
                else v.markAsRented();
                break;
            }
        }
    }

    @Override
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

    @Override
    public AbstractVehicle findById(int vehicleId) {
        for (AbstractVehicle v : vehicles) {
            if (v.getId() == vehicleId) {
                return v;
            }
        }
        return null;
    }

    private void showAllVehicles() {
        if (vehicles.isEmpty()) {
            System.out.println("登録されている車両はありません。");
        } else {
            System.out.println("=== 登録済み車両一覧 ===");
            System.out.printf("%-5s %-17s %-7s %-4s\n", "ID", "車両名", "価格", "利用可");
            for (AbstractVehicle v : vehicles) {
                String status = v.isAvailable() ? "○" : "×";
                System.out.printf("%-5d %-20s %-10d %-10s\n",
                    v.getId(), v.getName(), v.getRentalPrice(), status);
            }
            System.out.println("=======================");
        }
    }

    @Override
    public void registerVehicleWithInput(Manager manager, Scanner scan) {
        System.out.print("車両名を入力してください: ");
        String name = scan.nextLine();
        System.out.print("価格を入力してください: ");
        int price = Integer.parseInt(scan.nextLine());

        System.out.print("車(1)かバイク(2)か: ");
        int type = Integer.parseInt(scan.nextLine());

        AbstractVehicle vehicle = null;
        if (type == 1) {
            System.out.print("マニュアルですか？(true/false): ");
            boolean manual = Boolean.parseBoolean(scan.nextLine());
            vehicle = new Car(name, true, price, manual);
        } else if (type == 2) {
            System.out.print("排気量: ");
            int cc = Integer.parseInt(scan.nextLine());
            vehicle = new Motorcycle(name, true, price, cc);
        } else {
            System.out.println("無効な選択です。");
            return;
        }

        registerVehicle(vehicle);
        System.out.println("車両を登録しました。");
    }

    @Override
    public void changeAvailabilityWithInput(Manager manager, Scanner scan) {
        showAllVehicles();
        if (vehicles.isEmpty()) return;

        System.out.print("変更する車両のIDを入力してください: ");
        int id = parseVehicleId(scan);
        if (id == -1) return;

        AbstractVehicle v = findById(id);
        if (v == null) {
            System.out.println("指定されたIDの車両は存在しません。");
            return;
        }

        System.out.print("利用可能性を変更してください (true/false): ");
        boolean available = Boolean.parseBoolean(scan.nextLine());

        changeAvailability(manager, id, available);
        System.out.println("状態を更新しました。");
    }

    @Override
    public void removeVehicleWithInput(Manager manager, Scanner scan) {
        showAllVehicles();
        if (vehicles.isEmpty()) return;

        System.out.print("削除する車両のIDを入力してください: ");
        int id = parseVehicleId(scan);
        if (id == -1) return;

        AbstractVehicle v = findById(id);
        if (v == null) {
            System.out.println("指定されたIDの車両は存在しません。");
            return;
        }

        removeVehicle(manager, id);
        System.out.println("車両を削除しました。");
    }

    @Override
    public void updateRentalPriceWithInput(Manager manager, Scanner scan) {
        showAllVehicles();
        if (vehicles.isEmpty()) return;

        System.out.print("価格を変更する車両のIDを入力してください: ");
        int id = parseVehicleId(scan);
        if (id == -1) return;

        AbstractVehicle v = findById(id);
        if (v == null) {
            System.out.println("指定されたIDの車両は存在しません。");
            return;
        }

        System.out.print("新しい価格を入力してください: ");
        try {
            int price = Integer.parseInt(scan.nextLine());
            if (price < 0) {
                System.out.println("価格は0以上である必要があります。");
                return;
            }
            updateRentalPrice(manager, id, price);
            System.out.println("価格を更新しました。");
        } catch (NumberFormatException e) {
            System.out.println("価格は整数で入力してください。");
        }
    }

    private int parseVehicleId(Scanner scan) {
        try {
            return Integer.parseInt(scan.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("IDは整数で入力してください。");
            return -1;
        }
    }

	@Override
	public void registerVehicle(AbstractVehicle vehicle) {
		vehicles.add(vehicle);
	}
}
