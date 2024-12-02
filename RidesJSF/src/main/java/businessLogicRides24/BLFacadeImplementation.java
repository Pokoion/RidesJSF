package businessLogicRides24;
import java.util.Date;
import java.util.List;

import nagusia.DataAccess;
import domain.Ride;
import eredua.domeinua.User;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.UserAlreadyExistsException;
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

   public Ride createRide( String from, String to, Date date, int nPlaces, float price, String driverEmail ) throws RideMustBeLaterThanTodayException, RideAlreadyExistException{
		Ride ride=dbManager.createRide(from, to, date, nPlaces, price, driverEmail);		
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

}

