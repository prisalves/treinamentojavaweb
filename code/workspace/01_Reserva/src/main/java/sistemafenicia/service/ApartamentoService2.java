/*package sistemafenicia.service;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sistemafenicia.dao.ApartamentoDao;
import sistemafenicia.model.Apartamento;
import sistemafenicia.model.ApartamentoItem;
import sistemafenicia.utils.FacesUtil;
import sistemafenicia.utils.MessagesUtil;

@ManagedBean
@RequestScoped
@Service
@Controller
public class ApartamentoService2 extends basicService<Apartamento> {

	static final Logger log = Logger.getLogger(ApartamentoService2.class);

	@Autowired
	ApartamentoDao apartamentoDao;
	
	Apartamento apartamento;
	ApartamentoItem apartamentoItem;
	Set<ApartamentoItem> addItens;

	@PostConstruct
	public void init() {
		super.setDao(apartamentoDao);
		super.setClazz(Apartamento.class);
	}

	@RequestMapping(value = "/apartamentos", method=RequestMethod.GET)
	public String page() {
		return "administracao/apartamento/apartamentos";
	}

	@PreAuthorize("hasPermission(#user, 'SAVE') or hasPermission(#user, 'UPDATE')")
	@Transactional
	public void saveOrUpdate() {
		try {
			String title = null;
			if(apartamento.getIdApartamento()!=null){
				update(apartamento);
				title = "atualizado";
			}else{
				save(apartamento);
				title = "adicionado";
			}
			MessagesUtil.sucess("O registro \""+apartamento+"\" foi "+title+".");
			apartamento = new Apartamento();
			FacesUtil.fecharModal("modalApartamento");
			RequestContext.getCurrentInstance().update("formDataTable");
			RequestContext.getCurrentInstance().update("formApartamento");
			RequestContext.getCurrentInstance().update("formReserva:apartamento");
		}catch(Exception e){
			log.debug(e.getMessage());
			log.debug("ERRO SALVAR");
	    }
	}

	@PreAuthorize("hasPermission(#user, 'UPDATE')")
	@Transactional
	public void alterar(Apartamento entity){
		try {
			apartamento = new Apartamento();
			apartamento = (Apartamento) apartamentoDao.findById(entity.getIdApartamento());
			addItens = apartamento.getApartamentoItens();
			apartamentoItem = new ApartamentoItem();
			RequestContext.getCurrentInstance().update("formApartamento");
			FacesUtil.abrirModal("modalApartamento");
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}

	@Override
	@PreAuthorize("hasPermission(#user, 'DELETE')")
	public void delete(Apartamento entity) {
		super.delete(entity);
		RequestContext.getCurrentInstance().update("formDataTable");
	}

	@PreAuthorize("hasPermission(#user, 'SAVE')")
	public void add(){
		apartamento = new Apartamento();
		apartamentoItem = new ApartamentoItem();
		addItens = new HashSet<ApartamentoItem>();
		RequestContext.getCurrentInstance().update("formApartamento");
		FacesUtil.abrirModal("modalApartamento");
	}
	
	public void addItem(){
		if(apartamentoItem.getNome()!=null && apartamentoItem.getQuantidade()!=null){
			if(apartamento.getApartamentoItens().size()==0){
				addItens.add(apartamentoItem);
				apartamento.setApartamentoItens(addItens);
				apartamentoItem = new ApartamentoItem();
				return;
			}
			boolean itemExiste = false;
			for (ApartamentoItem apItem : apartamento.getApartamentoItens()) {
				if(	apItem.getNome().equals(apartamentoItem.getNome()) 
						&& apItem.getQuantidade()==apartamentoItem.getQuantidade() ){
					itemExiste = true;
				}
			}
			if(!itemExiste){
				addItens.add(apartamentoItem);
				apartamento.setApartamentoItens(addItens);
			}
			
			apartamentoItem = new ApartamentoItem();
		}
	}
	public void removeItem(ApartamentoItem entity){
		apartamento.getApartamentoItens().remove(entity);
	}
	

	public Apartamento getApartamento() {
		return apartamento;
	}
	public void setApartamento(Apartamento apartamento) {
		this.apartamento = apartamento;
	}
	
	public ApartamentoItem getApartamentoItem() {
		return apartamentoItem;
	}
	public void setApartamentoItem(ApartamentoItem apartamentoItem) {
		this.apartamentoItem = apartamentoItem;
	}
	


}

*/
