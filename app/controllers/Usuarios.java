package controllers;



import java.util.List;

import javax.swing.JOptionPane;

import models.Rol;
import models.Usuario;
import play.mvc.Before;
import play.mvc.Controller;

public class Usuarios extends Controller {

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
    	if(usuario.rol.descripcion.equals("postulante")){
    	pedirActivacion();
    	}else{
    		flash.put("confirmacion", "Usuario creado exitosamente. Recuerde que para usar esta cuenta deberá activarla desde el email: "+usuario.email);
    		administracionPersonal();
    	}
    	
    }
    
    
    //Mensaje de informacion al usuario pidiendole que active su cuenta
    public static void pedirActivacion(){
    	render();    	
    }
    
    //Mensaje de informacion diciendole al usuario que su cuenta ha sido activada
    public static void mensajeActivacion(){
    	render();
    }
    
    //Formulario de creacion de cuentas para el personal de posgrados
    public static void crearCuentas(){
    	List<Rol>roles=Rol.findAll();
    	render(roles);
    }

    
    //Proceso para guardar un Nuevo Usuario que sea persoal de posgrados Validando el email y password
    public static void guardarPersonal(String nombre, String apellido, String email,Long idRol) {
    	
    	flash.put("nombre", nombre);
		flash.put("apellido", apellido);
		flash.put("email", email);	
		Rol rol=Rol.findById(idRol);
		flash.put("descripcion_rol",rol.descripcion);


			
			//buscando si el usuario ya ha sido registrado anteriormente
			Usuario user = Usuario.find("byEmail",email).first(); 
			
			if (user == null){
				String identificador=generarIdentificador(email, apellido, nombre);
			
				String password=email;
				
				Usuario u=new Usuario(nombre,apellido,email,password,identificador,false,rol);				
				u.save();	
				enviarConfirmacion(u);			
			}else{
		
			flash.put("existente", "Error: El email ya esta en uso.");
			administracionPersonal();
			}
	
			   	
    }  
    
    
    
    
    //Metodo que muestra el listado de usuarios para administrar
    public static void administracionPersonal(){
    	List<Usuario>	usuarios=Usuario.find("rol.descripcion<>'postulante' order by id desc").fetch();
    	List<Rol>		roles=Rol.find("descripcion<>'administrador' and descripcion<>'postulante'").fetch();
    	
    	render(usuarios,roles);
    }
    
    
    //Proceso para realizar cambios en la informacion del personal de posgrados relacionado con el proceso de admision
    public static void actualizarPersonal(Long idUsuario, String nombre, String apellido, String email, Long idRol, Boolean estado ){
    
    	Usuario usuario=Usuario.findById(idUsuario);
    	usuario.nombre=nombre;
    	usuario.apellido=apellido;
    	usuario.email=email;
     	Rol rol=Rol.findById(idRol);
    	usuario.rol=rol;
    	
    	if(estado!=null){
    		usuario.activo=true;
    	}else{
    		usuario.activo=false;
    	}
    	usuario.save();
    	
    	administracionPersonal();
    	
    	
    	
    }


       
}
