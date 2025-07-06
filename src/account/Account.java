package account;

public abstract class Account {//アカウント情報はaccountServiceが保持
    protected String accountId;
    protected String email;
    protected String password;
    Account(String accountId,String email,String password){
    	this.accountId=accountId;
    	this.email=email;
    	this.password=password;
    }
	public Object getEmail() {
		return email;
	}
	public Object getPassword() {
		return password;
	}
	public Object getID(){
		return accountId;
	}
}