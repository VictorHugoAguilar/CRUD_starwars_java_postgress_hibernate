package com.tema4.views;

import java.util.Scanner;

import com.tema4.controller.StarshipsController;

public class ViewConsultaStarships {
	private static StarshipsController starshipsController;
	private static Scanner teclado;

	private ViewConsultaStarships() {
		starshipsController = StarshipsController.getInstance();
		teclado = new Scanner(System.in);
		menuOpciones();
	}

	public static ViewConsultaStarships getInstance() {
		return new ViewConsultaStarships();
	}

	private void menuOpciones() {
		boolean terminado = false;

		do {
			showMenu();
			String opcion = teclado.nextLine();
			switch (opcion) {
			case "1":
				System.out.println("Ingrese el nombre de la Starships a buscar");
				String name = teclado.nextLine();
				starshipsController.findbyName(name);
				presioneTeclaParaContinuar();
				break;
			case "2":
				starshipsController.showRegisters();
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
