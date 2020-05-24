package br.com.emiolo.service;

import br.com.emiolo.dao.UsuarioDao;
import br.com.emiolo.model.Usuario;

public class UsuarioService extends Service<Long, Usuario> {
	
	UsuarioDao usuarioDao = new UsuarioDao();

	public UsuarioService() {
		super.setDao(usuarioDao);
		super.setClazz(Usuario.class);
	}
	
	public Usuario login(String login, String senha) {
		Usuario usuario = null;
		try {
			usuario = usuarioDao.login(login, senha);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return usuario;
	}
	
	

}
