package com.tema4.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.tema4.constants.KConstants;

/**
 * Clase: Manejador de sessiones
 * 
 * @author Victor Hugo Aguilar Aguilar
 *
 */
public class HandlerBD {

	public SessionFactory sessionFactory = null;
	public Session session = null;

	HandlerBD() {
	}

	public static HandlerBD getInstance() {
		switchLog(false);
		return new HandlerBD();
	}

	public void tearUp() {
		try {
			sessionFactory = new Configuration().configure().buildSessionFactory();
			session = sessionFactory.openSession();
		} catch (Exception e) {
			System.err.println(KConstants.Common.FAIL_CONECTION);
		}

	}

	public void tearDown() {
		try {
			session.close();
		} catch (Exception e) {
			System.err.println(KConstants.Common.FAIL_CONECTION);
		}
	}

	public void comprobarSession() {
		if (session.isConnected()) {
			System.out.println("Sesión abierta");
		} else {
			System.out.println("Fallo en la sesión");
		}
	}

	public static void switchLog(boolean activo) {
		if (!activo) {
			@SuppressWarnings("unused")
			org.jboss.logging.Logger looger = org.jboss.logging.Logger.getLogger("org.hibernate");
			java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.OFF);
		}
	}

}
