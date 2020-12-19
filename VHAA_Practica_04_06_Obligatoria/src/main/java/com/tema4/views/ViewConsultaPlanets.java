package com.tema4.views;

import java.util.Scanner;

import com.tema4.controller.ConsultaPlanetsController;

public class ViewConsultaPlanets {
	public static Scanner teclado;

	private ViewConsultaPlanets() {
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
				ConsultaPlanetsController.getPlanetByName(name);
				presioneTeclaParaContinuar();
				break;
			case "2":
				ConsultaPlanetsController.showRegisters();
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
		System.out.println("Opciones de Consulta de Planetas: ");
		System.out.println("1- Buscar especie por nombre");
		System.out.println("2- Mostrar Todas");
		System.out.println("0- Salir ");
	}

	static void presioneTeclaParaContinuar() {
		System.out.println("presione una tecla para continuar....");
		teclado.nextLine();
	}

}
