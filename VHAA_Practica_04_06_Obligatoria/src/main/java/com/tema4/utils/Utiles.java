package com.tema4.utils;

public class Utiles {

	Utiles() {
	}

	public static void getCabeceraRegistroPeople() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%-8s", "Código"));
		sb.append(String.format("%-30s", "Nombre"));
		sb.append(String.format("%-8s", "Altura"));
		sb.append(String.format("%-8s", "Peso"));
		sb.append(String.format("%-14s", "Pelo"));
		sb.append(String.format("%-14s", "Piel"));
		sb.append(String.format("%-14s", "Ojos"));
		sb.append(String.format("%-14s", "Nacimiento"));
		sb.append(String.format("%-15s", "Mundo"));
		sb.append(String.format("%-30s", "Creado"));
		sb.append(String.format("%-30s", "Editado"));
		System.out.println(sb.toString());
		System.out.println(String.format("%185s", " ").replace(" ", "-"));
	}
	
}
