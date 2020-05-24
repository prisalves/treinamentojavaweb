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

@WebServlet("login")
public class Login extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(Login.class);
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		log.debug("POST LOGIN");
		
		ObjectMapper mapper = new ObjectMapper();
		Usuario usuario = mapper.readValue(req.getInputStream(), Usuario.class);
		UsuarioService usuarioService = new UsuarioService();
		usuario = usuarioService.login(usuario.getLogin(), usuario.getSenha());
		log.debug("Usuario: "+usuario);
		
		JsonObject jsonResponse = new JsonObject();
		if(usuario==null){
			jsonResponse.addProperty("msg", "Usuário e senha incorretos!");
		}else{
			jsonResponse.addProperty("msg", "SUCESSO");
			jsonResponse.addProperty("nome", usuario.getNome());
		}
		
		String json = new Gson().toJson(jsonResponse);
        res.setContentType("application/json");
        res.getWriter().write(json);
		
	}
	
	

}
