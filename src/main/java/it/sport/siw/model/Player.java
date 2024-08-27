package it.sport.siw.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Player {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	//private Long id;
	private String name;
	private String surname;
	private String city;
	private String dateOfBirth;
	private String role;
	private String startCarreer;
	private String stopCarreer;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getStartCarreer() {
		return startCarreer;
	}
	public void setStartCarreer(String startCarreer) {
		this.startCarreer = startCarreer;
	}
	public String getStopCarreer() {
		return stopCarreer;
	}
	public void setStopCarreer(String stopCarreer) {
		this.stopCarreer = stopCarreer;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(city, dateOfBirth, name, role, startCarreer, stopCarreer, surname);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		return Objects.equals(city, other.city) && Objects.equals(dateOfBirth, other.dateOfBirth)
				&& Objects.equals(name, other.name) && Objects.equals(role, other.role)
				&& Objects.equals(startCarreer, other.startCarreer) && Objects.equals(stopCarreer, other.stopCarreer)
				&& Objects.equals(surname, other.surname);
	}
}
