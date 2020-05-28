package com.cap.filter;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogFilterImpl implements Filter {

    private FilterConfig filterConfig;
    private static final Logger log = Logger.getLogger(LogFilterImpl.class.getName());
    
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
        throws IOException, ServletException {
    	
    	HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
    	
    	// Dados
        StringBuffer urlPath = ((HttpServletRequest) req).getRequestURL();
        String url = "Url requisitada: " + urlPath.toString();
        
        // ----- PRINT

        // Inicio filter
        java.util.Date date = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:SS");
        System.out.println("Inicio do Filter: " + sdf.format(date));

        // Chamada ao servlet
        System.out.println(url);
        filterChain.doFilter(req, response);
        
        // Fim filter
        date = new Date();
        System.out.println("Fim do Filter: " + sdf.format(date));
        

    }

    public FilterConfig getFilterConfig() {
        return filterConfig;
    }

    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        log.info("-------------------------------------------------------------------------------------------------- ");
        log.info("Inicializado o filter");
    }

    public void destroy() {
    	log.info("-------------------------------------------------------------------------------------------------- ");
    	log.info("Removendo o filter");
    }

}