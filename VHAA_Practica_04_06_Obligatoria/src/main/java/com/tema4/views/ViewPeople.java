package com.tema4.views;

import java.util.Scanner;

public class ViewPeople {
	public static Scanner teclado;

	private ViewPeople() {
		menuOpciones();
	}

	public static ViewPeople getInstance() {
		return new ViewPeople();
	}

	private void menuOpciones() {
		boolean terminado = false;
		teclado = new Scanner(System.in);

		do {
			showMenu();
			String opcion = teclado.nextLine();
			switch (opcion) {
			case "1":
				ViewConsultaPeople.getInstance();
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
		System.out.println("1- Consultas");
		System.out.println("2- Insertar people");
		System.out.println("3- Modificar people");
		System.out.println("4- Borrar people");
		System.out.println("0- Salir ");
	}

}
