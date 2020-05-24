package br.com.emiolo.servlet;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("nasa")
public class nasa extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(nasa.class);


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		log.debug("POST NASA");
		
		ObjectMapper mapper = new ObjectMapper();
        TypeReference<Map<String,String>> typeRef = new TypeReference<Map<String,String>>() {};
        HashMap<String, String> result = mapper.readValue(req.getInputStream(), typeRef);
        
        String camera = "";
        if(!result.get("camera").isEmpty()){
        	camera = "&camera="+result.get("camera");
        }
        URL url = new URL(result.get("url")+"?api_key="+result.get("key")+camera+"&earth_date="+result.get("data"));

        URLConnection uc = url.openConnection();
        uc.setRequestProperty("X-Requested-With", "Curl");
        InputStreamReader inputStreamReader = new InputStreamReader(uc.getInputStream(), "UTF-8");
        
        JsonObject jsonResponse = new JsonObject();
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            for (String line; (line = reader.readLine()) != null;) {
                System.out.println(line);
                jsonResponse = (new JsonParser()).parse(line).getAsJsonObject();
            }
        }
        
        String json = new Gson().toJson(jsonResponse);
        res.setContentType("application/json");
        res.getWriter().write(json);
        
	}
	
	
	

}
