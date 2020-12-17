package com.tema4.controller;

import java.util.List;

import com.tema4.constants.KConstants;
import com.tema4.models.People;
import com.tema4.services.HandlerBD;
import com.tema4.utils.Utiles;

@SuppressWarnings("unchecked")
public class ConsultaPeopleController {

	static HandlerBD manejador = null;

	ConsultaPeopleController() {
	}

	public static void getPeoplesByName(String name) {
		if (name.trim().isEmpty()) {
			System.out.println(KConstants.Common.NOT_DATA_FIND);
			return;
		}

		manejador = HandlerBD.getInstance();
		manejador.tearUp();
		buscarPeopleName(name);
		manejador.tearDown();
	}

	private static void buscarPeopleName(String name) {
		final String sqlQuery = "SELECT * FROM people WHERE UPPER(name) like :name";

		try {
			List<People> consultaPeople = manejador.session.createNativeQuery(sqlQuery).addEntity(People.class)
					.setParameter("name", "%" + name.toUpperCase() + "%").list();

			if (consultaPeople.isEmpty()) {
				System.out.println(KConstants.Common.NOT_REGISTER + " para el nombre " + name);
			} else {
				consultaPeople.stream().forEach(People::imprimeRegistroDetallado);
			}

		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
	}

	public static void getPeopleWithoutSpecies() {
		manejador = HandlerBD.getInstance();
		manejador.tearUp();
		mostrarPeopleSinEspecie();
		manejador.tearDown();
	}

	private static void mostrarPeopleSinEspecie() {
		final String sqlQuery = "SELECT p.* FROM people p LEFT OUTER JOIN species_people pe "
				+ " ON	(p.codigo = pe.codigo_people) WHERE pe.codigo_people IS NULL ORDER BY p.name ";

		try {
			List<People> consultaPeople = manejador.session.createNativeQuery(sqlQuery).addEntity(People.class).list();

			if (consultaPeople.isEmpty()) {
				System.out.println(KConstants.Common.NOT_REGISTER);
			} else {
				System.out.println("Los personajes que no tienen especies son");
				consultaPeople.stream().forEach(People::imprimeRegistroCodigoName);
			}
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
	}

	public static void getPeopleMoreFilmsParticipated() {
		manejador = HandlerBD.getInstance();
		manejador.tearUp();
		mostrarPeoplesMasPeliculasParticipado();
		manejador.tearDown();
	}

	private static void mostrarPeoplesMasPeliculasParticipado() {
		try {
			final String sqlQuery = "SELECT p.name,  COUNT(codigo_film) AS veces "
					+ " FROM films_people fp INNER JOIN people p ON( fp.codigo_people  = p.codigo) "
					+ " GROUP BY p.name ORDER BY veces DESC";

			List<Object[]> tuplas = manejador.session.createNativeQuery(sqlQuery).list();

			String masVeces = "";

			if (tuplas.isEmpty()) {
				System.out.println(KConstants.Common.NOT_REGISTER);
			} else {
				masVeces = tuplas.get(0)[1].toString();
				System.out.println("El/los siguientes personaje/s han participado en " + masVeces + " films.");

				for (int i = 0; i < tuplas.size(); i++) {
					String nombrePeople = tuplas.get(i)[0].toString();
					String vecesFilmPeople = tuplas.get(i)[1].toString();
					if (masVeces.equalsIgnoreCase(vecesFilmPeople)) {
						System.out.println(nombrePeople);
					}
				}
			}
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
	}

	public static void getMoreRepeatedHair() {
		manejador = HandlerBD.getInstance();
		manejador.tearUp();
		mostrarCabellosRepetidos();
		manejador.tearDown();
	}

	private static void mostrarCabellosRepetidos() {
		final String filterA = "%none%";
		final String filterB = "%n/a%";
		final String sqlQuey = "SELECT hair_color, COUNT(hair_color) as cantidad "
				+ " FROM people WHERE hair_color NOT LIKE :condicion1 AND hair_color NOT LIKE :condicion2 "
				+ " GROUP BY hair_color ORDER BY cantidad desc ";
		try {
			List<Object[]> tupla = manejador.session.createNativeQuery(sqlQuey).setParameter("condicion1", filterA)
					.setParameter("condicion2", filterB).list();

			if (tupla.isEmpty()) {
				System.out.println(KConstants.Common.NOT_REGISTER);
			} else {
				String cabello = tupla.get(0)[0].toString().toLowerCase();
				String cantidad = tupla.get(0)[1].toString();
				String resultQuery = "El cabello más repetido es " + cabello + " con la cantidad de " + cantidad
						+ " veces.";
				System.out.println(resultQuery);
			}
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
	}

	public static void getAllRegister() {
		manejador = HandlerBD.getInstance();
		manejador.tearUp();
		buscarTodosRegistros();
		manejador.tearDown();
	}

	private static void buscarTodosRegistros() {
		final String sqlQuery = "SELECT * FROM people";

		try {
			List<People> consultaPeople = manejador.session.createNativeQuery(sqlQuery).addEntity(People.class).list();

			if (consultaPeople.isEmpty()) {
				System.out.println(KConstants.Common.NOT_REGISTER);
			} else {
				Utiles.getCabeceraRegistroPeople();
				consultaPeople.stream().forEach(People::imprimeRegistroCompleto);
			}

		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
	}

}
