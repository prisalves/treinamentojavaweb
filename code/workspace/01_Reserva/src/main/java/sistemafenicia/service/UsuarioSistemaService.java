package sistemafenicia.service;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import sistemafenicia.dao.UsuarioDao;
import sistemafenicia.model.Usuario;

@ManagedBean
@ApplicationScoped
@Service("usuarioSistemaService")
@Controller
public class UsuarioSistemaService implements Serializable {
	
	private static final long serialVersionUID = 1L;

	static final Logger log = Logger.getLogger(UsuarioSistemaService.class);
	
	@Autowired
	UsuarioDao usuarioDao;
	
	public static String getUsername() {
		String login = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth!=null && auth.isAuthenticated()){
			login = auth.getName();
		}
		return login;
	}
	
	public String getUsernameSistema(){
		return getUsername();
	}
	
	@Transactional
	public Usuario getUsuarioSistema(){
		Usuario usuario = new Usuario();
		try {
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        if(auth.isAuthenticated()){
	        	String login = auth.getName();
	        	usuario = (Usuario) usuarioDao.findByField("login",login);
	        }
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return usuario;
    }
	
	@RequestMapping(value = "/login/{error}", method = RequestMethod.GET)
	public final String displayLoginform(Model model, @PathVariable final String error) {
	    model.addAttribute("error", error);
	    return "login";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public ModelAndView doLogin(ModelAndView model,
			ModelMap map,
			HttpServletRequest req,
			HttpServletResponse res) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		String status = req.getParameter("status");
		
		if(status!=null){
			if(status.equals("error")) {
				modelAndView.addObject("msg", "Usuário ou senha inválidos.");
			}
			if(status.equals("expired")) {
				modelAndView.addObject("msg", "Sessão expirada!");
			}
			if(status.equals("session")) {
				modelAndView.addObject("msg", "erro na autenticacao!");
			}
		}
	    return modelAndView;
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView doLogout(ModelAndView model,
			ModelMap map,
			HttpServletRequest req,
			HttpServletResponse res) {

		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.isAuthenticated()){
			String name = auth.getName();
			map.addAttribute("username", name);
			modelAndView.setViewName("redirect:/j_spring_security_logout");
		}else{
			modelAndView.setViewName("redirect:/login.xhtml");
		}
		return modelAndView;
	}

}
