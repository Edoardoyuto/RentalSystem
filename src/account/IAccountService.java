package account;

import java.util.Optional;

public interface IAccountService {
	Optional<Account> login(String email, String password);
    void logout(Account account);
}
