package com.tema4.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import com.tema4.constants.KConstants;

/**
 * Clase: Útiles
 * 
 * @author Victor Hugo Aguilar Aguilar
 *
 */
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
		sb.append(String.format("%-30s", "Fecha estreno"));
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
		if (cadena.contains("\n")) {
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
		String textoCheck = KConstants.Common.DESCONOCIDO;
		if (cadena == null || cadena.trim().isEmpty()) {
			return textoCheck;
		}

		if (KConstants.Common.UNKNOWN.equalsIgnoreCase(cadena) || KConstants.Common.NOTHING.equalsIgnoreCase(cadena)
				|| KConstants.Common.NOT_DATA.equalsIgnoreCase(cadena)) {
			textoCheck = KConstants.Common.DESCONOCIDO;
		}

		return cadena;
	}

	public static boolean isNumeric(String cadena) {
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	public static String parseDate(String fechaString, String formatoRequerido) {
		String nuevoFormato = "";
		switch (formatoRequerido) {
		case KConstants.FormatDate.YYYYMMDDD:
			try {
				// Formato inicial.
				SimpleDateFormat formato = new SimpleDateFormat(KConstants.FormatDate.DDMMYYYY);
				Date d;

				d = formato.parse(fechaString);
				// Aplica formato requerido.
				formato.applyPattern(KConstants.FormatDate.YYYYMMDDD);
				nuevoFormato = formato.format(d);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			break;
		case KConstants.FormatDate.DDMMYYYY:
			try {
				// Formato inicial.
				SimpleDateFormat formato = new SimpleDateFormat(KConstants.FormatDate.YYYYMMDDD);
				Date d;

				d = formato.parse(fechaString);
				// Aplica formato requerido.
				formato.applyPattern(KConstants.FormatDate.DDMMYYYY);
				nuevoFormato = formato.format(d);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			break;
		case KConstants.FormatDate.FORMAT_BD:
			try {
				// Formato inicial.
				SimpleDateFormat formato = new SimpleDateFormat(KConstants.FormatDate.FORMAT_SYS, Locale.US);
				Date d;

				d = formato.parse(fechaString);
				// Aplica formato requerido.
				formato.applyPattern(KConstants.FormatDate.FORMAT_BD);
				nuevoFormato = formato.format(d);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}

		return nuevoFormato;
	}

	public static boolean isFormatedDateOk(String fechaString) {
		String regexp = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
		return Pattern.matches(regexp, fechaString);
	}

	public static String controlData(String cadena, boolean vacio, boolean numerico) {
		if (cadena == null) {
			return KConstants.Common.UNKNOWN;
		}

		if (vacio && cadena.trim().isEmpty()) {
			return KConstants.Common.UNKNOWN;
		}

		if (numerico && !isNumeric(cadena)) {
			return KConstants.Common.UNKNOWN;
		}

		return cadena;
	}

}
