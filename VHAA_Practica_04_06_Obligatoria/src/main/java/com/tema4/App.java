package com.tema4;

import java.util.Scanner;

import com.tema4.views.ViewFilms;
import com.tema4.views.ViewPeople;
import com.tema4.views.ViewPlanets;
import com.tema4.views.ViewSpecies;
import com.tema4.views.ViewStarships;
import com.tema4.views.ViewVehicles;

public class App {

	public static Scanner teclado;

	public static void main(String[] args) {
		boolean terminado = false;
		teclado = new Scanner(System.in);

		do {
			showMenu();
			String opcion = teclado.nextLine();
			switch (opcion) {
			case "1":
				ViewPeople.getInstance();
				break;
			case "2":
				ViewPlanets.getInstance();
				break;
			case "3":
				ViewSpecies.getInstance();
				break;
			case "4":
				ViewFilms.getInstance();
				break;
			case "5":
				ViewStarships.getInstance();
				break;
			case "6":
				ViewVehicles.getInstance();
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
		teclado.close();
	}

	private static void showMenu() {
		System.out.println("Escoja una opción: ");
		System.out.println("1- Gestión de people");
		System.out.println("2- Gestión de planets");
		System.out.println("3- Gestión de species");
		System.out.println("4- Gestión de films");
		System.out.println("5- Gestión de starships");
		System.out.println("6- Gestión de vehicles");
		System.out.println("0- Salir ");
	}
}
