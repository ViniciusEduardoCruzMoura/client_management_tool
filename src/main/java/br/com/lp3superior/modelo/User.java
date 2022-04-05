package br.com.lp3superior.modelo;

public class User {
	private Long idUser;
	private String firtName;
	private String lastName;
	private String email;
	private String password;
	
	public Long getIdUser() {
		return idUser;
	}
	
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}
	
	public String getFirstName() {
		return firtName;
	}
	
	public void setFirstName(String firtName) {
		this.firtName = firtName;
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
