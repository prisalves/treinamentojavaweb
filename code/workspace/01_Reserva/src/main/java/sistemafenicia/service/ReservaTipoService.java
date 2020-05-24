package sistemafenicia.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import sistemafenicia.dao.ReservaTipoDao;
import sistemafenicia.model.ReservaTipo;

@ManagedBean
@RequestScoped
@Service
@Controller
public class ReservaTipoService extends basicService<ReservaTipo> {

	static final Logger log = Logger.getLogger(ReservaTipoService.class);

	@Autowired
	ReservaTipoDao reservaTipoDao;
	
	@PostConstruct
	public void init() {
		super.setDao(reservaTipoDao);
		super.setClazz(ReservaTipo.class);
	}
	
	public List<ReservaTipo> getLista(){
		List<ReservaTipo> lista = new ArrayList<ReservaTipo>();
		try {
			
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return lista;
	}

}
