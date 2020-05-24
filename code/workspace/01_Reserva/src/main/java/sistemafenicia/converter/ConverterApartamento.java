package sistemafenicia.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sistemafenicia.dao.ApartamentoDao;
import sistemafenicia.model.Apartamento;

@Component("componentConverterApartamento")
public class ConverterApartamento implements Converter {
	
	Apartamento apartamento;
	
	@Autowired
	ApartamentoDao apartamentoDao;
	
	@Override
	@Transactional
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
			try {
				if (value != null && !value.equals("")) {
					apartamento = (Apartamento) apartamentoDao.findById(Integer.valueOf(value));
					return apartamento;
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
		if (value instanceof Apartamento) {
			apartamento = (Apartamento) value;
			return String.valueOf(apartamento.getIdApartamento());
		}
		return "";
	}

}
