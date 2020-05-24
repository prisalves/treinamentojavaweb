package sistemafenicia.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sistemafenicia.model.Usuario;

@Repository("usuarioDao")
@Transactional
public class UsuarioDao extends Dao<Usuario> {

	static final Logger log = Logger.getLogger(UsuarioDao.class);

	public UsuarioDao() {
		log.debug("Iniciou - UsuarioDao");
		setClazz(Usuario.class);
	}

	/*@Override
	public void save(Usuario entity) throws SQLException, Exception {

		try {

			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			entity.setSenha(passwordEncoder.encode(entity.getSenha()));

			super.save(entity);

		} catch (Exception e) {
			log.debug(e.getMessage());
		}

	}*/



}
