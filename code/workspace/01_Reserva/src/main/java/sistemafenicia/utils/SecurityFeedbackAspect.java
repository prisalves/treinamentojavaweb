package sistemafenicia.utils;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class SecurityFeedbackAspect {
	
	static final Logger log = Logger.getLogger(SecurityFeedbackAspect.class);
	
    public Object handleSecuredAnnotations(final ProceedingJoinPoint pjp) throws Throwable {
    	log.debug(pjp);
        try {
            return pjp.proceed();
         } catch (AccessDeniedException e) {
            // log + set feedback for user here
         }
		return pjp;
    }
}