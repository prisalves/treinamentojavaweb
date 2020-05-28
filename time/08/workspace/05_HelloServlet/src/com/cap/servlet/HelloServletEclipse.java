package com.cap.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cap.conf.DatabaseConnection;

public class HelloServletEclipse extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(HelloServletEclipse.class.getName());
	
	public void init(ServletConfig config) {
		log.info("-------------------------------------------------------------------------------------------------- ");
		log.info("Inicializado o Servlet");
    }
	
	public PrintWriter setRetorno(String msg, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
    	writer.println("<html><head><title>Simple Web Application</title></head><body>");
    	writer.println("<br/>");
		writer.println("<h2 style='color:red;'>" + msg + "</h2>");
		writer.println("<br/>");
		writer.println("<a href=\"#\" onclick=\"history.go(-1)\">Voltar</a>");
		writer.println("</body></html>");
		return writer;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try { 
			log.info("Metodo POST");
			String nome = request.getParameter("nome");
			int count = 0;
			if (Objects.nonNull(nome)) { 
				Connection con = DatabaseConnection.initializeDatabase();
	            Statement st = con.createStatement();
				ResultSet result = st.executeQuery("SELECT * FROM funcionarios where nome = '" + nome + "' ");
				while(result.next()){
		        	count++;
		        	request.setAttribute("nome", result.getString("nome"));
		        	request.setAttribute("celular", result.getString("celular"));
		        }
	            st.close(); 
	            con.close();
			}
			if(Objects.isNull(nome)) {
				response.setContentType("text/html");
				PrintWriter writer = setRetorno("Nome nao informado!", response);
				writer.flush();
			} else if(count == 0) {
				response.setContentType("text/html");
				PrintWriter writer = setRetorno("Funcionario nao encontrado!", response);
				writer.flush();
			} else {
				request.getRequestDispatcher("resultado.jsp").forward(request, response);
			}
		} catch (Exception e) { 
			response.getWriter().append("<b>Erro:</b> " + e.getCause() + " - " + e.getMessage());
        } 
	}
	
	
	
	public void destroy() {
		log.info("-------------------------------------------------------------------------------------------------- ");
		log.log(Level.WARNING, "Removendo o Servlet...");
    }
}
