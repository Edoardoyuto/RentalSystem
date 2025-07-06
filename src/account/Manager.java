package account;

public class Manager extends Account {



	public Manager(String accountId, String email, String password,String name) {
		super(accountId, email, password);
		// TODO 自動生成されたコンストラクター・スタブ
		this.name=name;
	}


	public String name;
}