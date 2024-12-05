package eredua.domeinua;

import javax.persistence.Entity;

@Entity
public class Traveler extends User{

    public Traveler() {
    }
	
    public Traveler(String email, String name, String pass) {
        super(email, name, pass);
    }

    @Override
    public String goMain() {
    	return "Traveler";
    }
    
    @Override
    public String goHome() {
    	return "HomeTraveler";
    }
}
