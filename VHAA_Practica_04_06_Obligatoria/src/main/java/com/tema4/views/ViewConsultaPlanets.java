package com.tema4.views;

import java.util.Scanner;

import com.tema4.controller.PlanetsController;

public class ViewConsultaPlanets {
	private static Scanner teclado;
	private static PlanetsController planetsController;

	private ViewConsultaPlanets() {
		planetsController =  PlanetsController.getIntance();
		menuOpciones();
	}

	public static ViewConsultaPlanets getInstance() {
		return new ViewConsultaPlanets();
	}

	private void menuOpciones() {
		boolean terminado = false;
		teclado = new Scanner(System.in);

		do {
			showMenu();
			String opcion = teclado.nextLine();
			switch (opcion) {
			case "1":
				System.out.println("Ingrese el nombre del Planeta a buscar");
				String name = teclado.nextLine();
				planetsController.findbyName(name);
				presioneTeclaParaContinuar();
				break;
			case "2":
				planetsController.showRegisters();
				presioneTeclaParaContinuar();
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
		System.out.println("Opciones de Consulta de Planetas: ");
		System.out.println("1- Buscar planeta por nombre");
		System.out.println("2- Mostrar todos");
		System.out.println("0- Volver ");
	}

	static void presioneTeclaParaContinuar() {
		System.out.println("presione una tecla para continuar....");
		teclado.nextLine();
	}

}
