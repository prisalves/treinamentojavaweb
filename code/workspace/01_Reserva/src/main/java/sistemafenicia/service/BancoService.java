package sistemafenicia.service;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import sistemafenicia.dao.BancoDao;
import sistemafenicia.model.Banco;

@ManagedBean
@RequestScoped
@Service
@Controller
@RequestMapping(value={"/banco", "/banco/**"})
public class BancoService extends basicService<Banco> {

	static final Logger log = Logger.getLogger(BancoService.class);

	@Autowired
	BancoDao bancoDao;
	
	Banco banco;

	@PostConstruct
	public void init() {
		super.setDao(bancoDao);
		super.setClazz(Banco.class);
	}
	
	public Banco getBanco() {
		return banco;
	}
	
	public void setBanco(Banco banco) {
		this.banco = banco;
	}
		


}
