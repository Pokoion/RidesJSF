package eredua.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import businessLogic.BLFacade;
import domain.Ride;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

@ManagedBean(name = "queryRides")
@SessionScoped
public class QueryRidesBean {

	private BLFacade facade = FacadeBean.getBusinessLogic();

	private Date data = new Date();
	private String selectedDepartCity = "";
	private String selectedArrivalCity = "";
	private List<String> departCities = new ArrayList<>();
	private List<String> arrivalCities = new ArrayList<>();
	private List<Ride> rides = new ArrayList<>();

	public QueryRidesBean() {

	}

	public List<Ride> getRides() {
		return rides;
	}

	public void setRides(List<Ride> rides) {
		this.rides = rides;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getSelectedDepartCity() {
		return selectedDepartCity;
	}

	public void setSelectedDepartCity(String selectedDepartCity) {
		arrivalCities.clear();
		selectedArrivalCity = "";
		this.selectedDepartCity = selectedDepartCity;
	}

	public String getSelectedArrivalCity() {
		return selectedArrivalCity;
	}

	public void setSelectedArrivalCity(String selectedArrivalCity) {
		this.selectedArrivalCity = selectedArrivalCity;
	}

	public List<String> getDepartCities() {
		return facade.getDepartCities();
	}

	public void setDepartCities(List<String> departCities) {
		this.departCities = departCities;
	}

	public List<String> getArrivalCities() {
		if (selectedDepartCity == null || selectedDepartCity.isEmpty()) {
			return new ArrayList<>();
		}
		return facade.getDestinationCities(selectedDepartCity);
	}

	public void setArrivalCities(List<String> arrivalCities) {
		this.arrivalCities = arrivalCities;
	}

	public void updateRides() {
		System.out.println(selectedDepartCity);
		System.out.println(selectedArrivalCity);
		System.out.println(data);
		// Validación de campos
		if (selectedDepartCity == null || selectedDepartCity.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error: Please select a Depart City."));
			this.rides.clear();
			return;
		}

		if (selectedArrivalCity == null || selectedArrivalCity.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Error: Please select an Arrival City."));
			this.rides.clear();
			return;
		}

		if (data == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error: Please select a valid Date."));
			this.rides.clear();
			return;
		}

		fetchRides();

	}

	public void fetchRides() {
		List<Date> monthRides = facade.getThisMonthDatesWithRides(selectedDepartCity, selectedArrivalCity, data);

		this.rides.clear();
		for (Date date : monthRides) {
			this.rides.addAll(facade.getRides(selectedDepartCity, selectedArrivalCity, date));
		}
	}

}
