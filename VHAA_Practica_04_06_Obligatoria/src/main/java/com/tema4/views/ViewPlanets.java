package com.tema4.views;

import java.util.Scanner;

import com.tema4.controller.PlanetsController;

public class ViewPlanets {
	private static Scanner teclado;
	private static PlanetsController planetsController;

	private ViewPlanets() {
		planetsController = PlanetsController.getIntance();
		menuOpciones();
	}

	public static ViewPlanets getInstance() {
		return new ViewPlanets();
	}

	private void menuOpciones() {
		boolean terminado = false;
		teclado = new Scanner(System.in);

		do {
			showMenu();
			String opcion = teclado.nextLine();
			switch (opcion) {
			case "1":
				ViewConsultaPlanets.getInstance();
				break;
			case "2":
				planetsController.create();
				break;
			case "3":
				planetsController.update();
				break;
			case "4":
				planetsController.delete();
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
		System.out.println("Opciones de Planeta: ");
		System.out.println("1- Consultas");
		System.out.println("2- Insertar planeta");
		System.out.println("3- Modificar planeta");
		System.out.println("4- Borrar planeta");
		System.out.println("0- Volver ");
	}

}
