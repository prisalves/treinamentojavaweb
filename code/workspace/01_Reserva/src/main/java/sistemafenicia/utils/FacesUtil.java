package sistemafenicia.utils;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import sistemafenicia.model.Usuario;
import sistemafenicia.service.UsuarioSistemaService;

@ManagedBean
@SessionScoped
public class FacesUtil implements Serializable {
	
	private static final long serialVersionUID = 1L;
	static final Logger log = Logger.getLogger(FacesUtil.class);
	
	public static Usuario getUsuario(){
		Usuario usuario = new Usuario();
		UsuarioSistemaService usuarioSistemaService = new UsuarioSistemaService();
		/*try {
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        if(auth.isAuthenticated()){
	        	usuario = (Usuario) usuarioDao.findByField("login", auth.getName());
	        	FacesUtil.setObjectInSession("usuario",usuario);
	    		FacesUtil.setInSession("usuarioLogin", usuario.getLogin());
	    		FacesUtil.setInSession("usuarioEmail", usuario.getEmail());
	        }
		} catch (Exception e) {
			log.debug(e.getMessage());
		}*/
		usuario = usuarioSistemaService.getUsuarioSistema();
        return usuario;
	}

	public static void adicionarMensagem(Severity tipo, String msg) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(tipo, msg, msg));
	}

	public static Object getRequestAttribute(String name) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		return request.getAttribute(name);
	}

	public static String getParameterUrl(String parameter) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return facesContext.getExternalContext().getRequestParameterMap().get(parameter);
	}

	public static String getServerName () {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		return request.getServerName();
	}

	public static Object getObjectInSession (String name) {
		try {
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			HttpSession session = request.getSession(true);
			return session.getAttribute(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void setObjectInSession (String name, Object obj) {
		try {
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			HttpSession session = request.getSession(true);
			session.setAttribute(name, obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setInSession (String name, String value) {
		try {
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			HttpSession session = request.getSession(true);
			session.setAttribute(name, value);
			//System.out.println("ADICIONOU NA SESSAO: " + name + " / " + value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getInSession (String name) {
		try {
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			HttpSession session = request.getSession(true);
			//System.out.println("RETORNOU DA SESSAO: " + name + " / " + (String) session.getAttribute(name));
			return (String) session.getAttribute(name);
		} catch (Exception e) {
			//e.printStackTrace();
			e.getMessage();
		}
		return null;
	}

	public static void externalRedirect (String page) {
		try {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			externalContext.redirect(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void internalRedirect (String page) {
		try {
			HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			externalContext.redirect( req.getContextPath() + page );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void redirect(String page) {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			NavigationHandler navHandler = context.getApplication().getNavigationHandler();
			navHandler.handleNavigation(context, null, page);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void removeManagedBeanInSession(String managedBean) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(managedBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String semAcento(String str) {
		try {
			str = str.replaceAll("-","_");
			str = str.replaceAll(" ","_");
			str = Normalizer.normalize(str, Normalizer.Form.NFD);
			//str = str.replaceAll("[^\\p{ASCII}]", "");
			str = str.replaceAll("[^a-zA-Z0-9_.]", "");
			return str;
		} catch (Exception e) {
			e.getMessage();
		}
		return null;
	}

	public static void abrirModal(String modal){
		RequestContext.getCurrentInstance().execute("$('#"+modal+"').appendTo('body').modal('show')");
		//RequestContext.getCurrentInstance().execute("$('.noui').removeClass('ui-button')");
		//RequestContext.getCurrentInstance().execute("$('.modal').css({ opacity: 0.5 })");
		//RequestContext.getCurrentInstance().execute("$('.modal:nth-last-of-type(2)').css({ opacity:1 })");
	}
	public static void fecharModal(String modal){
		RequestContext.getCurrentInstance().execute("$('#"+modal+"').appendTo('body').modal('hide')");
		//RequestContext.getCurrentInstance().execute("$('.modal').css({ opacity:1 })");
		//RequestContext.getCurrentInstance().execute("$('.modal:nth-last-of-type(3)').css({ opacity:1 })");
	}
	
	public static void atualizaTable(String table){
		RequestContext.getCurrentInstance().update(table);
		if(findComponent("formDataTable:dataTableBlock")){
			RequestContext.getCurrentInstance().execute("$('#dataTable').DataTable(settings);");
			//RequestContext.getCurrentInstance().execute("$('.switch')['bootstrapSwitch']();");
			/*RequestContext.getCurrentInstance().execute("setTimeout(function(){"
					+ "	$('.selectpicker,.dataTables_length .form-control').selectpicker({style:'btn-info',size:4,width:'auto'}); "
					+ "	$('#dataTable_length').change(function(){$('.switch')['bootstrapSwitch']();}); "
					+ "	$('#dataTable_length select').hide(); "
					+ "	$('#dataTable thead').on('click','tr',function(){$('.switch')['bootstrapSwitch']();}); "
					+ "},300);");*/
		}
	}

	public static String getMsg(String messageId) {    
		FacesContext facesContext = FacesContext.getCurrentInstance();    
		String msg = "";    
		Locale locale = facesContext.getViewRoot().getLocale();   
		ResourceBundle bundle;
		bundle = ResourceBundle.getBundle("messages", locale); 
		//bundle = facesContext.getApplication().getResourceBundle(facesContext, "messages");
		try {    
			msg = bundle.getString(messageId);    
		} catch (Exception e) {    
		}    
		return msg;    
	}  

	public static void setLocale(String lang) {
		Locale locale = new Locale("pt","BR");
		if(lang.equals("en")){
			locale = new Locale("en","US");
		}
		locale = new Locale("en","US");
		FacesContext context = FacesContext.getCurrentInstance();  
		context.getViewRoot().setLocale(locale);
	}

	public static double diferencaEmDias(Date dataInicial, Date dataFinal){
		double result = 0;
		long diferenca = dataFinal.getTime() - dataInicial.getTime();
		double diferencaEmDias = (diferenca /1000) / 60 / 60 /24; //resultado é diferença entre as datas em dias
		long horasRestantes = (diferenca /1000) / 60 / 60 %24; //calcula as horas restantes
		result = diferencaEmDias + (horasRestantes /24d); //transforma as horas restantes em fração de dias
		return result;
	}

	public static double diferencaEmHoras(Date dataInicial, Date dataFinal){
		double result = 0;
		long diferenca = dataFinal.getTime() - dataInicial.getTime();
		long diferencaEmHoras = (diferenca /1000) / 60 / 60;
		long minutosRestantes = (diferenca / 1000)/60 %60;
		double horasRestantes = minutosRestantes / 60d;
		result = diferencaEmHoras + (horasRestantes);
		return result;
	}

	public static double diferencaEmMinutos(Date dataInicial, Date dataFinal){
		double result = 0;
		long diferenca = dataFinal.getTime() - dataInicial.getTime();
		double diferencaEmMinutos = (diferenca /1000) / 60; //resultado é diferença entre as datas em minutos
		long segundosRestantes = (diferenca / 1000)%60; //calcula os segundos restantes
		result = diferencaEmMinutos + (segundosRestantes /60d); //transforma os segundos restantes em minutos
		return result;
	}

	/*public static <T> List<T> sortString(String propertyName, Set<T> lista){
		List<T> listaArray = new ArrayList<T>(lista);
		Collections.sort(listaArray, new BeanComparator("propertyName"));
		return listaArray;
	}*/
	
	private static Date dataHora = new Date();
	private static DateFormat dateFormat;
	public static String converteData(Date data, Integer i) {
		if(i==null)
			i=0;
		switch (i) {
			case 0: dateFormat = new SimpleDateFormat("dd'/'MM'/'yyyy"); break;
			case 1: dateFormat = new SimpleDateFormat("dd'/'MM'/'yyyy HH:mm:ss"); break;
			case 2: dateFormat = new SimpleDateFormat("dd ' de ' MMMM ' de ' yyyy"); break;
			case 3: dateFormat = new SimpleDateFormat("EEEE, dd ' de ' MMMM ' de ' yyyy HH:mm:ss"); break;
			case 4: dateFormat = new SimpleDateFormat("EEEE, dd ' de ' MMMM ' de ' yyyy"); break;
		}
		if(data==null)
			data = new Date();
		return dateFormat.format(data);
	}
	
	public static Date formataData(String data,Integer i){
		Date dataReturn = null;
		if(i==null)
			i=0;
		switch (i) {
			case 0: dateFormat = new SimpleDateFormat("dd/MM/yyyy"); break;
			case 1: dateFormat = new SimpleDateFormat("dd/MM/yyyy 00:00:00"); break;
			case 2: dateFormat = new SimpleDateFormat("MM-dd-yyyy"); break;
			case 3: dateFormat = new SimpleDateFormat("MM-dd-yyyy 00:00:00"); break;
			case 4:	dateFormat = new SimpleDateFormat("yyyy-MM-dd"); break;
			case 5:	dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00"); break;
		}
		try {
			if(data==null)
				data = converteData(null,null);
			dataReturn = (Date) dateFormat.parse(data);
		} catch (ParseException e) {
			log.debug(e.getMessage());
		}
		return dataReturn;
	}
	
	public static boolean findComponent(String id) {
		/*UIComponent where = FacesContext.getCurrentInstance().getViewRoot();
		List<UIComponent> childrenList = where.getChildren();
		if (childrenList == null || childrenList.isEmpty()) {
			return false;
		}
		for (UIComponent child : childrenList) {
			UIComponent result = null;
			result = findComponent(id, child);
			if(result != null) {
				return true;
			}
		}
		return false;*/
		
		UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
		UIComponent component = view.findComponent(id);
		if(component==null)
			return false;
		else
			return true;
	}
	
	public static String substring(Object texto, Integer limite){
		String newTexto = null;
		if(texto.toString().length()>limite){
			newTexto = texto.toString().substring(0,limite);
			newTexto = newTexto+"...";
		}else{
			newTexto = texto.toString();
		}
		return newTexto;
	}
	
	public static boolean hasRole(String role) {
        // get security context from thread local
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null)
            return false;
        Authentication authentication = context.getAuthentication();
        if (authentication == null)
            return false;
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if (role.equals(auth.getAuthority()))
                return true;
        }
        return false;
    }


}