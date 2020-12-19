package com.tema4.controller;

import java.util.ArrayList;
import java.util.List;

import com.tema4.constants.KConstants;
import com.tema4.models.Films;
import com.tema4.models.Vehicles;
import com.tema4.services.HandlerBD;
import com.tema4.utils.Utiles;

@SuppressWarnings("unchecked")
public class ConsultaFilmsController {

	static HandlerBD manejador = null;

	ConsultaFilmsController() {
	}

	public static void getStarshipsByName(String title) {
		if (title.trim().isEmpty()) {
			System.out.println(KConstants.Common.NOT_DATA_FIND);
			return;
		}
		manejador = HandlerBD.getInstance();
		manejador.tearUp();
		buscarStarshipsName(title);
		manejador.tearDown();
	}

	private static void buscarStarshipsName(String title) {
		final String sqlQuery = "FROM Films where UPPER(title) like '%" + title.toUpperCase() + "%'";
		try {
			List<Films> consultaFilms = manejador.session.createQuery(sqlQuery).list();
			if (consultaFilms.isEmpty()) {
				System.out.println(KConstants.Common.NOT_REGISTER + " para el nombre " + title);
			} else {
				consultaFilms.stream().forEach(Films::imprimeRegistroDetallado);
			}
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
	}

	public static void showRegisters() {
		mostrarTodos(getRegisters());
	}

	private static void mostrarTodos(List<Films> consultaFilms) {
		if (consultaFilms.isEmpty()) {
			System.out.println(KConstants.Common.NOT_REGISTER);
		} else {
			Utiles.getCabeceraRegistroFilms();
			consultaFilms.stream().forEach(Films::imprimeRegistro);
		}
	}

	public static List<Films> getRegisters() {
		manejador = HandlerBD.getInstance();
		manejador.tearUp();
		List<Films> consultaFilms = obtenerRegistros();
		manejador.tearDown();
		return consultaFilms;
	}

	private static List<Films> obtenerRegistros() {
		final String sqlQuery = "FROM Films";
		List<Films> consultaFilms = new ArrayList<Films>();
		try {
			consultaFilms = manejador.session.createQuery(sqlQuery).list();
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
		return consultaFilms;
	}

}
