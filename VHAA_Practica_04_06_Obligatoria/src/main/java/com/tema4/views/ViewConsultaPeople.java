package com.tema4.views;

import java.util.Scanner;

import com.tema4.controller.PeopleController;

public class ViewConsultaPeople {
	private static Scanner teclado;
	private static PeopleController peopleController;

	private ViewConsultaPeople() {
		peopleController = PeopleController.getInstance();
		teclado = new Scanner(System.in);
		menuOpciones();
	}

	public static ViewConsultaPeople getInstance() {
		return new ViewConsultaPeople();
	}

	private void menuOpciones() {
		boolean terminado = false;

		do {
			showMenu();
			String opcion = teclado.nextLine();
			switch (opcion) {
			case "1":
				System.out.println("Ingrese el nombre de Personaje a buscar");
				String name = teclado.nextLine();
				peopleController.findbyName(name);
				presioneTeclaParaContinuar();
				break;
			case "2":
				peopleController.getPeopleWithoutSpecies();
				presioneTeclaParaContinuar();
				break;
			case "3":
				peopleController.getPeopleMoreFilmsParticipated();
				presioneTeclaParaContinuar();
				break;
			case "4":
				peopleController.getMoreRepeatedHair();
				presioneTeclaParaContinuar();
				break;
			case "5":
				peopleController.showRegisters();
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
		System.out.println("Opciones de Consulta de Personajes: ");
		System.out.println("1- Buscar personaje por nombre");
		System.out.println("2- Buscar personajes sin especie");
		System.out.println("3- Buscar personajes en más películas");
		System.out.println("4- Buscar color de pelo más repetido");
		System.out.println("5- Mostrar todos");
		System.out.println("0- Volver ");
	}

	static void presioneTeclaParaContinuar() {
		System.out.println("presione una tecla para continuar....");
		teclado.nextLine();
	}

}
