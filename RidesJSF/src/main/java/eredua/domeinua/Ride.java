package eredua.domeinua;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Ride {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer rideNumber;
	private String depart;
	private String arrival;
	private int nPlaces;
	private Date date;
	private float price;
	
	@ManyToOne(targetEntity=Driver.class, fetch=FetchType.EAGER)
	private Driver driver;
	
	@ManyToOne(targetEntity=Car.class, fetch=FetchType.EAGER)
	private Car car;
	
	public Ride(){
	}
	
	public Ride(Integer rideNumber, String depart, String arrival, Date date, int nPlaces, float price, Driver driver, Car car) {
		this.rideNumber = rideNumber;
		this.depart = depart;
		this.arrival = arrival;
		this.nPlaces = nPlaces;
		this.date=date;
		this.price=price;
		this.driver = driver;
		this.car = car;
	}

	

	public Ride(String depart, String arrival,  Date date, int nPlaces, float price, Driver driver, Car car) {
		super();
		this.depart = depart;
		this.arrival = arrival;
		this.nPlaces = nPlaces;
		this.date=date;
		this.price=price;
		this.driver = driver;
		this.car = car;
	}
	
	/**
	 * Get the  number of the ride
	 * 
	 * @return the ride number
	 */
	public Integer getRideNumber() {
		return rideNumber;
	}

	
	/**
	 * Set the ride number to a ride
	 * 
	 * @param ride Number to be set	 */
	
	public void setRideNumber(Integer rideNumber) {
		this.rideNumber = rideNumber;
	}


	/**
	 * Get the origin  of the ride
	 * 
	 * @return the origin location
	 */

	public String getDepart() {
		return depart;
	}


	/**
	 * Set the origin of the ride
	 * 
	 * @param origin to be set
	 */	
	
	public void setDepart(String depart) {
		this.depart = depart;
	}

	/**
	 * Get the destination  of the ride
	 * 
	 * @return the destination location
	 */

	public String getArrival() {
		return arrival;
	}


	/**
	 * Set the origin of the ride
	 * 
	 * @param destination to be set
	 */	
	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	/**
	 * Get the free places of the ride
	 * 
	 * @return the available places
	 */
	
	/**
	 * Get the date  of the ride
	 * 
	 * @return the ride date 
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * Set the date of the ride
	 * 
	 * @param date to be set
	 */	
	public void setDate(Date date) {
		this.date = date;
	}

	
	public float getnPlaces() {
		return nPlaces;
	}

	/**
	 * Set the free places of the ride
	 * 
	 * @param  nPlaces places to be set
	 */

	public void setnPlaces(int nPlaces) {
		this.nPlaces = nPlaces;
	}

	/**
	 * Get the driver associated to the ride
	 * 
	 * @return the associated driver
	 */
	public Driver getDriver() {
		return driver;
	}

	/**
	 * Set the driver associated to the ride
	 * 
	 * @param driver to associate to the ride
	 */
	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public String toString(){
		return rideNumber+";"+";"+depart+";"+arrival+";"+date;  
	}

}
