package com.tema4.views;

import java.util.Scanner;

import com.tema4.controller.StarshipsController;

public class ViewStarships {
	private static StarshipsController starshipsController;
	private static Scanner teclado;

	private ViewStarships() {
		starshipsController = StarshipsController.getInstance();
		menuOpciones();
	}

	public static ViewStarships getInstance() {
		return new ViewStarships();
	}

	private void menuOpciones() {
		boolean terminado = false;
		teclado = new Scanner(System.in);

		do {
			showMenu();
			String opcion = teclado.nextLine();
			switch (opcion) {
			case "1":
				ViewConsultaStarships.getInstance();
				break;
			case "2":
				starshipsController.create();
				break;
			case "3":
				starshipsController.update();
				break;
			case "4":
				starshipsController.delete();
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
		System.out.println("Opciones de  Naves: ");
		System.out.println("1- Consultas");
		System.out.println("2- Insertar nave");
		System.out.println("3- Modificar nave");
		System.out.println("4- Borrar nave");
		System.out.println("0- Volver ");
	}

}
