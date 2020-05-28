package com.cap.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection; 
import java.sql.ResultSet;
import java.sql.Statement;

import com.cap.conf.DatabaseConnection;

public class InfoServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
    	writer.println("<html><head><title>Simple Web Application</title></head>");
		writer.println("<body><h1>Funcionarios</h1>");
		writer.println("<br/>");
		
		try { 
			
				Connection con = DatabaseConnection.initializeDatabase();
	            Statement st = con.createStatement();
				ResultSet result = st.executeQuery("SELECT * FROM funcionarios");
				int count = 0;
		        while(result.next()){
		        	count++;
		        	writer.println("Nome: " + result.getString("nome") + " / Celular: " + result.getString("celular") + "<br/>" );
		        }
	            st.close(); 
	            con.close();
	            
	            if(count == 0) {
	            	writer.println("Nenhum funcionario encontrado!");
	            }
            
		} catch (Exception e) { 
            writer.println(e.getMessage());
        } 
		
		writer.println("<br/>");
		writer.println("<br/> <a href=\"#\" onclick=\"history.go(-1)\">Voltar</a>");
		writer.println("</body></html>");
		writer.flush();
		
	}

}
