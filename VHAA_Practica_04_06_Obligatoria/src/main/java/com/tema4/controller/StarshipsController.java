package com.tema4.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.tema4.constants.KConstants;
import com.tema4.models.Starships;
import com.tema4.services.HandlerBD;
import com.tema4.utils.Utiles;

public class StarshipsController implements ICRUDController {
	private static StarshipsController startshipsController;
	private static HandlerBD manejador;
	private static Scanner teclado;

	StarshipsController() {
		teclado = new Scanner(System.in);
		manejador = HandlerBD.getInstance();
	}

	public static StarshipsController getInstance() {
		if (startshipsController == null) {
			startshipsController = new StarshipsController();
			return startshipsController;
		}
		return startshipsController;
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
		buscarStarshipsName(name);
		manejador.tearDown();
	}

	private static void buscarStarshipsName(String name) {
		final String sqlQuery = "FROM Starships where UPPER(name) like '%" + name.toUpperCase() + "%'";
		try {
			List<Starships> consultaStarships = manejador.session.createQuery(sqlQuery).list();
			if (consultaStarships.isEmpty()) {
				System.out.println(KConstants.Common.NOT_REGISTER + " para el nombre " + name);
			} else {
				consultaStarships.stream().forEach(Starships::imprimeRegistroDetallado);
			}
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
	}

	public void showRegisters() {
		mostrarTodos(getRegisters());
	}

	private static void mostrarTodos(List<Starships> consultaStarships) {
		if (consultaStarships.isEmpty()) {
			System.out.println(KConstants.Common.NOT_REGISTER);
		} else {
			Utiles.getCabeceraRegistroStarships();
			consultaStarships.stream().forEach(Starships::imprimeRegistro);
		}
	}

	public static List<Starships> getRegisters() {
		manejador = HandlerBD.getInstance();
		manejador.tearUp();
		List<Starships> consultaStarships = obtenerRegistros();
		manejador.tearDown();

		return consultaStarships;
	}

	private static List<Starships> obtenerRegistros() {
		final String sqlQuery = "FROM Starships";
		List<Starships> consultaStarships = new ArrayList<Starships>();
		try {
			consultaStarships = manejador.session.createQuery(sqlQuery).list();
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
		return consultaStarships;
	}

}
