package com.tema4.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.tema4.constants.KConstants;
import com.tema4.models.Planets;
import com.tema4.services.HandlerBD;
import com.tema4.utils.Utiles;

public class PlanetsController implements ICRUDController {
	private static PlanetsController planetsController;
	private static HandlerBD manejador;
	private static Scanner teclado;

	PlanetsController() {
		teclado = new Scanner(System.in);
		manejador = HandlerBD.getInstance();
	}

	public static PlanetsController getIntance() {
		if (planetsController == null) {
			planetsController = new PlanetsController();
			return planetsController;
		}
		return planetsController;
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	public void findbyName(String name) {
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

	public void showRegisters() {
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

}
