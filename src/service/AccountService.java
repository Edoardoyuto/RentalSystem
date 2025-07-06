package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import account.Account;
import account.IAccountService;
import account.User;

public class AccountService implements IAccountService {

    private List<Account> accounts;

    public AccountService() {
        this.accounts = new ArrayList<Account>();
    }

    public Optional<Account> login(String email, String password) {
        System.out.println("DEBUG: accounts に登録されている全要素:");
        for (Account a : accounts) {
            System.out.println(" - " + a.getClass().getName());
        }

        for (Account account : accounts) {
            if (account.getEmail().equals(email) && account.getPassword().equals(password)) {
                return Optional.of(account);
            }
        }
        return Optional.empty();
    }


    public void logout(Account account) {
        // いらなかったかも
    }

    public void register(String accountId, String email, String password, String username) {
        User user = new User(accountId, email, password, username);
        accounts.add(user);
        System.out.println("DEBUG: 登録されたアカウントの型 = " + user.getClass().getName());
    }

}
