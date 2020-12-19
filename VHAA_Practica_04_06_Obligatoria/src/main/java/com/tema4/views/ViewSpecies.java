package com.tema4.views;

import java.util.Scanner;

import com.tema4.controller.SpeciesController;

public class ViewSpecies {
	private static SpeciesController speciesController;
	private static Scanner teclado;

	private ViewSpecies() {
		speciesController = SpeciesController.getInstance();
		teclado = new Scanner(System.in);
		menuOpciones();
	}

	public static ViewSpecies getInstance() {
		return new ViewSpecies();
	}

	private void menuOpciones() {
		boolean terminado = false;

		do {
			showMenu();
			String opcion = teclado.nextLine();
			switch (opcion) {
			case "1":
				ViewConsultaSpecie.getInstance();
				break;
			case "2":
				speciesController.create();
				break;
			case "3":
				speciesController.update();
				break;
			case "4":
				speciesController.delete();
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
