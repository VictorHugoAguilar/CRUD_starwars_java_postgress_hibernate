package com.tema4.views;

import java.util.Scanner;

import com.tema4.controller.FilmsController;

public class ViewFilms {
	private static Scanner teclado;
	private static FilmsController filmsController;

	private ViewFilms() {
		filmsController = FilmsController.getInstance();
		menuOpciones();
	}

	public static ViewFilms getInstance() {
		return new ViewFilms();
	}

	private void menuOpciones() {
		boolean terminado = false;
		teclado = new Scanner(System.in);

		do {
			showMenu();
			String opcion = teclado.nextLine();
			switch (opcion) {
			case "1":
				ViewConsultaFilms.getInstance();
				break;
			case "2":
				filmsController.create();
				break;
			case "3":
				filmsController.update();
				break;
			case "4":
				filmsController.delete();
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
		System.out.println("Opciones de Films: ");
		System.out.println("1- Consultas");
		System.out.println("2- Insertar film");
		System.out.println("3- Modificar film");
		System.out.println("4- Borrar film");
		System.out.println("0- Salir ");
	}

}
