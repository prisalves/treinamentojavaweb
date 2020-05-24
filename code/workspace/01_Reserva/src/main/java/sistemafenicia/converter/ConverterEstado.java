package sistemafenicia.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sistemafenicia.dao.EstadoDao;
import sistemafenicia.model.Estado;

@Component("componentConverterEstado")
public class ConverterEstado implements Converter {
	
	Estado estado;
	
	@Autowired
	EstadoDao estadoDao;
	
	@Override
	@Transactional
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
			try {
				if (value != null && !value.equals("")) {
					estado = (Estado) estadoDao.findById(Integer.valueOf(value));
					return estado;
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
		if (value instanceof Estado) {
			estado = (Estado) value;
			return String.valueOf(estado.getIdEstado());
		}
		return "";
	}

}
