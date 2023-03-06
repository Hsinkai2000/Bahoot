public class User {
    int id;
    String email, name, password;
    int mobile;

    public User() {
    }

    public User(int id, String email, String name, String password, int mobile) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.mobile = mobile;
    }

}