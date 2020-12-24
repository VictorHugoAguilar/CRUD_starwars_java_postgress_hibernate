package com.tema4.views;

import java.util.Scanner;

import com.tema4.controller.SpeciesController;

public class ViewConsultaSpecie {
	private static Scanner teclado;
	private static SpeciesController speciesController;

	private ViewConsultaSpecie() {
		speciesController = SpeciesController.getInstance();
		teclado = new Scanner(System.in);
		menuOpciones();
	}

	public static ViewConsultaSpecie getInstance() {
		return new ViewConsultaSpecie();
	}

	private void menuOpciones() {
		boolean terminado = false;

		do {
			showMenu();
			String opcion = teclado.nextLine();
			switch (opcion) {
			case "1":
				System.out.println("Ingrese el nombre de la especie a buscar");
				String name = teclado.nextLine();
				speciesController.findbyName(name);
				presioneTeclaParaContinuar();
				break;
			case "2":
				speciesController.getSpeciesWithoutPeople();
				presioneTeclaParaContinuar();
				break;
			case "3":
				speciesController.showRegisters();
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
		System.out.println("Opciones de Consulta de Especies: ");
		System.out.println("1- Buscar especie por nombre");
		System.out.println("2- Buscar especie sin personajes");
		System.out.println("3- Mostrar todas");
		System.out.println("0- Volver ");
	}

	static void presioneTeclaParaContinuar() {
		System.out.println("presione una tecla para continuar....");
		teclado.nextLine();
	}

}
