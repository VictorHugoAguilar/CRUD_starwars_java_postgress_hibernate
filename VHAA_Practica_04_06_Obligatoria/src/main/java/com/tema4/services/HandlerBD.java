package com.tema4.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HandlerBD {

	public SessionFactory sessionFactory = null;
	public Session session = null;

	HandlerBD() {
	}

	public static HandlerBD getInstance() {
		return new HandlerBD();
	}

	public void tearUp() {
		sessionFactory = new Configuration().configure().buildSessionFactory();
		session = sessionFactory.openSession();
	}

	public void tearDown() {
		session.close();
	}

	public void comprobarSession() {
		if (session.isConnected()) {
			System.out.println("Sesión abierta");
		} else {
			System.out.println("Fallo en la sesión");
		}
	}

}
