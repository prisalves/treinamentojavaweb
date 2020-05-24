package sistemafenicia.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sistemafenicia.dao.BancoDao;
import sistemafenicia.model.Banco;

@Component("componentConverterBanco")
public class ConverterBanco implements Converter {
	
	Banco banco;
	
	@Autowired
	BancoDao bancoDao;
	
	@Override
	@Transactional
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
			try {
				if (value != null && !value.equals("")) {
					banco = (Banco) bancoDao.findById(Integer.valueOf(value));
					return banco;
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
		if (value instanceof Banco) {
			banco = (Banco) value;
			return String.valueOf(banco.getIdBanco());
		}
		return "";
	}

}
