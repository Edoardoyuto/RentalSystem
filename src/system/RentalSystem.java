package system;

import java.util.Optional;
import java.util.Scanner;

import account.Account;
import account.Manager;
import account.User;
import reservation.Reservation;
import service.IAccountService;
import service.IReservationService;
import service.IVehicleManagementService;
import service.PaymentService;

public class RentalSystem {

    private final IAccountService accountService;
    private final IReservationService reservationService;
    private final IVehicleManagementService vehicleManager;
    private final PaymentService paymentService;

    private Account currentAccount;
    private final Scanner scan = new Scanner(System.in);

    public RentalSystem(IAccountService accountService,
                        IReservationService reservationService,
                        IVehicleManagementService vehicleManager,
                        PaymentService paymentService) {
        this.accountService = accountService;
        this.reservationService = reservationService;
        this.vehicleManager = vehicleManager;
        this.paymentService = paymentService;
    }

    public void run() {
        while (true) {
            System.out.println("login( L ) or Register ( R )?:");
            String input = scan.nextLine().trim();
            if (input.equalsIgnoreCase("L")) {
                login();
            } else if (input.equalsIgnoreCase("R")) {
                handleRegisterAsUser();
            } else {
                System.out.println("press L or R");
            }
        }
    }

    private void login() {
        System.out.print("e-mail を入力してください: ");
        String email = scan.nextLine();
        System.out.print("パスワード を入力してください: ");
        String password = scan.nextLine();

        Optional<Account> result = accountService.login(email, password);
        if (result.isPresent()) {
            currentAccount = result.get();
            System.out.println("ログインに成功しました。");

            if (currentAccount instanceof User user) {
                runUserMenu(user);
            } else if (currentAccount instanceof Manager manager) {
                runManagerMenu(manager);
            }
        } else {
            System.out.println("ログインに失敗しました。");
        }
    }

    private void runUserMenu(User user) {
        while (true) {
            System.out.println("\nログイン中のユーザー: " + user.getID());
            System.out.print("Logout( L ) or Rent( R ) or Return( B ) or History( S ): ");
            String input = scan.nextLine().trim();
            switch (input.toLowerCase()) {
                case "l" -> { logout(); return; }
                case "r" -> {
                    Reservation reservation = reservationService.makeReservation(user, scan);
                    if (reservation != null) {
                        boolean paid = paymentService.executePayment(user, reservation, scan);
                        if (!paid) reservationService.cancelReservation(user, reservation);
                    }
                }
                case "b" -> reservationService.returnVehicle(user, scan);
                case "s" -> reservationService.showHistory(user);
                default -> System.out.println("press L, R, B, or S");
            }
        }
    }

    private void runManagerMenu(Manager manager) {
        while (true) {
            System.out.println("\nログイン中のマネージャー: " + manager.getID());
            System.out.print("Logout(L) / Register(R) / Change(C) / Remove(D) / Price(P): ");
            switch (scan.nextLine()) {
                case "l" -> { logout(); return; }
                case "r" -> vehicleManager.registerVehicleWithInput(manager, scan);
                case "c" -> vehicleManager.changeAvailabilityWithInput(manager, scan);
                case "d" -> vehicleManager.removeVehicleWithInput(manager, scan);
                case "p" -> vehicleManager.updateRentalPriceWithInput(manager, scan);
                default -> System.out.println("press L, R, C, D, or P");
            }


        }
    }

    private void logout() {
        if (currentAccount != null) {
            accountService.logout(currentAccount);
            currentAccount = null;
        }
    }

    private void handleRegisterAsUser() {

        System.out.print("メール: ");
        String email = scan.nextLine();
        System.out.print("パスワード: ");
        String pw = scan.nextLine();
        System.out.print("ユーザー名: ");
        String name = scan.nextLine();

        accountService.register(email, pw, name);
        System.out.println("登録が完了しました。ログインしてください。");
    }
}
