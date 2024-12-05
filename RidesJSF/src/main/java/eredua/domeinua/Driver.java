package eredua.domeinua;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;


@Entity
public class Driver extends User {

	@OneToMany(targetEntity=Ride.class, mappedBy="driver", fetch=FetchType.EAGER, cascade= {CascadeType.REMOVE,CascadeType.PERSIST})
	private List<Ride> rides;
	
	@OneToMany(targetEntity=Car.class, mappedBy="driver", fetch=FetchType.LAZY, cascade= {CascadeType.REMOVE,CascadeType.PERSIST})
	private List<Car> cars;
	
    public Driver() {
    }
    
    public Driver(String email, String name, String pass) {
        super(email, name, pass);
        this.rides = new ArrayList<>();
        this.cars = new ArrayList<>();
    }

    
	public List<Ride> getRides() {
		return rides;
	}

	public void setRides(List<Ride> rides) {
		this.rides = rides;
	}

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	/**
	 * This method creates a bet with a question, minimum bet ammount and percentual profit
	 * 
	 * @param question to be added to the event
	 * @param betMinimum of that question
	 * @return Bet
	 */
    
	public Ride addRide(String from, String to, Date date, int nPlaces, float price, Car car)  {
        Ride ride=new Ride(from,to,date,nPlaces,price, this, car);
        rides.add(ride);
        return ride;
	}
	
	public Car addCar(String licensePlate, int seats, String brand, String model)  {
        Car car=new Car(licensePlate, seats, brand, model, this);
        cars.add(car);
        return car;
	}

	/**
	 * This method checks if the ride already exists for that driver
	 * 
	 * @param from the origin location 
	 * @param to the destination location 
	 * @param date the date of the ride 
	 * @return true if the ride exists and false in other case
	 */
	public boolean doesRideExists(String from, String to, Date date)  {	
		for (Ride r:rides)
			if ( (java.util.Objects.equals(r.getDepart(),from)) && (java.util.Objects.equals(r.getArrival(),to)) && (java.util.Objects.equals(r.getDate(),date)) )
			 return true;
		
		return false;
	}

	public Ride removeRide(String from, String to, Date date) {
		boolean found=false;
		int index=0;
		Ride r=null;
		while (!found && index<=rides.size()) {
			r=rides.get(++index);
			if ( (java.util.Objects.equals(r.getDepart(),from)) && (java.util.Objects.equals(r.getArrival(),to)) && (java.util.Objects.equals(r.getDate(),date)) )
			found=true;
		}
			
		if (found) {
			rides.remove(index);
			return r;
		} else return null;
	}
	
    @Override
    public String goMain() {
    	return "Driver";
    }
    
    @Override
    public String goHome() {
    	return "HomeDriver";
    }

}