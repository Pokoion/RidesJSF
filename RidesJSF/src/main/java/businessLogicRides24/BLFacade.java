package businessLogicRides24;

import java.util.Date;
import java.util.List;

import eredua.domeinua.Alarm;
import eredua.domeinua.Car;
//import domain.Booking;
import eredua.domeinua.Ride;
import eredua.domeinua.User;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.UserAlreadyExistsException;
import exceptions.AlarmAlreadyExistsException;
import exceptions.CarAlreadyExistsException;
import exceptions.RideAlreadyExistException;
 
/**
 * Interface that specifies the business logic.
 */
public interface BLFacade  {
	  
	/**
	 * This method returns all the cities where rides depart 
	 * @return collection of cities
	 */
	public List<String> getDepartCities();
	
	/**
	 * This method returns all the arrival destinations, from all rides that depart from a given city  
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	public List<String> getDestinationCities(String from);


	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @param nPlaces available seats
	 * @param driver to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today 
 	 * @throws RideAlreadyExistException if the same ride already exists for the driver
	 */
   public Ride createRide( String from, String to, Date date, int nPlaces, float price, String driverEmail, String licensePlate) throws RideMustBeLaterThanTodayException, RideAlreadyExistException;
	
	
	/**
	 * This method retrieves the rides from two locations on a given date 
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @return collection of rides
	 */
	public List<Ride> getRides(String from, String to, Date date);
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride 
	 * @param date of the month for which days with rides want to be retrieved 
	 * @return collection of rides
	 */
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date);
	
	public User register(String email, String name, String pass, String type) throws UserAlreadyExistsException, RuntimeException;

	public User login(String email, String pass);
	
	public Car createCar(String email, String licensePlate, int seats, String brand, String model) throws CarAlreadyExistsException;
	
	public List<Car> getCarsByDriver(String email);
	
	public Alarm createAlarm(String email,String depart, String arrival, Date date) throws AlarmAlreadyExistsException;
	
	public List<Alarm> getRideMatchingAlarms(String email);
	
	public boolean disableAlarm(String email, int id);
}
