package controllers;

import models.Usuario;
import play.mvc.*;

public class Security extends Secure.Security {


    //Para autenticar Usuarios -> Falta cerificacion del estado de la cuenta activo o no
	public static boolean authenticate(String username, String password) {
		
		
		
		 Usuario user = Usuario.find("byEmail", username).first();

	      if( user != null){ 
	    	  
	    		  if( user.activo){
	    			  
	    			  if( user.password.equals(password)){	
		    		  	return true;
	    			  }else{
	    				  flash.put("clave","ERROR: Contraseña Incorrecta.");
	    				  return false;
	    			  }
	    			  
		    	  }else{
		    		  flash.put("inactivo","ERROR: Esta cuenta aún no ha sido Activada. Revise su correo electrónico.");
		    		  return false;
		    	  }
	      }else{	
	    	  flash.put("usuario","ERROR: Este email no esta registrado");
	    	  return false;
	      }
	}
    

}
