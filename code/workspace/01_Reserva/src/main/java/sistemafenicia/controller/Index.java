package sistemafenicia.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@RequestScoped
@ManagedBean
@Controller
public class Index {

	private static final Logger log = Logger.getLogger(Index.class);
	
	private String msg = "Hello World JSF!";

	@PostConstruct
	public void init(){
		log.info("Bean executado!");
	}

	public String getMessage(){
		return this.msg;
	}

	@RequestMapping("/ok")
	public String index() {
		this.msg = "Hello OK!";
		log.info("Executando a lógica com Spring MVC!");
	    return "index";
	}
	
	/*@RequestMapping("/index/{error}")
	public ModelAndView indexError(@PathVariable final String error) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/index.xhtml");
		modelAndView.addObject("msg",error);
		return modelAndView;
	}*/
	
	@RequestMapping("/accessDenied")
	public ModelAndView accessDenied(Model model, ModelMap map,RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView();
		log.debug(model);
		modelAndView.setViewName("redirect:/index.xhtml");
		modelAndView.addObject("acesso",false);
		return modelAndView;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}



}