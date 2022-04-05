package br.com.lp3superior.modelo;

public class Client {

	private Long idClient;
	private String name;
	private String cpf;
	
	public Client() {
		super();
	}

	public Client(String name, String cpf) {
		super();
		this.name = name;
		this.cpf = cpf;
	}
	
	public Long getIdClient() {
		return idClient;
	}
	public void setIdClient(Long idClient) {
		this.idClient = idClient;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}	
	
	
}
