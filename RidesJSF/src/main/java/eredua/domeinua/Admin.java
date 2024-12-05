package eredua.domeinua;

import javax.persistence.Entity;

@Entity
public class Admin extends User {

    public Admin() {

    }
    
    public Admin(String email, String name, String pass) {
        super(email, name, pass);
    }

    @Override
    public String goMain() {
    	return "Admin";
    }
    
    @Override
    public String goHome() {
    	return "HomeAdmin";
    }
}