package eredua.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import businessLogicRides24.BLFacade;
import eredua.LoggedUser;
import eredua.domeinua.Car;
import eredua.domeinua.Ride;
import exceptions.CarAlreadyExistsException;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

@ManagedBean(name = "createCars")
@SessionScoped
public class CreateCarBean {

	private BLFacade facade = FacadeBean.getBusinessLogic();
	
	private String licensePlate = "";
	private Integer seats = 0;
	private String brand = "";
	private String model = "";
	
    @ManagedProperty(value = "#{loggedUser}")
    private LoggedUser loggedUser;
	
	public CreateCarBean() {
	}
	
	public String getLicensePlate() {
		return licensePlate;
	}
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}
	public int getSeats() {
		return seats;
	}
	public void setSeats(int seats) {
		this.seats = seats;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public LoggedUser getLoggedUser() {
		return loggedUser;
	}
	public void setLoggedUser(LoggedUser loggedUser) {
		this.loggedUser = loggedUser;
	}
	public void setSeats(Integer seats) {
		this.seats = seats;
	}

	public boolean validateInput() {
		String licensePlateRegex = "^\\d{4}[A-Z]{3}$";
		
		if (this.licensePlate == null || this.licensePlate.isEmpty() || !this.licensePlate.matches(licensePlateRegex)) {
			FacesContext.getCurrentInstance().addMessage(null, 
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Please write a valid License Plate."));
			return false;
		}

		if (this.seats == null || this.seats <= 0) {
			FacesContext.getCurrentInstance().addMessage(null, 
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Please write a Seats Number."));
			return false;
		}
		if (this.brand == null || this.brand.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null, 
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Please write a valid Brand."));
			return false;
		}
		if (this.model == null || this.model.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null, 
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Please write a valid Model."));
			return false;
		}
		return true;
	}
	
	public void createCar() {
		if(!validateInput()) return;
		Car car = null;
		try {
			car = facade.createCar(loggedUser.getUser().getEmail() ,licensePlate, seats, brand, model);
		} catch (CarAlreadyExistsException e) {
			FacesContext.getCurrentInstance().addMessage(null, 
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Car already exists."));
			e.printStackTrace();
			return;
		}
		
		if(car == null) {
			FacesContext.getCurrentInstance().addMessage(null, 
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Car could not be created."));
			return;
		}
		
		FacesContext.getCurrentInstance().addMessage(null, 
			    new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "Car created succesfully!"));
	}
	
}
