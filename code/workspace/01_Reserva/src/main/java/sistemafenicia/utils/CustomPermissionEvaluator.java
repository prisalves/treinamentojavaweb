package sistemafenicia.utils;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

	static final Logger log = Logger.getLogger(CustomPermissionEvaluator.class);
	
	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		try {
			if (authentication == null || !authentication.isAuthenticated()) {
	        	MessagesUtil.error("Usuario nao autenticado.");
	            return false;
	        } else {
	        	for(GrantedAuthority authority: authentication.getAuthorities()) {
	        		if(authority.getAuthority().equals(permission)) {
						return true;
					}
	        	}
	        	MessagesUtil.error("Você não tem permissão <br/> para executar esta acão.");
	            return false;
	        }
		} catch (AccessDeniedException adex) {
			log.debug("ERRO PERMISSAO 1");
			log.debug(adex);
		} catch (RuntimeException e) {
			//log.debug("ERRO PERMISSAO 2");
		}
		return false;	
	}

	@Override
	 public boolean hasPermission(Authentication authentication,
	   Serializable targetId, String targetType, Object permission) {
	    //throw new RuntimeException("Id and Class permissions are not supperted by this application");
		log.debug("Evaluating expression using hasPermission signature #2");
		return false;
	 }

}
