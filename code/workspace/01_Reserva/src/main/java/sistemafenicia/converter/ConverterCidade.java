package sistemafenicia.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sistemafenicia.dao.CidadeDao;
import sistemafenicia.model.Cidade;

@Component("componentConverterCidade")
public class ConverterCidade implements Converter {
	
	Cidade cidade;
	
	@Autowired
	CidadeDao cidadeDao;
	
	@Override
	@Transactional
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
			try {
				if (value != null && !value.equals("")) {
					cidade = (Cidade) cidadeDao.findById(Integer.valueOf(value));
					return cidade;
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
		if (value instanceof Cidade) {
			cidade = (Cidade) value;
			return String.valueOf(cidade.getIdCidade());
		}
		return "";
	}

}
