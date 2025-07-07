package system;

import service.AccountService;
import service.IAccountService;
import service.IPaymentService;
import service.IReservationService;
import service.IVehicleManagementService;
import service.PaymentService;
import service.PricingCalculator;
import service.ReservationService;
import service.VehicleManager;
import vehicle.AbstractVehicle;
import vehicle.Car;
import vehicle.Motorcycle;

public class Main {
    public static void main(String[] args) {
        // 各サービスの初期化
        IAccountService accountService = new AccountService();
        PricingCalculator calculator = new PricingCalculator();
        IVehicleManagementService vehicleManager = new VehicleManager();
        IReservationService reservationService = new ReservationService(calculator, vehicleManager);

        // IPaymentServiceインターフェース型でPaymentServiceをインスタンス化
        IPaymentService paymentService = new PaymentService();

        // レンタルシステム起動時に、すべてのサービス（インターフェース）を渡す
        RentalSystem rentalSystem = new RentalSystem(
            accountService, reservationService, vehicleManager, paymentService
        );

        // アカウントの登録
        ((AccountService) accountService).register("user1@example.com", "pass", "username");
        ((AccountService) accountService).registerManager("manager@example.com", "pass", "ManagerName");

        // 車両の登録
        AbstractVehicle car1 = new Car("Toyota", true, 2000, false);
        AbstractVehicle car2 = new Car("Matuda", true, 3000, false);
        AbstractVehicle bike1 = new Motorcycle("Honda", true, 4000, 250);

        vehicleManager.registerVehicle(car1);
        vehicleManager.registerVehicle(car2);
        vehicleManager.registerVehicle(bike1);

        // システム実行
        rentalSystem.run();
    }
}