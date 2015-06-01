package controllers;



import models.Rol;
import models.Usuario;
import play.mvc.Controller;

public class Usuarios extends Controller {

    
    //Vista para Registrar un Nuevo Usuario
    public static void registro() {    	
        render();
    }

    
    //Proceso para guardar un Nuevo Usuario Validando el email y password
    public static void guardarPostulante(String nombre, String apellido, String email,String password, String confirm_password) {
    	
    	flash.put("nombre", nombre);
		flash.put("apellido", apellido);
		flash.put("email", email);
		
		//verificando si los password proporcionados por el usuario coinciden
		if(password.equals(confirm_password)){
			
			//buscando si el usuario ya ha sido registrado anteriormente
			Usuario user = Usuario.find("byEmail",email).first(); 
			
			if (user == null){
				String identificador=generarIdentificador(email, apellido, nombre);
				Rol rol=Rol.findById(1L);//Asignando un rol al Usuario por defecto es POSTULANTE
				Usuario u=new Usuario(nombre,apellido,email,password,identificador,false,rol);
				u.save();	
				enviarConfirmacion(u);			
			}else{
		
			flash.put("existente", "Error: El email: <b>"+email+"</b>, ya esta en uso.");
			registro();
			}
	
		}else
		{					
			flash.error("Error: Las <b> contraseñas </b> ingresadas no coinciden.");
			registro();
		}  	   	
    }
    
    
    //Metodo que se invoca a traves del link que se envia al correo del usuario
    public static void activarCuenta(String identificador){
    	Usuario user=Usuario.find("byIdentificador",identificador).first();
    	user.activo=true;
    	user.save();	 
    	mensajeActivacion();
    }
    
    
    //Para generar un identificador que permita crear un link de activacion de la cuenta de usuario
    public static String generarIdentificador(String email,String apellido,String nombre){
		String identificador=email;		
		return identificador;
	}
    
    //Generando el link de activacion que se envia al correo del usuario 
    public static void enviarConfirmacion(Usuario usuario){
    	String url="http://localhost:9000/usuarios/activarCuenta?identificador="+usuario.identificador;
    	String email=usuario.email;
    	Mails.confirmarRegistro(email, url);
    	pedirActivacion();
    	
    }
    
    
    //Mensaje de informacion al usuario pidiendole que active su cuenta
    public static void pedirActivacion(){
    	render();    	
    }
    
    //Mensaje de informacion diciendole al usuario que su cuenta ha sido activada
    public static void mensajeActivacion(){
    	render();
    }
    
    
    
}
