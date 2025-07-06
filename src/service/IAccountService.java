package service;

import java.util.Optional;

import account.Account;

public interface IAccountService {
    Optional<Account> login(String email, String password);
    void logout(Account account);
    void register(String id, String email, String password, String username);
}
