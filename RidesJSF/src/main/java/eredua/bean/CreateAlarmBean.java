package eredua.bean;

import java.util.Date;
import java.util.Objects;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import businessLogicRides24.BLFacade;
import eredua.LoggedUser;
import eredua.domeinua.Alarm;
import eredua.domeinua.Car;
import exceptions.AlarmAlreadyExistsException;
import exceptions.CarAlreadyExistsException;

@ManagedBean(name = "createAlarms")
@SessionScoped
public class CreateAlarmBean {
	
	private BLFacade facade = FacadeBean.getBusinessLogic();

	private String depart;
	private String arrival;
	private Date date;
	
    @ManagedProperty(value = "#{loggedUser}")
    private LoggedUser loggedUser;
	
	public CreateAlarmBean(){
		
	}

	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public LoggedUser getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(LoggedUser loggedUser) {
		this.loggedUser = loggedUser;
	}

	public boolean validateInput() {
		
		if (this.depart == null || this.depart.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null, 
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Please write a valid Depart City."));
			return false;
		}

		if (this.arrival == null || this.arrival.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null, 
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Please write a valid Arrival City."));
			return false;
		}
		if (this.date == null || new Date().compareTo(this.date) > 0) {
			FacesContext.getCurrentInstance().addMessage(null, 
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Please write a Date (After today)."));
			return false;
		}
		return true;
	}
	
	public void createAlarm() {
		if(!validateInput()) return;
		Alarm alarm = null;
		try {
			alarm = facade.createAlarm(loggedUser.getUser().getEmail(), depart, arrival, date);
		} catch (AlarmAlreadyExistsException e) {
			FacesContext.getCurrentInstance().addMessage(null, 
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e.getMessage()));
			e.printStackTrace();
			return;
		}	
		if(alarm == null) {
			FacesContext.getCurrentInstance().addMessage(null, 
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Alarm could not be created."));
			return;
		}
		
		FacesContext.getCurrentInstance().addMessage(null, 
			    new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "Alarm created succesfully!"));
	}
	
}
