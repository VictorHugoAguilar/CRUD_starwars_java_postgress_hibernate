package com.tema4.utils;

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
		sb.append(String.format("%-10s", "Altura"));
		sb.append(String.format("%-10s", "Peso"));
		sb.append(String.format("%-30s", "Pelo"));
		sb.append(String.format("%-30s", "Piel"));
		sb.append(String.format("%-30s", "Ojos"));
		sb.append(String.format("%-20s", "Nacimiento"));
		sb.append(String.format("%-20s", "Mundo"));
		sb.append(String.format("%-30s", "Creado"));
		sb.append(String.format("%-30s", "Editado"));
		sb.append(String.format("%-30s", "Species"));
		sb.append(String.format("%-60s", "Vehicles"));
		sb.append(String.format("%-60s", "Starships"));
		sb.append(String.format("%-80s", "Films"));
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
		sb.append(String.format("%-30s", "Terreno"));
		sb.append(String.format("%-30s", "SuperficieAgua"));
		sb.append(String.format("%-40s", "Creado"));
		sb.append(String.format("%-40s", "Editado"));
		System.out.println(sb.toString());
		System.out.println(String.format("%320s", " ").replace(" ", "-"));
	}

	public static void getCabeceraRegistroVehicles() {

	}

	public static void getCabeceraRegistroStarships() {

	}

	public static void getCabeceraRegistroFilms() {

	}

}
