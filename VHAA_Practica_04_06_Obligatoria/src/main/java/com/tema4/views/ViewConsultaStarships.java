package com.tema4.views;

import java.util.Scanner;

import com.tema4.controller.ConsultaStarshipsController;

public class ViewConsultaStarships {
	public static Scanner teclado;

	private ViewConsultaStarships() {
		menuOpciones();
	}

	public static ViewConsultaStarships getInstance() {
		return new ViewConsultaStarships();
	}

	private void menuOpciones() {
		boolean terminado = false;
		teclado = new Scanner(System.in);

		do {
			showMenu();
			String opcion = teclado.nextLine();
			switch (opcion) {
			case "1":
				System.out.println("Ingrese el nombre de la Starships a buscar");
				String name = teclado.nextLine();
				ConsultaStarshipsController.getStarshipsByName(name);
				presioneTeclaParaContinuar();
				break;
			case "2":
				ConsultaStarshipsController.showRegisters();
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
		System.out.println("Opciones de Consultas de Starships: ");
		System.out.println("1- Buscar Starships por nombre");
		System.out.println("2- Mostrar Todas");
		System.out.println("0- Salir ");
	}

	static void presioneTeclaParaContinuar() {
		System.out.println("presione una tecla para continuar....");
		teclado.nextLine();
	}

}
