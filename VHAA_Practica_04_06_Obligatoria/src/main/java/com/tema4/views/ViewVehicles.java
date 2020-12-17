package com.tema4.views;

import java.util.Scanner;

public class ViewVehicles {

	public static Scanner teclado;

	private ViewVehicles() {
		menuOpciones();
	}

	public static ViewVehicles getInstance() {
		return new ViewVehicles();
	}

	private void menuOpciones() {
		boolean terminado = false;
		teclado = new Scanner(System.in);

		do {
			showMenu();
			String opcion = teclado.nextLine();
			switch (opcion) {
			case "1":
				ViewConsultaVehicles.getInstance();
				break;
			case "2":
				break;
			case "3":
				break;
			case "4":
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
		System.out.println("Opciones de Vehicles: ");
		System.out.println("1- Consultas");
		System.out.println("2- Insertar vehicle");
		System.out.println("3- Modificar vehicle");
		System.out.println("4- Borrar vehicle");
		System.out.println("0- Salir ");
	}

}
