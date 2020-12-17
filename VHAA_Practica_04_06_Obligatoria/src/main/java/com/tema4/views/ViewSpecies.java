package com.tema4.views;

import java.util.Scanner;

public class ViewSpecies {

	public static Scanner teclado;

	private ViewSpecies() {
		menuOpciones();
	}

	public static ViewSpecies getInstance() {
		return new ViewSpecies();
	}

	private void menuOpciones() {
		boolean terminado = false;
		teclado = new Scanner(System.in);

		do {
			showMenu();
			String opcion = teclado.nextLine();
			switch (opcion) {
			case "1":
				ViewConsultaSpecie.getInstance();
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
		System.out.println("Opciones de Especie: ");
		System.out.println("1- Consultas");
		System.out.println("2- Insertar especie");
		System.out.println("3- Modificar especie");
		System.out.println("4- Borrar especie");
		System.out.println("0- Salir ");
	}

}
