package system;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import account.Account;
import account.Manager;
import account.User;
import payment.AbstractPayment;
import payment.IPayment;
import reservation.Reservation;
import service.IAccountService;
import service.IReservationService;
import service.IVehicleManagementService;
import service.PaymentService;
import vehicle.AbstractVehicle;
import vehicle.Car;
import vehicle.Motorcycle;

public class RentalSystem {

    private IAccountService accountService;
    private IReservationService reservationService;
    private PaymentService paymentService;
    private IVehicleManagementService vehicleManager;

    private Account currentAccount;
    private int state = 0; // 未ログイン状態

    private Scanner scan = new Scanner(System.in);

    public RentalSystem(
        IAccountService accountService,
        IReservationService reservationService,
        IVehicleManagementService vehicleManager,
        PaymentService paymentService
    ) {
        this.accountService = accountService;
        this.reservationService = reservationService;
        this.paymentService = paymentService;
        this.vehicleManager = vehicleManager;    }

    public void run() {
        char choose;

        while (true) {
            switch (state) {
                case 0:
                    System.out.println("login( L ) or Register ( R )?:");
                    String input0 = scan.nextLine();
                    if (input0.length() > 0) {
                        choose = input0.charAt(0);
                        if (choose == 'L' || choose == 'l') {
                            login();
                        } else if (choose == 'R' || choose == 'r') {
                            handleRegisterAsUser();
                        } else {
                            System.out.println("press L or R");
                        }
                    }else {
                        System.out.println("入力が空です。もう一度入力してください。");
                    }
                    break;

                case 1:
                    System.out.println("\nログイン中のアカウント: " + currentAccount.getID());
                    System.out.print("Logout( L ) or Rent a vehicle( R ) or See History( S ): ");
                    String input1 = scan.nextLine();
                    if (input1.length() > 0) {
                        choose = input1.charAt(0);
                        if (choose == 'L' || choose == 'l') {
                            logout();
                        } else if (choose == 'R' || choose == 'r') {
                            handleMakeReservation();
                        } else if (choose == 'S' || choose == 's') {
                            // 履歴機能（未実装）
                        } else {
                            System.out.println("press L or R or S ");
                        }
                    } else {
                        System.out.println("入力が空です。もう一度入力してください。");
                    }
                    break;
            }
        }
    }

    public boolean login() {
        System.out.print("e-mail を入力してください: ");
        String email = scan.nextLine();

        System.out.print("パスワード を入力してください: ");
        String password = scan.nextLine();
        System.out.println();

        Optional<Account> result = accountService.login(email, password);
        if (result.isPresent()) {
            System.out.println("ログインに成功しました。\n________");
            currentAccount = result.get();
            state = 1;
            return true;
        } else {
            System.out.println("ログインに失敗しました。");
            return false;
        }
    }

    public void logout() {
        if (currentAccount != null) {
            System.out.println(currentAccount.getEmail() + " さんがログアウトしました。\n________");
            accountService.logout(currentAccount);
            currentAccount = null;
            state = 0;
        } else {
            System.out.println("現在ログインしているアカウントはありません。\n________");
        }
    }

    public void handleMakeReservation() {
        System.out.println("以下のうち、レンタルする車のIDを入力してください");

        List<AbstractVehicle> availableVehicles = reservationService.getAvailableVehicles();
        for (AbstractVehicle vehicle : availableVehicles) {
            System.out.println(vehicle.getDisplayInfo());
        }

        int vehicleId = -1;

        while (true) {
            try {
                String idInput = scan.nextLine();
                vehicleId = Integer.parseInt(idInput);
                boolean found = false;
                for (AbstractVehicle v : availableVehicles) {
                    if (v.getId() == vehicleId) {
                        found = true;
                        break;
                    }
                }
                if (found) break;
                else System.out.println("有効なIDを入力してください");

            } catch (NumberFormatException e) {
                System.out.println("数字でIDを入力してください");
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate, endDate;

        try {
            System.out.print("貸出日を入力してください (例: 2025-07-05): ");
            startDate = dateFormat.parse(scan.nextLine());

            System.out.print("返却日を入力してください (例: 2025-07-10): ");
            endDate = dateFormat.parse(scan.nextLine());

            if (startDate.after(endDate)) {
                System.out.println("返却日は貸出日より後にしてください。");
                return;
            }
        } catch (ParseException e) {
            System.out.println("日付の形式が正しくありません。yyyy-MM-dd の形式で入力してください。");
            return;
        }

        if (currentAccount instanceof User user) {
            try {
                reservationService.reserveVehicle(user, vehicleId, startDate, endDate);
                System.out.println("予約が完了しました！");
                handlePaymentProcess();
            } catch (Exception e) {
                System.out.println("予約中にエラーが発生しました: " + e.getMessage());
            }
        } else {
            System.out.println("予約はユーザーアカウントでのみ可能です。");
        }
    }

    public void handlePaymentProcess() {
        if (!(currentAccount instanceof User user)) {
            System.out.println("ユーザーとしてログインしてください。");
            return;
        }

        Reservation unpaid = reservationService.getLatestUnpaidReservationForCurrentUser();
        if (unpaid == null) {
            System.out.println("未払いの予約はありません。");
            return;
        }

        int amount = unpaid.getPrice();
        IPayment payment = paymentService.createPaymentFromUserInput(scan, amount);
        if (payment == null) return;

        try {
            paymentService.processPayment(payment);
            if (payment instanceof AbstractPayment ap && !ap.isPaid()) {
                System.out.println("支払いが完了しませんでした。");
                return;
            }

            unpaid.setPayment(payment);
            payment.makeReceipt();
            System.out.println("支払いが完了しました。金額: " + amount + " 円");

        } catch (Exception e) {
            System.out.println("支払い中にエラーが発生しました: " + e.getMessage());
        }
    }




    public void handleRegisterVehicle() {
        if (!(currentAccount instanceof Manager manager)) {
            System.out.println("この操作は管理者のみ可能です。");
            return;
        }

        System.out.println("登録する車両の種類を選択してください:");
        System.out.println("1. 車 (Car)");
        System.out.println("2. バイク (Motorcycle)");
        System.out.print("番号を入力: ");
        int type;
        try {
            type = Integer.parseInt(scan.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("数字を入力してください。");
            return;
        }

        System.out.print("車両ID（整数）を入力してください: ");
        int id;
        try {
            id = Integer.parseInt(scan.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("IDは整数で入力してください。");
            return;
        }

        System.out.print("車両名を入力してください: ");
        String name = scan.nextLine();

        System.out.print("レンタル価格（1日あたり）を入力してください: ");
        int price;
        try {
            price = Integer.parseInt(scan.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("価格は整数で入力してください。");
            return;
        }

        AbstractVehicle vehicle = null;
        switch (type) {
            case 1 -> {
                System.out.print("マニュアル車ですか？（true / false）: ");
                boolean isManual = Boolean.parseBoolean(scan.nextLine());
                vehicle = new Car(name,true, price, isManual);
            }
            case 2 -> {
                System.out.print("排気量を入力してください（cc）: ");
                int displacement;
                try {
                    displacement = Integer.parseInt(scan.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("排気量は整数で入力してください。");
                    return;
                }
                vehicle = new Motorcycle(name,true, price, displacement);
            }
            default -> {
                System.out.println("不正な選択肢です。");
                return;
            }
        }

        try {
        	vehicleManager.registerVehicle(manager, vehicle);
            System.out.println("車両を登録しました。");
        } catch (Exception e) {
            System.out.println("車両登録に失敗しました: " + e.getMessage());
        }
    }


    public void handleRegisterAsUser() {
        // 未実装
    }

    public void handleRegisterAsManager() {
        // 未実装
    }
}
