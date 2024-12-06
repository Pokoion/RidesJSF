package eredua.domeinua;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Alarm {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String depart;
	private String arrival;
	private Date date;
	private boolean active;
	
	@ManyToOne(targetEntity=Traveler.class, fetch=FetchType.LAZY)
	private Traveler traveler;
	
	public Alarm() {
		
	}

	public Alarm(String depart, String arrival, Date date, Traveler traveler) {
		this.depart = depart;
		this.arrival = arrival;
		this.date = date;
		this.active = true;
		this.traveler = traveler;
	}

	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}
