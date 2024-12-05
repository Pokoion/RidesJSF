package eredua.domeinua;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Car {
	
	@Id 
	private String licensePlate;
	private int seats;
	private String brand;
	private String model;
	
	@ManyToOne(targetEntity=Driver.class, fetch=FetchType.EAGER)
	private Driver driver;
	
	@OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Ride> rides;
	
	public Car() {
		
	}

	public Car(String licensePlate, int seats, String brand, String model, Driver driver) {
		this.licensePlate = licensePlate;
		this.seats = seats;
		this.brand = brand;
		this.model = model;
		this.driver = driver;
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

	@Override
	public int hashCode() {
		return Objects.hash(licensePlate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Car other = (Car) obj;
		return Objects.equals(licensePlate, other.licensePlate);
	}

	@Override
	public String toString() {
		return licensePlate + " - " + brand + " - " + model;
	}
	
	
	
	
}
