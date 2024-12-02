package nagusia;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Query;
import org.hibernate.Session;

import configuration.UtilDate;
import eredua.domeinua.Driver;
import domain.Ride;
import eredua.HibernateUtil;
import eredua.domeinua.User;
import eredua.domeinua.UserFactory;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.UserAlreadyExistsException;

public class DataAccess {

	public DataAccess() {
		
	}
	
	public User register(String email, String name, String pass, String type) throws UserAlreadyExistsException, RuntimeException{
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction();
	    
	    try {
	        Query query = session.createQuery("FROM User u WHERE u.email = :email");
	        query.setParameter("email", email);
	        User existingUser = (User) query.uniqueResult();
	        
	        if (existingUser != null) {
	            throw new UserAlreadyExistsException("User with email " + email + " already exists.");
	        }

	        User u = UserFactory.createUser(type, email, name, pass);
	        session.save(u);
	        session.getTransaction().commit();    
	        return u;
	    } catch (UserAlreadyExistsException e) {
	        if (session.getTransaction().isActive()) {
	            session.getTransaction().rollback();
	        }
	        throw e;
	    } catch (Exception e) {
	        if (session.getTransaction().isActive()) {
	            session.getTransaction().rollback();
	        }
	        throw new RuntimeException("An error occurred while registering the user.", e);
	    }
	}
	
	public User login(String email, String pass) {
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction();
	    
	    Query q = session.createQuery("from User where email = :email and pass = :pass");
	    q.setParameter("email", email);
	    q.setParameter("pass", pass);
	    
        List<User> result = q.list();
	    
	    User user = null;
	    if (!result.isEmpty()) {
	        user = result.get(0); 
	    }

	    session.getTransaction().commit();
	    
	    return user;
	}
	
	/**
	 * This method returns all the cities where rides depart 
	 * @return collection of cities
	 */
	public List<String> getDepartCities(){
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction();
	    
        Query query = session.createQuery("SELECT DISTINCT r.from FROM Ride r ORDER BY r.from");
        List<String> cities = query.list();
        session.getTransaction().commit();
        return cities;

		
	}
	/**
	 * This method returns all the arrival destinations, from all rides that depart from a given city  
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	public List<String> getArrivalCities(String from){
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction();
	    Query query = session.createQuery("SELECT DISTINCT r.to FROM Ride r WHERE r.from = :from ORDER BY r.to");
	    query.setParameter("from", from);
	    List<String> arrivingCities = query.list();

	    session.getTransaction().commit();
		return arrivingCities;
		
	}
	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @param nPlaces available seats
	 * @param driverEmail to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today 
 	 * @throws RideAlreadyExistException if the same ride already exists for the driver
	 */
	public Ride createRide(String from, String to, Date date, int nPlaces, float price, String driverEmail) throws RideAlreadyExistException, RideMustBeLaterThanTodayException {
	    System.out.println(">> DataAccess: createRide=> from= " + from + " to= " + to + " driver=" + driverEmail + " date " + date);
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction();
	    try {
	        if (new Date().compareTo(date) > 0) {
	            throw new RideMustBeLaterThanTodayException(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorRideMustBeLaterThanToday"));
	        }

	        Query query = session.createQuery("FROM Driver d WHERE d.email = :email");
	        query.setParameter("email", driverEmail);
	        Driver driver = (Driver) query.uniqueResult();

	        if (driver == null) {
	            throw new NullPointerException("Driver with email " + driverEmail + " not found.");
	        }
	        
	        if (driver.doesRideExists(from, to, date)) {
	            session.getTransaction().commit();
	            throw new RideAlreadyExistException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.RideAlreadyExist"));
	        }

	        Ride ride = driver.addRide(from, to, date, nPlaces, price);
	        session.persist(driver); 
	        session.getTransaction().commit();
	        return ride;
	    } catch (Exception e) {
	        if (session.getTransaction().isActive()) {
	            session.getTransaction().rollback();
	        }
	        throw e;
	    }
	}
	
	/**
	 * This method retrieves the rides from two locations on a given date 
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @return collection of rides
	 */
	public List<Ride> getRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getRides=> from= "+from+" to= "+to+" date "+date);

	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction();

	    Query query = session.createQuery("SELECT r FROM Ride r WHERE r.from = :from AND r.to = :to AND r.date = :date");
	    query.setParameter("from", from);
	    query.setParameter("to", to);
	    query.setParameter("date", date);

	    List<Ride> rides = query.list();
	    session.getTransaction().commit();
	    return rides;
	}
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride 
	 * @param date of the month for which days with rides want to be retrieved 
	 * @return collection of rides
	 */
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction();

	    Date firstDayMonthDate = UtilDate.firstDayMonth(date);
	    Date lastDayMonthDate = UtilDate.lastDayMonth(date);

	    Query query = session.createQuery("SELECT DISTINCT r.date FROM Ride r WHERE r.from = :from AND r.to = :to AND r.date BETWEEN :startDate AND :endDate");
	    query.setParameter("from", from);
	    query.setParameter("to", to);
	    query.setParameter("startDate", firstDayMonthDate);
	    query.setParameter("endDate", lastDayMonthDate);

	    List<Date> dates = query.list();
	    session.getTransaction().commit();
	    return dates;
	}
}
