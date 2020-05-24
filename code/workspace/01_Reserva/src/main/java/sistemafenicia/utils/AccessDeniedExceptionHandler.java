package sistemafenicia.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;


public class AccessDeniedExceptionHandler extends AccessDeniedHandlerImpl { 
	private static Logger logger = Logger.getLogger(AccessDeniedExceptionHandler.class); 
	private static final String LOG_TEMPLATE = "AccessDeniedHandlerApp:  User attempted to access a resource for which they do not have permission.  User %s attempted to access %s"; 
	private String accessDeniedUrl ; 

	public AccessDeniedExceptionHandler(){
		System.out.println("Creating AccessDeniedExceptionHandler");
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,  AccessDeniedException _exception) throws IOException, ServletException{         
		setErrorPage(accessDeniedUrl);
		super.handle(request, response, _exception); 
	} 

	/**
	 * This method is set from applicationContext_security.xml  
	 * @param errorPage
	 */
	public void setAccessDeniedUrl (String accessDeniedUrl ) {        
		if ((accessDeniedUrl  != null) && !accessDeniedUrl .startsWith("/")) 
		{             
			throw new IllegalArgumentException("errorPage must begin with '/'");         
		}
		this.accessDeniedUrl  = accessDeniedUrl ;     
	} 

}