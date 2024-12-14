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
	
	/**
	 * Registers a new user in the system.
	 * 
	 * @param email the email address of the user
	 * @param name the full name of the user
	 * @param pass the password for the user
	 * @param type the type of user (e.g., Traveler, Driver, Admin)
	 * @return the newly created user
	 * @throws UserAlreadyExistsException if a user with the same email already exists
	 * @throws RuntimeException if there is an error during the registration process
	 */
	public User register(String email, String name, String pass, String type) throws UserAlreadyExistsException, RuntimeException;

	/**
	 * Authenticates a user by verifying their email and password.
	 * 
	 * @param email the email address of the user
	 * @param pass the password of the user
	 * @return the authenticated user, or null if authentication fails
	 */
	public User login(String email, String pass);
	
	/**
	 * Creates a new car for a driver.
	 * 
	 * @param email the email of the driver
	 * @param licensePlate the license plate of the car
	 * @param seats the number of seats in the car
	 * @param brand the brand of the car
	 * @param model the model of the car
	 * @return the created car
	 * @throws CarAlreadyExistsException if a car with the same license plate already exists
	 */
	public Car createCar(String email, String licensePlate, int seats, String brand, String model) throws CarAlreadyExistsException;
	
	/**
	 * Retrieves all cars associated with a specific driver.
	 * 
	 * @param email the email of the driver
	 * @return a list of cars owned by the driver
	 */
	public List<Car> getCarsByDriver(String email);
	
	/**
	 * Creates a new alarm for a traveler.
	 * 
	 * @param email the email of the traveler
	 * @param depart the departure location for the alarm
	 * @param arrival the arrival location for the alarm
	 * @param date the date of the ride for which the alarm is created
	 * @return the created alarm
	 * @throws AlarmAlreadyExistsException if an alarm with the same details already exists
	 */
	public Alarm createAlarm(String email,String depart, String arrival, Date date) throws AlarmAlreadyExistsException;
	
	/**
	 * Retrieves all active alarms that match the criteria for the rides.
	 * 
	 * @param email the email of the traveler
	 * @return a list of alarms matching available rides
	 */
	public List<Alarm> getRideMatchingAlarms(String email);
	
	/**
	 * Disables an alarm for a traveler.
	 * 
	 * @param email the email of the traveler
	 * @param id the ID of the alarm to disable
	 * @return true if the alarm was successfully disabled, false otherwise
	 */
	public boolean disableAlarm(String email, int id);
	
	public List<Ride> getRidesAriketa(String depart);
}
