package sistemafenicia.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import sistemafenicia.model.PessoaFisica;

@Repository
public class PessoaFisicaDao extends Dao<PessoaFisica> {

	static final Logger log = Logger.getLogger(PessoaFisicaDao.class);

	public PessoaFisicaDao() {
		log.debug("Iniciou - PessoaFisicaDao");
		setClazz(PessoaFisica.class);
	}

}
