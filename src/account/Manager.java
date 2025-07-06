package account;

public class Manager extends Account {
    private String name;

    public Manager(String id, String email, String password, String name) {
        super(id, email, password);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
