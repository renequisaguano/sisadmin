package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import com.sun.org.apache.xpath.internal.operations.Equals;

import models.*;

@With(Secure.class)
public class Application extends Controller {

	@Before
	public static void mostrarUsuario(){
		try{
			Usuario user = Usuario.find("byEmail", Security.connected()).first();
			//obteniendo datos del usuario que ha iniciado sesion
			if(Security.isConnected()) {
		        renderArgs.put("conectado", user); 
		    }
			}catch(Exception ex){
				
			}
	}
    public static void index() {
    	
    		render();
    	        
    }

}