package controllers;

import javax.swing.JOptionPane;

import models.Usuario;

import org.apache.commons.collections.set.CompositeSet.SetMutator;
import org.apache.commons.mail.EmailAttachment;

import play.Play;
import play.mvc.Mailer;

public class Mails extends Mailer {

	public static void confirmarRegistro(String email,String url){
		setSubject("Confirmacion de Registro");
		addRecipient(email);
		setFrom("clubsistemasutc@gmail.com");
		send(url);
		
	}
}
