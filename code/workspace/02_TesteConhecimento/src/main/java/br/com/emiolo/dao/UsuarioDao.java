package br.com.emiolo.dao;

import org.hibernate.criterion.Restrictions;

import br.com.emiolo.model.Usuario;

public class UsuarioDao extends Dao<Long, Usuario> {
    
	public UsuarioDao() {
		setClazz(Usuario.class);
    }
	
	public Usuario login(String login, String senha) {
		Usuario usuario = null;
		try {
			usuario = (Usuario) this.getSession().createCriteria(clazz)
					.add(Restrictions.eq("login", login))
					.add(Restrictions.eq("senha", senha))
					.setMaxResults(1)
					.uniqueResult();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return usuario;
	}
	
}
