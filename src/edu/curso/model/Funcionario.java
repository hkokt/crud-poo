package edu.curso.model;

public class Funcionario {
	private long id;
	private String nome;
	private String cargo;
	private String cpf;
	private String email;
	private String celular;

	@Override
	public String toString() {
		return "id: " + id + "\\n nome: " + nome + "\\n cargo: " + cargo + "\\n cpf: " + cpf + "\\n email: " + email
				+ "\\n celular: " + celular;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

}
