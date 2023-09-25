package starter.Common;

public class RegisteredUser extends net.serenitybdd.screenplay.Actor {
    private final String username;
    private final String password;

    public RegisteredUser(
            String name,
            String username,
            String password)
    {
        super(name);
        this.username = username;
        this.password = password;
    }
    public String username() {
        return username;
    }
    public String password() {
        return password;
    }
}
