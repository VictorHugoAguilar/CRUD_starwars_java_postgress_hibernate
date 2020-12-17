package com.tema4.controller;

import java.util.List;

import com.tema4.constants.KConstants;
import com.tema4.models.Vehicles;
import com.tema4.services.HandlerBD;
import com.tema4.utils.Utiles;

@SuppressWarnings("unchecked")
public class ConsultaVehiclesController {

	static HandlerBD manejador = null;

	ConsultaVehiclesController() {
	}

	public static void getVehiclesByName(String name) {
		if (name.trim().isEmpty()) {
			System.out.println(KConstants.Common.NOT_DATA_FIND);
			return;
		}
		manejador = HandlerBD.getInstance();
		manejador.tearUp();
		buscarVehicleName(name);
		manejador.tearDown();
	}

	private static void buscarVehicleName(String name) {
		final String sqlQuery = "FROM Vehicles where UPPER(name) like '%" + name.toUpperCase() + "%'";
		try {
			List<Vehicles> consultaVehicles = manejador.session.createQuery(sqlQuery).list();
			if (consultaVehicles.isEmpty()) {
				System.out.println(KConstants.Common.NOT_REGISTER + " para el nombre " + name);
			} else {
				consultaVehicles.stream().forEach(Vehicles::imprimeRegistroDetallado);
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
		final String sqlQuery = "FROM Vehicles";
		try {
			List<Vehicles> consultaVehicles = manejador.session.createQuery(sqlQuery).list();

			if (consultaVehicles.isEmpty()) {
				System.out.println(KConstants.Common.NOT_REGISTER);
			} else {
				Utiles.getCabeceraRegistroVehicles();
				consultaVehicles.stream().forEach(Vehicles::imprimeRegistro);
			}
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
	}

}
