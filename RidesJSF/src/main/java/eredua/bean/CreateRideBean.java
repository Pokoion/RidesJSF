package eredua.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import businessLogicRides24.BLFacade;
import eredua.LoggedUser;
import eredua.domeinua.Car;
import eredua.domeinua.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

@ManagedBean(name = "createRides")
@SessionScoped
public class CreateRideBean {
	
	private BLFacade facade = FacadeBean.getBusinessLogic();
	
    private Date data = new Date();
    private String departCity = "";
    private String arrivalCity = "";
    private Float price = (float) 0.0;
    private Integer seats = 0;
    private Car selectedCar;
    private List<Car> cars = new ArrayList<>();
    
    @ManagedProperty(value = "#{loggedUser}")
    private LoggedUser loggedUser;
	
	public LoggedUser getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(LoggedUser loggedUser) {
		this.loggedUser = loggedUser;
	}

	public CreateRideBean() {
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDepartCity() {
		return departCity;
	}

	public void setDepartCity(String departCity) {
		this.departCity = departCity;
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public Car getSelectedCar() {
		return selectedCar;
	}

	public void setSelectedCar(Car selectedCar) {
		this.selectedCar = selectedCar;
	}

	public List<Car> getCars() {
		return facade.getCarsByDriver(loggedUser.getUser().getEmail());
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public void setSeats(Integer seats) {
		this.seats = seats;
	}

	public boolean validateInput() {
		String regex = "^[A-Za-zÁÉÍÓÚáéíóúÜüÑñ]{3,}(?:[ '-][A-Za-zÁÉÍÓÚáéíóúÜüÑñ]+)*$"; // Valid city name regex, at least 3 letters
		if (this.departCity == null || this.departCity.isEmpty() || !this.departCity.matches(regex)) {
			FacesContext.getCurrentInstance().addMessage(null, 
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Please write a valid Depart City."));
			return false;
		}
		if (this.arrivalCity == null || this.arrivalCity.isEmpty() || !this.arrivalCity.matches(regex)) {
			FacesContext.getCurrentInstance().addMessage(null, 
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Please write a valid Arrival City."));
			return false;
		}
		if (this.price == null || this.price <= 0) {
			FacesContext.getCurrentInstance().addMessage(null, 
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Please write a valid price."));
			return false;
		}
		if (this.seats == null || this.seats <= 0) {
			FacesContext.getCurrentInstance().addMessage(null, 
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Please write a valid number of seats."));
			return false;
		}
		if (this.data == null) {
			FacesContext.getCurrentInstance().addMessage(null, 
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Please select a valid date."));
			return false;
		}
		if (this.selectedCar == null) {
			FacesContext.getCurrentInstance().addMessage(null, 
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Please select a valid Car."));
			return false;
		}
		return true;
	}
	
	public void createRide(){
		
		if (!validateInput()) return;
		Ride ride = null;
		String licensePlate = selectedCar.getLicensePlate();
		try {
			ride = facade.createRide(departCity, arrivalCity, data, seats, price, loggedUser.getUser().getEmail(), licensePlate);
		} catch (NullPointerException | IllegalArgumentException | RideAlreadyExistException | RideMustBeLaterThanTodayException e) {
			FacesContext.getCurrentInstance().addMessage(null, 
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e.getMessage()));
			return;
		}
		
		if(ride == null) {
			FacesContext.getCurrentInstance().addMessage(null, 
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Ride could not be created."));
			return;
		}
		
		FacesContext.getCurrentInstance().addMessage(null, 
			    new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "Ride created succesfully!"));
	}
	
}
