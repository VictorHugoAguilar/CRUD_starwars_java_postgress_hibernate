package com.tema4.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.tema4.constants.KConstants;
import com.tema4.models.Vehicles;
import com.tema4.services.HandlerBD;
import com.tema4.utils.Utiles;

public class VehiclesController implements ICRUDController {
	private static VehiclesController vehiclesController;
	private static HandlerBD manejador;
	private static Scanner teclado;

	VehiclesController() {
		teclado = new Scanner(System.in);
		manejador = HandlerBD.getInstance();
	}

	public static VehiclesController getInstance() {
		if (vehiclesController == null) {
			vehiclesController = new VehiclesController();
			return vehiclesController;
		}
		return vehiclesController;
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

	public void showRegisters() {
		mostrarTodos(getRegisters());
	}

	private static void mostrarTodos(List<Vehicles> consultaVehicles) {
		if (consultaVehicles.isEmpty()) {
			System.out.println(KConstants.Common.NOT_REGISTER);
		} else {
			Utiles.getCabeceraRegistroVehicles();
			consultaVehicles.stream().forEach(Vehicles::imprimeRegistro);
		}
	}

	public static List<Vehicles> getRegisters() {
		manejador = HandlerBD.getInstance();
		manejador.tearUp();
		List<Vehicles> consultaVehicles = obtenerRegistros();
		manejador.tearDown();

		return consultaVehicles;
	}

	private static List<Vehicles> obtenerRegistros() {
		final String sqlQuery = "FROM Vehicles";
		List<Vehicles> consultaVehicles = new ArrayList<Vehicles>();
		try {
			consultaVehicles = manejador.session.createQuery(sqlQuery).list();
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
		return consultaVehicles;
	}

}
