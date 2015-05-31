package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;

import java.util.*;

@Entity
public class Usuario extends Model {
    public String nombre;
    public String apellido;
    public String email;
    public String password;
    public String identificador;
    public boolean activo;
    public Rol rol;
    
	public Usuario(String nombre, String apellido, String email,String password, String identificador, boolean activo, Rol rol) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.password = password;
		this.identificador = identificador;
		this.activo = activo;
		this.rol = rol;
	}

	

    
    
}
