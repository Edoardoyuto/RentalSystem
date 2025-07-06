package service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import payment.CashPayment;
import payment.ConvenienceStorePayment;
import payment.CreditCardPayment;
import payment.IPayment;

public class PaymentService {
    public void processPayment(IPayment payment) {
        payment.pay();
    }

    public IPayment createPaymentFromUserInput(Scanner scanner, int amount) {
        System.out.println("支払い方法を選択してください:");
        System.out.println("1. クレジットカード");
        System.out.println("2. 現金");
        System.out.println("3. コンビニ支払い");
        System.out.print("番号を入力: ");

        int method;
        try {
            method = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("無効な番号です。");
            return null;
        }

        switch (method) {
            case 1 -> {
                System.out.print("カード番号: ");
                String card = scanner.nextLine();
                System.out.print("有効期限 (MM/YY): ");
                String exp = scanner.nextLine();
                System.out.print("承認コード: ");
                String code = scanner.nextLine();
                return new CreditCardPayment(amount, new Date(), card, exp, code);
            }
            case 2 -> {
                return new CashPayment(amount, new Date());
            }
            case 3 -> {
                try {
                    System.out.print("支払いコード: ");
                    String code = scanner.nextLine();
                    System.out.print("支払期限 (yyyy-MM-dd): ");
                    Date deadline = new SimpleDateFormat("yyyy-MM-dd").parse(scanner.nextLine());
                    return new ConvenienceStorePayment(amount, new Date(), code, deadline);
                } catch (ParseException e) {
                    System.out.println("日付の形式が正しくありません。");
                    return null;
                }
            }
            default -> {
                System.out.println("不正な選択です。");
                return null;
            }
        }
    }
}