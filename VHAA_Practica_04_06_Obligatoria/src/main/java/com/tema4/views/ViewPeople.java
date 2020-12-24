package com.tema4.views;

import java.util.Scanner;

import com.tema4.controller.PeopleController;

public class ViewPeople {
	private static Scanner teclado;
	private static PeopleController peopleController;

	private ViewPeople() {
		peopleController = PeopleController.getInstance();
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
				peopleController.create();
				break;
			case "3":
				peopleController.update();
				break;
			case "4":
				peopleController.delete();
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
		System.out.println("Opciones de Personajes: ");
		System.out.println("1- Consultas");
		System.out.println("2- Insertar personaje");
		System.out.println("3- Modificar personaje");
		System.out.println("4- Borrar personaje");
		System.out.println("0- Volver ");
	}

}
