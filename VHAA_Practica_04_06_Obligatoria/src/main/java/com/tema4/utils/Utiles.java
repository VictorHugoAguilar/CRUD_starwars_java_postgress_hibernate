package com.tema4.utils;

import com.tema4.constants.KConstants;
import com.tema4.models.Films;
import com.tema4.models.Species;
import com.tema4.models.Starships;
import com.tema4.models.Vehicles;

public class Utiles {

	Utiles() {
	}

	public static void getCabeceraRegistroPeople() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%-8s", "Código"));
		sb.append(String.format("%-30s", "Nombre"));
		sb.append(String.format("%-15s", "Altura"));
		sb.append(String.format("%-15s", "Peso"));
		sb.append(String.format("%-30s", "Pelo"));
		sb.append(String.format("%-30s", "Piel"));
		sb.append(String.format("%-30s", "Ojos"));
		sb.append(String.format("%-20s", "Nacimiento"));
		sb.append(String.format("%-20s", "Mundo"));
		sb.append(String.format("%-30s", "Species"));
		sb.append(String.format("%-60s", "Vehicles"));
		sb.append(String.format("%-60s", "Starships"));
		sb.append(String.format("%-100s", "Films"));
		sb.append(String.format("%-30s", "Creado"));
		sb.append(String.format("%-30s", "Editado"));
		System.out.println(sb.toString());
		System.out.println(String.format("%520s", " ").replace(" ", "-"));
	}

	public static void getCabeceraRegistroSpecie() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%-8s", "Código"));
		sb.append(String.format("%-20s", "Nombre"));
		sb.append(String.format("%-20s", "Designacion"));
		sb.append(String.format("%-20s", "Altura Promedio"));
		sb.append(String.format("%-20s", "Esperanza Vida"));
		sb.append(String.format("%-20s", "Lenguaje"));
		sb.append(String.format("%-20s", "Mundo"));
		sb.append(String.format("%-50s", "Ojos"));
		sb.append(String.format("%-50s", "Pelo"));
		sb.append(String.format("%-50s", "Piel"));
		sb.append(String.format("%-40s", "Creado"));
		sb.append(String.format("%-40s", "Editado"));
		System.out.println(sb.toString());
		System.out.println(String.format("%348s", " ").replace(" ", "-"));
	}

	public static void getCabeceraRegistroPlanet() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%-10s", "Codigo"));
		sb.append(String.format("%-20s", "Name"));
		sb.append(String.format("%-20s", "Diametro"));
		sb.append(String.format("%-20s", "Rotacion"));
		sb.append(String.format("%-20s", "Orbital"));
		sb.append(String.format("%-30s", "Gravedad"));
		sb.append(String.format("%-30s", "Poblacion"));
		sb.append(String.format("%-30s", "Clima"));
		sb.append(String.format("%-50s", "Terreno"));
		sb.append(String.format("%-30s", "SuperficieAgua"));
		sb.append(String.format("%-40s", "Creado"));
		sb.append(String.format("%-40s", "Editado"));
		System.out.println(sb.toString());
		System.out.println(String.format("%320s", " ").replace(" ", "-"));
	}

	public static void getCabeceraRegistroVehicles() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%-10s", "Código"));
		sb.append(String.format("%-30s", "Nombre"));
		sb.append(String.format("%-30s", "Modelo"));
		sb.append(String.format("%-30s", "Clase"));
		sb.append(String.format("%-30s", "Fabricado"));
		sb.append(String.format("%-20s", "Coste"));
		sb.append(String.format("%-20s", "Largo"));
		sb.append(String.format("%-20s", "Tripulación"));
		sb.append(String.format("%-20s", "Velocidad"));
		sb.append(String.format("%-20s", "Capacidad"));
		sb.append(String.format("%-20s", "Consumibles"));
		sb.append(String.format("%-30s", "Creado"));
		sb.append(String.format("%-30s", "Editado"));
		System.out.println(sb.toString());
		System.out.println(String.format("%310s", " ").replace(" ", "-"));
	}

	public static void getCabeceraRegistroStarships() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%-10s", "Código"));
		sb.append(String.format("%-30s", "Nombre"));
		sb.append(String.format("%-30s", "Modelo"));
		sb.append(String.format("%-30s", "Clase"));
		sb.append(String.format("%-30s", "Fabricado"));
		sb.append(String.format("%-20s", "Coste"));
		sb.append(String.format("%-20s", "Largo"));
		sb.append(String.format("%-20s", "Tripulación"));
		sb.append(String.format("%-20s", "Velocidad"));
		sb.append(String.format("%-20s", "Hyper"));
		sb.append(String.format("%-20s", "Mglt"));
		sb.append(String.format("%-20s", "Capacidad"));
		sb.append(String.format("%-20s", "Consumibles"));
		sb.append(String.format("%-30s", "Creado"));
		sb.append(String.format("%-30s", "Editado"));
		System.out.println(sb.toString());
		System.out.println(String.format("%350s", " ").replace(" ", "-"));
	}

	public static void getCabeceraRegistroFilms() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%-10s", "Código"));
		sb.append(String.format("%-30s", "Título"));
		sb.append(String.format("%-10s", "Episodio"));
		sb.append(String.format("%-30s", "Director"));
		sb.append(String.format("%-50s", "Productor"));
		sb.append(String.format("%-65s", "Sinopsis"));
		sb.append(String.format("%-30s", "Creado"));
		sb.append(String.format("%-30s", "Editado"));
		System.out.println(sb.toString());
		System.out.println(String.format("%255s", " ").replace(" ", "-"));
	}

	public static String formatedTextSize(String cadena, int sizeFormated) {
		String textoFormateado = "";

		if (cadena.isEmpty() || sizeFormated <= 3) {
			return textoFormateado;
		}
		if(cadena.contains("\n")) {
			cadena = cadena.replace("\n", " ");
		}

		if (cadena.trim().length() > sizeFormated) {
			textoFormateado = cadena.trim().substring(0, sizeFormated - 3) + "...";
		} else {
			textoFormateado = cadena;
		}

		return textoFormateado;
	}

	public static String checkUnknown(String cadena) {
		String textoCheck = cadena;
		if (cadena.trim().isEmpty()) {
			return textoCheck;
		}

		if (KConstants.Common.UNKNOWN.equalsIgnoreCase(cadena) || KConstants.Common.NOTHING.equalsIgnoreCase(cadena)
				|| KConstants.Common.NOT_DATA.equalsIgnoreCase(cadena)) {
			textoCheck = KConstants.Common.DESCONOCIDO;
		}

		return textoCheck;
	}

}
