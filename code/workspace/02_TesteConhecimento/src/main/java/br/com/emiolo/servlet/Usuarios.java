package br.com.emiolo.servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.emiolo.model.Usuario;
import br.com.emiolo.service.UsuarioService;

@WebServlet("usuarios")
public class Usuarios extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(Usuarios.class);


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		log.debug("POST USUARIOS");
		
		ObjectMapper mapper = new ObjectMapper();
		Usuario usuario = mapper.readValue(req.getInputStream(), Usuario.class);
		UsuarioService usuarioService = new UsuarioService();
		log.debug(usuario);
		JsonObject jsonResponse = new JsonObject();
		try {
			usuarioService.save(usuario);
			jsonResponse.addProperty("msg", "SUCESSO");
		} catch (Exception e) {
			jsonResponse.addProperty("msg", "ERRO");
		}
		String json = new Gson().toJson(jsonResponse);
        res.setContentType("application/json");
        res.getWriter().write(json);
        
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		log.debug("GET USUARIOS");
		
		UsuarioService usuarioService = new UsuarioService();
		String json = new Gson().toJson(usuarioService.findAll());
        res.setContentType("application/json");
        res.getWriter().write(json);
		
		
	}
	

}
