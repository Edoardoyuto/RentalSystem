package system;

import account.IAccountService;
import account.Manager;
import service.AccountService;
import service.IReservationService;
import service.IVehicleManagementService;
import service.PaymentService;
import service.PricingCalculator;
import service.ReservationService;
import service.VehicleManager;
import vehicle.AbstractVehicle;
import vehicle.Car;

public class Main {
    public static void main(String[] args) {
        // 各サービスの初期化
        IAccountService accountService = new AccountService();
        PricingCalculator calculator = new PricingCalculator();
        IVehicleManagementService vehicleManager = new VehicleManager();
        IReservationService reservationService = new ReservationService(calculator,vehicleManager);
        
        PaymentService paymentService = new PaymentService();

        // レンタルシステム起動
        RentalSystem rentalSystem = new RentalSystem(
            accountService, reservationService, vehicleManager, paymentService
        );

        // アカウントの登録とログイン

        ((AccountService) accountService).register("user1", "user1@example.com", "pass","username");
        

        // 車両の登録（マネージャーとして）
        Manager manager = new Manager("admin", "admin@example.com", "adminpass", "Admin");
        AbstractVehicle car = new Car(1, "Toyota", true, 5000, false);
        vehicleManager.registerVehicle(manager, car);
        
        rentalSystem.run();

    }
}
