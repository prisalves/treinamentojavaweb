package com.cap.servlet;

import com.cap.model.Funcionario;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.SQLIntegrityConstraintViolationException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cap.conf.DatabaseConnection;

@WebServlet("/CadastraFuncionario")
public class CadastraFuncionario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CadastraFuncionario() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			Funcionario funcionario = new Funcionario();
			funcionario.setNome("Fabio");
			funcionario.setCelular(32988725882L);
			funcionario.setEmail("fabio.nascimento@capgemini.com");
			funcionario.setClienteAlocado("NEXT");
			funcionario.setFuncao("Analista de Sistemas Sr");
			funcionario.setUsuarioGithub("bimnascimento@gmail.com");

			Connection con = DatabaseConnection.initializeDatabase();

			PreparedStatement pst = con.prepareStatement("INSERT INTO funcionarios (`celular`, `cliente_alocado`, `email`, `funcao`, `nome`, `usuario_github`) VALUES (?,?,?,?,?,?) ");
			pst.setString(1, funcionario.getCelular().toString());
			pst.setString(2, funcionario.getClienteAlocado());
			pst.setString(3, funcionario.getEmail());
			pst.setString(4, funcionario.getFuncao());
			pst.setString(5, funcionario.getNome());
			pst.setString(6, funcionario.getUsuarioGithub());
			pst.executeUpdate();
			pst.close();
			con.close();

			response.getWriter().append("<b>Salvo!!</b>");
			

		} catch (SQLIntegrityConstraintViolationException e) {
			response.getWriter().append("<b>Erro de validacao:</b> " + e.getMessage());
		} catch (Exception e) {
			response.getWriter().append("<b>Erro:</b> " + e.getCause() + " - " + e.getMessage());
		}
	}

}
