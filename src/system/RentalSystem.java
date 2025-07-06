package system;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import account.Account;
import account.IAccountService;
import account.User;
import payment.IPayment;
import reservation.Reservation;
import service.IReservationService;
import service.IVehicleManagementService;
import service.PaymentService;
import vehicle.AbstractVehicle;
import vehicle.IVehicle;

public class RentalSystem {

    private IAccountService accountService;
    private IReservationService reservationService;
    private PaymentService paymentService;

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
    }

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
        // 仮：直近の予約を取得（本来は選ばせる）
        Reservation reservation = getLatestUnpaidReservationForCurrentUser();
        if (reservation == null) {
            System.out.println("未払いの予約が見つかりません。");
            return;
        }

        System.out.print("支払い方法を選んでください（1:credit, 2:cash, 3:convenience）: ");
        String methodType = scan.nextLine();

        Map<String, Object> options = new HashMap<>();
        if (methodType.equals("1")) {
            System.out.print("カード番号: ");
            options.put("cardNumber", scan.nextLine());
            System.out.print("有効期限: ");
            options.put("expirationDate", scan.nextLine());
        } else if (methodType.equals("3")) {
            options.put("paymentCode", "XYZ123");
        }

        IPayment payment = paymentService.createPayment(methodType, reservation.getPrice(), options);
        paymentService.processPayment(payment);

        reservation.setPayment(payment);
        System.out.println("以下の内容でよろしいですか？　１：yes 2: no");
        reservation.showReservation();
        
        int  a = scan.nextInt();
        if(a==1) {
        	System.out.println("支払いが完了しました。");
        }else {
        	handlePaymentProcess();
        }
        
        
    }
    
    public Reservation getLatestUnpaidReservationForCurrentUser() {
        if (currentAccount instanceof User user) {
            List<Reservation> history = user.getReservationHistory();
            for (int i = history.size() - 1; i >= 0; i--) {
                Reservation r = history.get(i);
                if (!r.isReturned() && r.getPayment() == null) {
                    return r;
                }
            }
        }
        return null;
    }


    public void handleRegisterVehicle(IVehicle vehicle) {

    }

    public void handleRegisterAsUser() {
        // 未実装
    }

    public void handleRegisterAsManager() {
        // 未実装
    }
}
