package it.sport.siw.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Amministratore {
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 private Long id;
		@NotBlank
		private String name;
		@NotBlank
		private String surname;
		@NotBlank
		private String city;
		@NotNull
		private LocalDate dateOfBirth;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
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
		public LocalDate getDateOfBirth() {
			return dateOfBirth;
		}
		public void setDateOfBirth(LocalDate dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
		}
		@Override
		public int hashCode() {
			return Objects.hash(city, dateOfBirth, id, name, surname);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Amministratore other = (Amministratore) obj;
			return Objects.equals(city, other.city) && Objects.equals(dateOfBirth, other.dateOfBirth)
					&& Objects.equals(id, other.id) && Objects.equals(name, other.name)
					&& Objects.equals(surname, other.surname);
		}
		
		
	 
}
