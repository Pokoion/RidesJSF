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
    
    public List<Alarm> getAlarms() {
		return alarms;
	}
    
    public List<Alarm> getActiveAlarms() {
		List<Alarm> activeAlarms = new ArrayList<>();
		for(Alarm alarma: this.alarms) {
			if(alarma.isActive()) activeAlarms.add(alarma);
		}
		return activeAlarms;
	}

	public void setAlarms(List<Alarm> alarms) {
		this.alarms = alarms;
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
	
	public void disableAlarm(int id) {
	    for (Alarm alarm : alarms) {
	        if (alarm.getId() == id) {
	            alarm.setActive(false);
	            break;
	        }
	    }
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
