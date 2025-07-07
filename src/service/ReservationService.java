package service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

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

    public Reservation makeReservation(User user, Scanner scan) {
        List<AbstractVehicle> available = getAvailableVehicles();
        if (available.isEmpty()) {
            System.out.println("レンタル可能な車がありません");
            return null;
        }
        
        System.out.println("================");
        available.forEach(v -> System.out.println(v.getDisplayInfo()));
        System.out.println("================");

        System.out.print("車両IDを入力: ");
        int vehicleId;
        try {
            vehicleId = Integer.parseInt(scan.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("IDは数字で入力してください。");
            return null;
        }

        AbstractVehicle vehicle = vehicleManager.findById(vehicleId);
        if (vehicle == null || !vehicle.isAvailable()) {
            System.out.println("有効なIDを入力してください。");
            return null;
        }

        Date start, end;
        try {
            System.out.print("貸出日 (yyyy-MM-dd): ");
            start = new SimpleDateFormat("yyyy-MM-dd").parse(scan.nextLine());
            System.out.print("返却日 (yyyy-MM-dd): ");
            end = new SimpleDateFormat("yyyy-MM-dd").parse(scan.nextLine());
            if (start.after(end)) {
                System.out.println("返却日は貸出日より後にしてください。");
                return null;
            }
        } catch (ParseException e) {
            System.out.println("日付の形式が正しくありません。");
            return null;
        }

        int price = pricingCalculator.calcPrice(vehicle, start, end);
        Reservation reservation = new Reservation(user, vehicle, start, end, price);
        user.getReservationHistory().add(reservation);
        vehicle.markAsRented();
        return reservation;
    }

    public void cancelReservation(User user, Reservation reservation) {
        user.getReservationHistory().remove(reservation);
        reservation.getVehicle().markAsReturned();
        System.out.println("予約はキャンセルされました。");
    }

    public void returnVehicle(User user, Scanner scan) {
        List<Reservation> ongoing = user.getReservationHistory().stream()
            .filter(r -> !r.isReturned())
            .toList();

        if (ongoing.isEmpty()) {
            System.out.println("返却対象の予約がありません。");
            return;
        }

        ongoing.forEach(r ->
            System.out.printf("車両ID: %d, 開始: %s, 終了: %s%n",
                r.getVehicle().getId(), r.getStartDate(), r.getEndDate())
        );

        System.out.print("返却する車両のIDを入力してください: ");
        try {
            int id = Integer.parseInt(scan.nextLine());
            Reservation target = ongoing.stream()
                .filter(r -> r.getVehicle().getId() == id)
                .findFirst()
                .orElse(null);

            if (target == null) {
                System.out.println("その車両IDに該当する未返却予約はありません。");
                return;
            }

            target.markReturned();
            target.getVehicle().markAsReturned();
            System.out.println("返却が完了しました。ありがとうございました！");

        } catch (NumberFormatException e) {
            System.out.println("IDは数字で入力してください。");
        }
    }

    public void showHistory(User user) {
        List<Reservation> history = user.getReservationHistory();
        if (history.isEmpty()) {
            System.out.println("予約履歴はありません。");
            return;
        }

        System.out.println("=== 予約履歴 ===");
        int index = 1;
        for (Reservation r : history) {
            System.out.printf("[%d] 車両ID: %d, 開始日: %s, 終了日: %s, 金額: %d円, 状態: %s%n",
                index++, r.getVehicle().getId(), r.getStartDate(), r.getEndDate(), r.getPrice(),
                r.isReturned() ? "返却済み" : r.getPayment() == null ? "未払い" : "支払い済み／未返却");
        }
    }

    @Override
    public List<AbstractVehicle> getAvailableVehicles() {
        return vehicleManager.getAvailableVehicles();
    }

}
