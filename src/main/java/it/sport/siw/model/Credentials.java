package it.sport.siw.model;

import jakarta.persistence.*;
import jakarta.persistence.Id;

@Entity
@Table(name = "credentials")
public class Credentials {
	public static final String ADMIN_ROLE = "ADMIN";
	public static final String PRESIDENT_ROLE = "PRESIDENT";
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	private String username;
	private String password;
	private String role;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	// Helper method per determinare se è un amministratore o presidente
    public boolean isAdmin() {
        return ADMIN_ROLE.equals(this.role);
    }

    public boolean isPresident() {
        return PRESIDENT_ROLE.equals(this.role);
    }
	
	
}