package sistemafenicia.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sistemafenicia.dao.PredioDao;
import sistemafenicia.model.Pessoa;
import sistemafenicia.model.Predio;

@Component("componentConverterPredio")
public class ConverterPredio implements Converter {
	
	Predio predio;
	
	@Autowired
	PredioDao predioDao;
	
	@Override
	@Transactional
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
			try {
				if (value != null && !value.equals("")) {
					predio = (Predio) predioDao.findById(Integer.valueOf(value));
					return predio;
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
		if (value instanceof Predio) {
			predio = (Predio) value;
			return String.valueOf(predio.getIdPredio());
		}
		return "";
	}

}
