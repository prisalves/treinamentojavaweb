package sistemafenicia.utils;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;

@ManagedBean
@SessionScoped
public class LanguageBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	static final Logger log = Logger.getLogger(LanguageBean.class);
	
	private static final String BUNDLE_NAME = "messages";

	private String localeCode;
	
	private Locale language;
	
    private String locale = FacesContext.getCurrentInstance().getViewRoot().getLocale().toString();
	
	/*public static String getMessage(MessageEnum messageEnum, Locale locale, Object... args ){
        ResourceBundle resourceBundle = null;
        
        if(locale!=null)
            resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
        else
            resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
        
        String message = null;
        if(resourceBundle!=null){
            message = resourceBundle.getString(messageEnum.getProp());
            if(message!=null && args.length > 0)
                message = MessageFormat.format(message, args);
        }            
        return message;
    }*/

	private static Map<String,Object> countries;
	static{
		countries = new LinkedHashMap<String,Object>();
		countries.put("English", new Locale("en", "US")); //label, value
		countries.put("Portugues", new Locale("pt", "BR"));
	}

	public Map<String, Object> getCountriesInMap() {
		return countries;
	}


	public String getLocaleCode() {
		return localeCode;
	}


	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	//value change event listener
	public void countryLocaleCodeChanged(ValueChangeEvent e){
		
		//log.debug("teste");

		String newLocaleValue;
		newLocaleValue = e.getNewValue().toString();
		//newLocaleValue = localeCode;

		for (Map.Entry<String, Object> entry : countries.entrySet()) {
			if(entry.getValue().toString().equals(newLocaleValue)){
				FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale)entry.getValue());
				this.locale = FacesContext.getCurrentInstance().getViewRoot().getLocale().toString();
			}
		}
		
		//localeCode = "";
	}
	
	public String getLanguage() {
		//return "pt_BR";
        return this.locale;
    }
	


}