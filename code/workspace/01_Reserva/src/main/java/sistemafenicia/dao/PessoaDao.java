package sistemafenicia.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import sistemafenicia.model.Pessoa;

@Repository
public class PessoaDao extends Dao<Pessoa> {

	static final Logger log = Logger.getLogger(PessoaDao.class);

	public PessoaDao() {
		log.debug("Iniciou - PessoaDao");
		setClazz(Pessoa.class);
	}

}
