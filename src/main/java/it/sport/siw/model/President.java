package it.sport.siw.model;

import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class President {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	//private Long id;
	private String name;
	private String surname;
	private String cf;
	private String city;
	private String dateOfBirth;
	
	
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
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
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
	
	
	@Override
	public int hashCode() {
		return Objects.hash(cf, city, dateOfBirth, name, surname);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		President other = (President) obj;
		return Objects.equals(cf, other.cf) && Objects.equals(city, other.city)
				&& Objects.equals(dateOfBirth, other.dateOfBirth) && Objects.equals(name, other.name)
				&& Objects.equals(surname, other.surname);
	}
}
