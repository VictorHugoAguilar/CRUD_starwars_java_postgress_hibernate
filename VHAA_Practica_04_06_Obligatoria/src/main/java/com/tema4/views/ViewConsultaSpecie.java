package com.tema4.views;

import java.util.Scanner;

import com.tema4.controller.ConsultaSpecieController;

public class ViewConsultaSpecie {
	public static Scanner teclado;

	private ViewConsultaSpecie() {
		menuOpciones();
	}

	public static ViewConsultaSpecie getInstance() {
		return new ViewConsultaSpecie();
	}

	private void menuOpciones() {
		boolean terminado = false;
		teclado = new Scanner(System.in);

		do {
			showMenu();
			String opcion = teclado.nextLine();
			switch (opcion) {
			case "1":
				System.out.println("Ingrese el nombre de la especie a buscar");
				String name = teclado.nextLine();
				ConsultaSpecieController.getSpecieByName(name);
				presioneTeclaParaContinuar();
				break;
			case "2":
				ConsultaSpecieController.getSpeciesWithoutPeople();
				presioneTeclaParaContinuar();
				break;
			case "3":
				ConsultaSpecieController.getAllRegister();
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
		System.out.println("Opciones de Consulta de Especies: ");
		System.out.println("1- Buscar especie por nombre");
		System.out.println("2- Buscar especie sin personajes");
		System.out.println("3- Mostrar Todas");
		System.out.println("0- Salir ");
	}

	static void presioneTeclaParaContinuar() {
		System.out.println("presione una tecla para continuar....");
		teclado.nextLine();
	}

}
