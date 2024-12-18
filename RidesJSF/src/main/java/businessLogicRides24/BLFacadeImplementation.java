package businessLogicRides24;
import java.util.Date;
import java.util.List;

import nagusia.DataAccess;
import eredua.domeinua.Alarm;
import eredua.domeinua.Car;
import eredua.domeinua.Ride;
import eredua.domeinua.User;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.UserAlreadyExistsException;
import exceptions.AlarmAlreadyExistsException;
import exceptions.CarAlreadyExistsException;
import exceptions.RideAlreadyExistException;

/**
 * It implements the business logic.
 */
public class BLFacadeImplementation implements BLFacade {
	DataAccess dbManager;

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		dbManager=new DataAccess();		    
	}
	
    public BLFacadeImplementation(DataAccess da)  {		
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");		
		dbManager=da;		
	}
    
    
    /**
     * {@inheritDoc}
     */
    public List<String> getDepartCities(){
		 List<String> departLocations=dbManager.getDepartCities();				
		return departLocations;
    	
    }
    /**
     * {@inheritDoc}
     */
	public List<String> getDestinationCities(String from){	
		 List<String> targetCities=dbManager.getArrivalCities(from);		
		return targetCities;
	}

	/**
	 * {@inheritDoc}
	 */

   public Ride createRide( String from, String to, Date date, int nPlaces, float price, String driverEmail, String licensePlate) throws RideMustBeLaterThanTodayException, RideAlreadyExistException{
		Ride ride=dbManager.createRide(from, to, date, nPlaces, price, driverEmail, licensePlate);		
		return ride;
   };
	
   /**
    * {@inheritDoc}
    */

	public List<Ride> getRides(String from, String to, Date date){
		List<Ride>  rides=dbManager.getRides(from, to, date);
		return rides;
	}

    
	/**
	 * {@inheritDoc}
	 */

	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date){
		List<Date>  dates=dbManager.getThisMonthDatesWithRides(from, to, date);
		return dates;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	
	public User register(String email, String name, String pass, String type) throws UserAlreadyExistsException, RuntimeException {
		return dbManager.register(email, name, pass, type);
	}

	
	/**
	 * {@inheritDoc}
	 */
	
	public User login(String email, String pass) {
		return dbManager.login(email, pass);
	}
	
    /**
     * {@inheritDoc}
     */
	
	public Car createCar(String email, String licensePlate, int seats, String brand, String model) throws CarAlreadyExistsException {
		return dbManager.createCar(email, licensePlate, seats, brand, model);
	}
	
    /**
     * {@inheritDoc}
     */
	
	public List<Car> getCarsByDriver(String email){
		return dbManager.getCarsByDriver(email);
	}
	
    /**
     * {@inheritDoc}
     */
	
	public Alarm createAlarm(String email,String depart, String arrival, Date date) throws AlarmAlreadyExistsException {
		return dbManager.createAlarm(email, depart, arrival, date);
	}
	
    /**
     * {@inheritDoc}
     */

	public List<Alarm> getRideMatchingAlarms(String email){
		return dbManager.getRideMatchingAlarms(email);
	}
	
    /**
     * {@inheritDoc}
     */
	
	public boolean disableAlarm(String email, int id) {
		return dbManager.disableAlarm(email, id);
	}
	
	public List<Ride> getRidesAriketa(String depart){
		return dbManager.getRidesAriketa(depart);
	}
}

