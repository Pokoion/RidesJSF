package eredua.domeinua;

public class UserFactory {
    public static User createUser(String type, String email, String name, String pass) {
        switch (type) {
            case User.TYPES.DRIVER:
                return new Driver(email, name, pass);
            case User.TYPES.ADMIN:
                return new Admin(email, name, pass);
            case User.TYPES.TRAVELER:
                return new Traveler(email, name, pass);
            default:
                throw new IllegalArgumentException("Unknown user type: " + type);
        }
    }
}
