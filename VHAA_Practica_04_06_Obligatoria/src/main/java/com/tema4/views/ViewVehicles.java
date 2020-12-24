package com.tema4.views;

import java.util.Scanner;

import com.tema4.controller.VehiclesController;

public class ViewVehicles {
	private static VehiclesController vehiclesController;
	private static Scanner teclado;

	private ViewVehicles() {
		teclado = new Scanner(System.in);
		vehiclesController = VehiclesController.getInstance();
		menuOpciones();
	}

	public static ViewVehicles getInstance() {
		return new ViewVehicles();
	}

	private void menuOpciones() {
		boolean terminado = false;

		do {
			showMenu();
			String opcion = teclado.nextLine();
			switch (opcion) {
			case "1":
				ViewConsultaVehicles.getInstance();
				break;
			case "2":
				vehiclesController.create();
				break;
			case "3":
				vehiclesController.update();
				break;
			case "4":
				vehiclesController.delete();
				break;
			case "0":
				System.out.println("Fin de la ejecución...");
				terminado = true;
				break;
			default:
				System.out.println("Ingrese una opción válida");
				break;
			}
		} while (!terminado);
	}

	private void showMenu() {
		System.out.println("Opciones de Vehículos: ");
		System.out.println("1- Consultas");
		System.out.println("2- Insertar vehículo");
		System.out.println("3- Modificar vehículo");
		System.out.println("4- Borrar vehículo");
		System.out.println("0- Volver ");
	}

}
