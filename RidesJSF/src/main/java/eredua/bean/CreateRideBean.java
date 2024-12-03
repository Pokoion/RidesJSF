package eredua.bean;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import businessLogicRides24.BLFacade;
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
		return true;
	}
	
	public void createRide(){
		
		if (!validateInput()) return;
		Ride ride = null;
		try {
			ride = facade.createRide(departCity, arrivalCity, data, seats, price, "jonoolea@gmail.com");
		} catch (RideMustBeLaterThanTodayException e) {
			FacesContext.getCurrentInstance().addMessage(null, 
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Ride must be later than today."));
			e.printStackTrace();
			return;
		} catch (RideAlreadyExistException e) {
			FacesContext.getCurrentInstance().addMessage(null, 
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Ride already exists."));
			e.printStackTrace();
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
