package account;

public abstract class Account {
    protected String accountId;
    protected String email;
    protected String password;

    public Account(String id, String email, String password) {
        this.accountId = id;
        this.email = email;
        this.password = password;
    }

    public String getID() {
        return accountId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
