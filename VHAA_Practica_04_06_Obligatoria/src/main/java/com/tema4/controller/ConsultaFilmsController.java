package com.tema4.controller;

import java.util.List;

import com.tema4.constants.KConstants;
import com.tema4.models.Films;
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

	public static void getAllRegister() {
		manejador = HandlerBD.getInstance();
		manejador.tearUp();
		mostrarTodos();
		manejador.tearDown();
	}

	private static void mostrarTodos() {
		final String sqlQuery = "FROM Films";
		try {
			List<Films> consultaFilms = manejador.session.createQuery(sqlQuery).list();

			if (consultaFilms.isEmpty()) {
				System.out.println(KConstants.Common.NOT_REGISTER);
			} else {
				Utiles.getCabeceraRegistroVehicles();
				consultaFilms.stream().forEach(Films::imprimeRegistro);
			}
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
	}

}
