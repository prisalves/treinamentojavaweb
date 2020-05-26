package com.cap.model;

public class Funcionario {
	
	private String nome;
	private String clienteAlocado;
	private String funcao;
	private Long celular;
	private String usuarioGithub;
	private String email;
	
	public Funcionario() { super(); }
	
	public Funcionario(String nome, Long celular) {
		super();
		this.nome = nome;
		this.celular = celular;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getClienteAlocado() {
		return clienteAlocado;
	}

	public void setClienteAlocado(String clienteAlocado) {
		this.clienteAlocado = clienteAlocado;
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	public Long getCelular() {
		return celular;
	}

	public void setCelular(Long celular) {
		this.celular = celular;
	}

	public String getUsuarioGithub() {
		return usuarioGithub;
	}

	public void setUsuarioGithub(String usuarioGithub) {
		this.usuarioGithub = usuarioGithub;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "<b>Nome:</b> " + nome + " - <b>Celular:</b> " + celular;
	}
	
	
	
}
