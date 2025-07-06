package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import account.Account;
import account.User;

public class AccountService implements IAccountService {
    private List<Account> accounts;

    public AccountService() {
        this.accounts = new ArrayList<>();
    }

    @Override
    public Optional<Account> login(String email, String password) {
        for (Account account : accounts) {
            if (account.getEmail().equals(email) && account.getPassword().equals(password)) {
                return Optional.of(account);
            }
        }
        return Optional.empty();
    }

    @Override
    public void logout(Account account) {
        // ログアウト処理が必要ならここに記述
    }

    @Override
    public void register(String id, String email, String password, String username) {
        User user = new User(id, email, password, username);
        accounts.add(user);
    }
}
