package com.tema4.controller;

import java.util.ArrayList;
import java.util.List;

import com.tema4.constants.KConstants;
import com.tema4.models.Planets;
import com.tema4.models.Starships;
import com.tema4.services.HandlerBD;
import com.tema4.utils.Utiles;

@SuppressWarnings("unchecked")
public class ConsultaPlanetsController {

	static HandlerBD manejador = null;

	ConsultaPlanetsController() {
	}

	public static void getPlanetByName(String name) {
		if (name.trim().isEmpty()) {
			System.out.println(KConstants.Common.NOT_DATA_FIND);
			return;
		}
		manejador = HandlerBD.getInstance();
		manejador.tearUp();
		buscarPlanetName(name);
		manejador.tearDown();
	}

	private static void buscarPlanetName(String name) {
		final String sqlQuery = "FROM Planets where UPPER(name) like '%" + name.toUpperCase() + "%'";
		try {
			List<Planets> consultaPlanets = manejador.session.createQuery(sqlQuery).list();
			if (consultaPlanets.isEmpty()) {
				System.out.println(KConstants.Common.NOT_REGISTER + " para el nombre " + name);
			} else {
				consultaPlanets.stream().forEach(Planets::imprimeRegistroDetallado);
			}
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
	}

	public static void showRegisters() {
		mostrarTodos(getRegisters());
	}

	private static void mostrarTodos(List<Planets> consultaPlanets) {
		if (consultaPlanets.isEmpty()) {
			System.out.println(KConstants.Common.NOT_REGISTER);
		} else {
			Utiles.getCabeceraRegistroPlanet();
			consultaPlanets.stream().forEach(Planets::imprimeRegistro);
		}
	}

	public static List<Planets> getRegisters() {
		manejador = HandlerBD.getInstance();
		manejador.tearUp();
		List<Planets> consultaPlanets = obtenerRegistros();
		manejador.tearDown();
		return consultaPlanets;
	}

	private static List<Planets> obtenerRegistros() {
		final String sqlQuery = "FROM Planets ";
		List<Planets> consultaPlanets = new ArrayList<Planets>();
		try {
			consultaPlanets = manejador.session.createQuery(sqlQuery).list();
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
		return consultaPlanets;
	}

	public static void main(String[] args) {
		showRegisters();
		getPlanetByName("nabo");
	}

}
