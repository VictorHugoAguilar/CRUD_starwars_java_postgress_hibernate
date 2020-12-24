package com.tema4.views;

import java.util.Scanner;

import com.tema4.controller.FilmsController;

public class ViewConsultaFilms {
	private static Scanner teclado;
	private static FilmsController filmsController;

	private ViewConsultaFilms() {
		filmsController = FilmsController.getInstance();
		teclado = new Scanner(System.in);
		menuOpciones();
	}

	public static ViewConsultaFilms getInstance() {
		return new ViewConsultaFilms();
	}

	private void menuOpciones() {
		boolean terminado = false;

		do {
			showMenu();
			String opcion = teclado.nextLine();
			switch (opcion) {
			case "1":
				System.out.println("Ingrese el título de la Película a buscar");
				String title = teclado.nextLine();
				filmsController.findbyName(title);
				presioneTeclaParaContinuar();
				break;
			case "2":
				filmsController.showRegisters();
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
		System.out.println("Opciones de Consultas de Películas: ");
		System.out.println("1- Buscar película por titulo");
		System.out.println("2- Mostrar todas");
		System.out.println("0- Volver ");
	}

	static void presioneTeclaParaContinuar() {
		System.out.println("presione una tecla para continuar....");
		teclado.nextLine();
	}

}
