package sistemafenicia.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sistemafenicia.dao.PessoaDao;
import sistemafenicia.model.Pessoa;
import sistemafenicia.model.PessoaFisica;

@Component("componentConverterPessoa")
public class ConverterPessoa implements Converter {
	
	Pessoa pessoa;
	PessoaFisica pessoaFisica;
	
	@Autowired
	PessoaDao pessoaDao;
	
	@Override
	@Transactional
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
			try {
				if (value != null && !value.equals("")) {
					pessoa = (Pessoa) pessoaDao.findById(Integer.valueOf(value));
					return pessoa;
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
		if (value instanceof Pessoa) {
			pessoa = (Pessoa) value;
			return String.valueOf(pessoa.getIdPessoa());
		}
		return "";
	}

}
