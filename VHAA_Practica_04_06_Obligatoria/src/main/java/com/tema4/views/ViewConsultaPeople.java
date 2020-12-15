package com.tema4.views;

import java.util.Scanner;

public class ViewConsultaPeople {
	public static Scanner teclado;

	private ViewConsultaPeople() {
		menuOpciones();
	}

	public static ViewConsultaPeople getInstance() {
		return new ViewConsultaPeople();
	}

	private void menuOpciones() {
		boolean terminado = false;
		teclado = new Scanner(System.in);

		do {
			showMenu();
			String opcion = teclado.nextLine();
			switch (opcion) {
			case "1":
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
		System.out.println("Opciones de People: ");
		System.out.println("1- Buscar people por name");
		System.out.println("2- Buscar people sin especie");
		System.out.println("3- Buscar people en más películas");
		System.out.println("4- Buscar color de pelo más repetido");
		System.out.println("0- Salir ");
	}

}
