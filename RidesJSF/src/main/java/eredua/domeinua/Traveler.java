package eredua.domeinua;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Traveler extends User{

	@OneToMany(targetEntity=Alarm.class, mappedBy="traveler", fetch=FetchType.LAZY, cascade= {CascadeType.REMOVE,CascadeType.PERSIST})
	private List<Alarm> alarms;
	
    public Traveler() {
    }
	
    public Traveler(String email, String name, String pass) {
        super(email, name, pass);
        this.alarms = new ArrayList<>();
    }
    
	public Alarm addAlarm(String from, String to, Date date)  {
        Alarm alarm = new Alarm(from, to, date, this);
        alarms.add(alarm);
        return alarm;
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
