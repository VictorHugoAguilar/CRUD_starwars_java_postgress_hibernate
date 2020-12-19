package com.tema4.views;

import java.util.Scanner;

import com.tema4.controller.ConsultaFilmsController;

public class ViewConsultaFilms {
	public static Scanner teclado;

	private ViewConsultaFilms() {
		menuOpciones();
	}

	public static ViewConsultaFilms getInstance() {
		return new ViewConsultaFilms();
	}

	private void menuOpciones() {
		boolean terminado = false;
		teclado = new Scanner(System.in);

		do {
			showMenu();
			String opcion = teclado.nextLine();
			switch (opcion) {
			case "1":
				System.out.println("Ingrese el título del Films a buscar");
				String title = teclado.nextLine();
				ConsultaFilmsController.getStarshipsByName(title);
				presioneTeclaParaContinuar();
				break;
			case "2":
				ConsultaFilmsController.showRegisters();
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
		System.out.println("Opciones de Consultas Films: ");
		System.out.println("1- Buscar Films por titulo");
		System.out.println("2- Mostrar Todos");
		System.out.println("0- Salir ");
	}

	static void presioneTeclaParaContinuar() {
		System.out.println("presione una tecla para continuar....");
		teclado.nextLine();
	}

}
