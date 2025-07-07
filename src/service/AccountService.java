package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import account.Account;
import account.Manager;
import account.User;

public class AccountService implements IAccountService {
    private List<Account> accounts;

    public AccountService() {
        this.accounts = new ArrayList<>();
    }

    @Override
    public Optional<Account> login(String email, String password) {
        return accounts.stream()
                .filter(a -> a.getEmail().equals(email) && a.getPassword().equals(password))
                .findFirst();
    }

    @Override
    public void logout(Account account) {
        System.out.println(account.getEmail() + " さんがログアウトしました。");
    }

    @Override
    public void register(String email, String password, String username) {
        User user = new User(email, password, username);
        accounts.add(user);
    }
    
    public void registerManager(String email, String password, String name) {
        Manager manager = new Manager(email, password, name);
        accounts.add(manager);
    }

}
