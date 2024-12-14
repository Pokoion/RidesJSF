package eredua.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import businessLogicRides24.BLFacade;
import eredua.domeinua.Ride;

@ManagedBean(name = "ariketa")
@SessionScoped
public class AriketaBean {

	private String selectedDepartCity = "";
	private List<String> departCities = new ArrayList<>();
	private List<Ride> rides = new ArrayList<>();
	
	private BLFacade facade = FacadeBean.getBusinessLogic();
	
	public AriketaBean() {
		
	}

	public String getSelectedDepartCity() {
		return selectedDepartCity;
	}

	public void setSelectedDepartCity(String selectedDepartCity) {
		this.selectedDepartCity = selectedDepartCity;
	}

	public List<String> getDepartCities() {
		return facade.getDepartCities();
	}

	public void setDepartCities(List<String> departCities) {
		this.departCities = departCities;
	}

	public List<Ride> getRides() {
		return facade.getRidesAriketa(this.selectedDepartCity);
	}

	public void setRides(List<Ride> rides) {
		this.rides = rides;
	}
	
	
}
