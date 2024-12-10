package nagusia;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import configuration.UtilDate;
import eredua.domeinua.Admin;
import eredua.domeinua.Alarm;
import eredua.domeinua.Car;
import eredua.domeinua.Driver;
import eredua.domeinua.Ride;
import eredua.domeinua.Traveler;
import eredua.HibernateUtil;
import eredua.domeinua.User;
import eredua.domeinua.User.TYPES;
import eredua.domeinua.UserFactory;
import exceptions.AlarmAlreadyExistsException;
import exceptions.CarAlreadyExistsException;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.UserAlreadyExistsException;

public class DataAccess {

	public DataAccess() {
        Configuration configuration = new Configuration().configure();
        String ddlOption = configuration.getProperty("hibernate.hbm2ddl.auto");

        if ("create".equalsIgnoreCase(ddlOption)) {
            System.out.println("Hibernate config set to 'create'. Initializing database...");
            initializeDatabase();
            System.out.println("--------------------------DataBase initialized--------------------------");
        } else {
            System.out.println("No database initialization needed. Hibernate config: " + ddlOption);
        }
	}
	
	private void initializeDatabase() {
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction();
	    
        Calendar calendar = Calendar.getInstance();      
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date datePlusOneDay = calendar.getTime();
	    
		Traveler traveler = (Traveler) UserFactory.createUser(TYPES.TRAVELER, "t@gmail.com", "Ibai Llanos", "a");
		Admin admin = (Admin) UserFactory.createUser(TYPES.ADMIN, "a@gmail.com", "Pedro Sanchez", "a");
		Driver driver = (Driver) UserFactory.createUser(TYPES.DRIVER, "d@gmail.com", "Albert Einstein", "a");
		Car car = driver.addCar("1234AAA", 4, "Opel", "Astra");
		Ride ride = driver.addRide("Ordizia", "Beasain", datePlusOneDay, 3, (float) 1.5, car);
		Alarm alarm = traveler.addAlarm("Ordizia", "Beasain", datePlusOneDay);
		
		session.persist(traveler);
		session.persist(admin);
		session.persist(driver);
		
        session.getTransaction().commit();  
	}

	public User register(String email, String name, String pass, String type) throws UserAlreadyExistsException, RuntimeException{
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction();
	    
	    try {
	        User existingUser = (User) session.get(User.class, email);
	        
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
	    
        Query query = session.createQuery("SELECT DISTINCT r.depart FROM Ride r ORDER BY r.arrival");
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
	    Query query = session.createQuery("SELECT DISTINCT r.arrival FROM Ride r WHERE r.depart = :depart ORDER BY r.arrival");
	    query.setParameter("depart", from);
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
	public Ride createRide(String from, String to, Date date, int nPlaces, float price, String driverEmail, String licensePlate) throws RideAlreadyExistException, RideMustBeLaterThanTodayException {
	    System.out.println(">> DataAccess: createRide=> from= " + from + " to= " + to + " driver=" + driverEmail + " date " + date + "car" + licensePlate);
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction();
	    try {
	        if (new Date().compareTo(date) > 0) {
	            throw new RideMustBeLaterThanTodayException("Ride must be later than today.");
	        }

	        Driver driver = (Driver) session.get(Driver.class, driverEmail);
	        Car car = (Car) session.get(Car.class, licensePlate);
	        
	        if (driver == null) {
	            throw new NullPointerException("Driver with email " + driverEmail + " not found.");
	        }
	        
	        if (car == null) {
	            throw new NullPointerException("Car with licensePlate " + licensePlate + " not found.");
	        }	        

	        if (car.getSeats() <= nPlaces) {
	        	throw new IllegalArgumentException("Car with license Plate " + car.getLicensePlate() + " does not have enough seats.");
	        }
	        
		    Query q = session.createQuery("from Ride where depart = :depart and arrival = :arrival and date = :date and driver.email = :driverEmail");
		    q.setParameter("depart", from);
		    q.setParameter("arrival", to);
		    q.setParameter("date", date);
		    q.setParameter("driverEmail", driverEmail);
		    
		    if(!q.list().isEmpty()) {
	            throw new RideAlreadyExistException("Ride already exists.");
		    }
	        
	        Ride ride = driver.addRide(from, to, date, nPlaces, price, car);	        
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

	    Query query = session.createQuery("SELECT r FROM Ride r WHERE r.depart = :depart AND r.arrival = :arrival AND r.date = :date");
	    query.setParameter("depart", from);
	    query.setParameter("arrival", to);
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

	    Query query = session.createQuery("SELECT DISTINCT r.date FROM Ride r WHERE r.depart = :depart AND r.arrival = :arrival AND r.date BETWEEN :startDate AND :endDate");
	    query.setParameter("depart", from);
	    query.setParameter("arrival", to);
	    query.setParameter("startDate", firstDayMonthDate);
	    query.setParameter("endDate", lastDayMonthDate);

	    List<Date> dates = query.list();
	    session.getTransaction().commit();
	    return dates;
	}
	
	public Car createCar(String email, String licensePlate, int seats, String brand, String model) throws CarAlreadyExistsException {
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction();
	    try {
        Driver driver = (Driver) session.get(Driver.class, email);
        
        if((Car) session.get(Car.class, licensePlate) != null) {
        	throw new CarAlreadyExistsException("Car already exists!");
        }
        Car car = driver.addCar(licensePlate, seats, brand, model);
        
        session.persist(driver);
        session.getTransaction().commit();
        return car;
	    } catch (Exception e) {
	        if (session.getTransaction().isActive()) {
	            session.getTransaction().rollback();
	        }
	        throw e;
	    }
	}
	
	public List<Car> getCarsByDriver(String email){
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction();
	    Query query = session.createQuery("FROM Car r WHERE r.driver.email = :email");
	    query.setParameter("email", email);

	    List<Car> cars = query.list();
        session.getTransaction().commit();
        return cars;
	}
	
	public Alarm createAlarm(String email,String depart, String arrival, Date date) throws AlarmAlreadyExistsException {
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction();
	    
	    try {
        Traveler traveler = (Traveler) session.get(Traveler.class, email);
	    Query query = session.createQuery("FROM Alarm a WHERE a.depart = :depart and a.arrival = :arrival and a.date = :date and a.traveler.email = :email and a.active = true");
	    query.setParameter("depart", depart);
	    query.setParameter("arrival", arrival);
	    query.setParameter("date", date);
	    query.setParameter("email", email);
	    List<Alarm> alarmList = query.list();
	    if(!alarmList.isEmpty()) {
	    	throw new AlarmAlreadyExistsException("Alarm already exists");
	    }
		Alarm alarm = traveler.addAlarm(depart, arrival, date);
        session.persist(traveler);
        session.getTransaction().commit();
        return alarm;
	    } catch (Exception e) {
	        if (session.getTransaction().isActive()) {
	            session.getTransaction().rollback();
	        }
	        throw e;
	    }
	}
	
	public List<Alarm> getRideMatchingAlarms(String email){
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction();
        Traveler traveler = (Traveler) session.get(Traveler.class, email);
        
        List<Alarm> alarmList = traveler.getActiveAlarms();
        List<Alarm> matchingAlarms = new ArrayList<>();
        Date currentDate = new Date();
        
        for (Alarm alarm : alarmList) {
            Query query = session.createQuery("FROM Ride r WHERE r.depart = :depart AND r.arrival = :arrival AND r.date = :date AND r.date > :currentDate");
            query.setParameter("depart", alarm.getDepart());
            query.setParameter("arrival", alarm.getArrival());
            query.setParameter("date", alarm.getDate());
            query.setParameter("currentDate", currentDate);

            Ride ride = (Ride) query.setMaxResults(1).uniqueResult();

            if (ride != null) {
                matchingAlarms.add(alarm);
            }
        }
        
        session.getTransaction().commit();
        return matchingAlarms;
	}
	
	public boolean disableAlarm(String email, int id) {
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction();
	    try {
        Traveler traveler = (Traveler) session.get(Traveler.class, email);
        traveler.disableAlarm(id);
        session.persist(traveler);
        System.out.println("Alarm disabled");
        session.getTransaction().commit();
        return true;
	    } catch (Exception e) {
	        if (session.getTransaction().isActive()) {
	            session.getTransaction().rollback();
	        }
	        return false;
	    }
	}
}
