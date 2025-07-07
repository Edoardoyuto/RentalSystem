package account;

public class Manager extends Account {
    private String name;
    private static int userCount = 1; // 初期化
    private String id;

    public Manager(String email, String password, String name) {
        super("Manager" + userCount, email, password); 
        this.id = "Manager" + userCount;               
        this.name = name;
        userCount++;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return id;
    }
}
