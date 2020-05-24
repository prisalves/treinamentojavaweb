package sistemafenicia.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sistemafenicia.dao.ContaDao;
import sistemafenicia.model.Conta;

@Component("componentConverterConta")
public class ConverterConta implements Converter {
	
	Conta conta;
	
	@Autowired
	ContaDao contaDao;
	
	@Override
	@Transactional
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
			try {
				if (value != null && !value.equals("")) {
					conta = (Conta) contaDao.findById(Integer.valueOf(value));
					return conta;
				}
			} catch (NumberFormatException e) {
				e.getMessage();
			} catch (Exception e) {
				e.getMessage();
			}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value instanceof Conta) {
			conta = (Conta) value;
			return String.valueOf(conta.getIdConta());
		}
		return "";
	}

}
