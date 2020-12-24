package com.tema4;

import java.util.Scanner;

import com.tema4.views.ViewFilms;
import com.tema4.views.ViewPeople;
import com.tema4.views.ViewPlanets;
import com.tema4.views.ViewSpecies;
import com.tema4.views.ViewStarships;
import com.tema4.views.ViewVehicles;

/**
 * Clase: Principal del programa
 * 
 * @author Victor Hugo Aguilar Aguilar
 *
 */
public class App {
	private static Scanner teclado;

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
				System.out.println("Ingrese una opción válida");
				break;
			}
		} while (!terminado);
		teclado.close();
	}

	private static void showMenu() {
		System.out.println("Escoja una opción: ");
		System.out.println("1- Gestión de Personajes");
		System.out.println("2- Gestión de Planetas");
		System.out.println("3- Gestión de Especies");
		System.out.println("4- Gestión de Peliculas");
		System.out.println("5- Gestión de Naves");
		System.out.println("6- Gestión de Vehiculos");
		System.out.println("0- Salir ");
	}
}
