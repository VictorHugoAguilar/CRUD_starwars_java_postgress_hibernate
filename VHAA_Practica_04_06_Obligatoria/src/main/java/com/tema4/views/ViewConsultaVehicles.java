package com.tema4.views;

import java.util.Scanner;

import com.tema4.controller.VehiclesController;

public class ViewConsultaVehicles {
	private static Scanner teclado;
	private static VehiclesController vehiclesController;

	private ViewConsultaVehicles() {
		vehiclesController = VehiclesController.getInstance();
		menuOpciones();
	}

	public static ViewConsultaVehicles getInstance() {
		return new ViewConsultaVehicles();
	}

	private void menuOpciones() {
		boolean terminado = false;
		teclado = new Scanner(System.in);

		do {
			showMenu();
			String opcion = teclado.nextLine();
			switch (opcion) {
			case "1":
				System.out.println("Ingrese el nombre del Vehículo a buscar");
				String name = teclado.nextLine();
				vehiclesController.findbyName(name);
				presioneTeclaParaContinuar();
				break;
			case "2":
				vehiclesController.showRegisters();
				presioneTeclaParaContinuar();
				break;
			case "0":
				System.out.println("Fin de la ejecución...");
				terminado = true;
				break;
			default:
				System.out.println("Ingrese una opcion válida");
				break;
			}
		} while (!terminado);
	}

	private void showMenu() {
		System.out.println("Opciones de Consulta de Vehículos: ");
		System.out.println("1- Buscar Vehículo por nombre");
		System.out.println("2- Mostrar Todas");
		System.out.println("0- Salir ");
	}

	static void presioneTeclaParaContinuar() {
		System.out.println("presione una tecla para continuar....");
		teclado.nextLine();
	}

}
