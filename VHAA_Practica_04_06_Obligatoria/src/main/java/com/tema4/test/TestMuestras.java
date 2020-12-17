package com.tema4.test;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.query.Query;

import com.sun.net.ssl.internal.www.protocol.https.Handler;
import com.tema4.constants.KConstants;
import com.tema4.models.Films;
import com.tema4.models.People;
import com.tema4.models.Planets;
import com.tema4.models.Species;
import com.tema4.models.Starships;
import com.tema4.models.Vehicles;
import com.tema4.services.HandlerBD;
import com.tema4.utils.Utiles;

public class TestMuestras {
	static HandlerBD manejador = null;

	public TestMuestras() {
	}

	public static void main(String[] args) {
		// levantar conexion con BD
		manejador = HandlerBD.getInstance();
		manejador.tearUp();
		manejador.comprobarSession();
		// comprueba
		// compruebaFilms();
		// compruebaStarships();
		// compruebaVehicles();
		// compruebaPeoples();
		// compruebaEspecies();
		// compruebaPlanets();
		// mostrarPeoplesMasPeliculasParticipado();
		// mostrarPeopleSinEspecie();
		// buscarPeopleName("r2");
		// buscarSpecieName("yod");
		// mostrarEspeciesSinPersonajes();

		// cerrar
		manejador.tearDown();
	}

	private static void mostrarEspeciesSinPersonajes() {
		final String sqlQuery = "FROM Species";
		List<Species> consultaSpecies = manejador.session.createQuery(sqlQuery).list();

		List<Species> SpeciesSinPersonajes = consultaSpecies.stream().filter(s -> s.getPeoples().isEmpty())
				.collect(Collectors.toList());

		SpeciesSinPersonajes.stream().forEach(Species::imprimeRegistroDetallado);
	}

	private static void buscarSpecieName(String name) {
		final String sqlQuery = "FROM Species where UPPER(name) like '%" + name.toUpperCase() + "%'";
		List<Species> consultaSpecies = manejador.session.createQuery(sqlQuery).list();
		consultaSpecies.stream().forEach(Species::imprimeRegistro);
	}

	@SuppressWarnings("unchecked")
	private static void buscarPeopleName(String name) {
		if (name.trim().isEmpty()) {
			System.out.println(KConstants.Common.NOT_DATA_FIND);
			return;
		}

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

	@SuppressWarnings("unchecked")
	private static void mostrarPeopleSinEspecie() {

		try {
			final String sqlQuery = "SELECT p.* FROM people p LEFT OUTER JOIN species_people pe "
					+ " ON	(p.codigo = pe.codigo_people) WHERE pe.codigo_people IS NULL ORDER BY p.name ";

			List<People> consultaPeople = manejador.session.createNativeQuery(sqlQuery).addEntity(People.class).list();

			if (consultaPeople.isEmpty()) {
				System.out.println("No hay registros en la Base de Datos");
			} else {
				System.out.println("Los personajes que no tienen especies son");
				consultaPeople.stream().forEach(People::imprimeRegistroCodigoName);
			}
		} catch (Exception e) {
			System.out.println("Ha surgido un fallo en la conexión con la Base de Datos");
		}

	}

	@SuppressWarnings("unchecked")
	private static void mostrarPeoplesMasPeliculasParticipado() {
		try {
			final String sqlQuery = "SELECT p.name,  COUNT(codigo_film) AS veces "
					+ " FROM films_people fp INNER JOIN people p ON( fp.codigo_people  = p.codigo) "
					+ " GROUP BY p.name ORDER BY veces DESC";

			List<Object[]> tuplas = manejador.session.createNativeQuery(sqlQuery).list();

			String masVeces = "";

			if (!tuplas.isEmpty()) {
				masVeces = tuplas.get(0)[1].toString();
				System.out.println("El/los siguientes personaje/s han participado en " + masVeces + " films.");

				for (int i = 0; i < tuplas.size(); i++) {
					String nombrePeople = tuplas.get(i)[0].toString();
					String vecesFilmPeople = tuplas.get(i)[1].toString();
					if (masVeces.equalsIgnoreCase(vecesFilmPeople)) {
						System.out.println(nombrePeople);
					}
				}
			} else {
				System.out.println("No hay datos en la BD");
			}
		} catch (Exception e) {
			System.out.println("Ah ocurrido un fallo en la conexión con la BD");
		}
	}

	@SuppressWarnings("unchecked")
	private static void mostrarCabellosRepetidos() {
		String filterA = "%none%";
		String filterB = "%n/a%";

		final String sqlQuey = "SELECT hair_color, " + "COUNT(hair_color) as cantidad "
				+ "FROM people WHERE hair_color NOT LIKE :condicion1 " + "AND hair_color NOT LIKE :condicion2 "
				+ "GROUP BY hair_color " + "ORDER BY cantidad desc ";

		List<Object[]> tupla = manejador.session.createNativeQuery(sqlQuey).setParameter("condicion1", filterA)
				.setParameter("condicion2", filterB).list();

		if (!tupla.isEmpty()) {
			String cabello = tupla.get(0)[0].toString().toLowerCase();
			String cantidad = tupla.get(0)[1].toString();
			String resultQuery = "El cabello más repetido es " + cabello + " con la cantidad de " + cantidad
					+ " veces.";
			System.out.print(resultQuery);
		}
	}

	private static void compruebaPlanets() {
		Query<Planets> consultaPlanets = manejador.session.createQuery("from Planets");
		java.util.List<Planets> resultadoPlanets = consultaPlanets.list();

		for (Planets planet : resultadoPlanets) {
			System.out.println("----------------------------------");
			System.out.println(planet.getName());
			System.out.println("-----------ESPECIE---------");
			for (Species specie : planet.getSpecieses()) {
				System.out.println(specie.getName());
			}
			System.out.println("-------FILMS------");
			for (Films film : planet.getFilmses()) {
				System.out.println(film.getTitle());
			}
			System.out.println("----PEOPLE----");
			for (People people : planet.getPeoples()) {
				System.out.println(people.getName());
			}
		}

	}

	private static void compruebaEspecies() {
		Query<Species> consultaSpecies = manejador.session.createQuery("from Species");
		java.util.List<Species> resultadoSpecies = consultaSpecies.list();

		for (Species specie : resultadoSpecies) {
			System.out.println("----------------------------------");
			System.out.println(specie.getName());
			System.out.println("------PLANETS-----");
			System.out.println(specie.getPlanets() != null ? specie.getPlanets().getName() : "no hay");
			;
			System.out.println("--------PEOPLE--------");
			for (People people : specie.getPeoples()) {
				System.out.println(people.getName());
			}
		}

	}

	private static void compruebaPeoples() {
		Query<People> consultaPeople = manejador.session.createQuery("from People");
		java.util.List<People> resultadoPeople = consultaPeople.list();
		int resultadoNaves = 0;
		int resultadoCoche = 0;
		for (People people : resultadoPeople) {
			System.out.println("-------------------------------------------");
			System.out.println(people.getCodigo() + " - " + people.getName());
			System.out.println("------------PLANETS------------");
			System.out.println(people.getPlanets().getName());

			System.out.println("----------ESPECIE----------");
			for (Species specie : people.getSpecieses()) {
				System.out.println(specie.getName());
			}

			System.out.println("--------FILMS--------");
			for (Films film : people.getFilmses()) {
				System.out.println(film.getTitle());
			}

			System.out.println("------VEHICLES------");
			for (Vehicles vehicle : people.getVehicleses()) {
				System.out.println(vehicle.getName());
				if (!vehicle.getName().isEmpty()) {
					resultadoCoche++;
				}
			}

			System.out.println("------STARSHPS------");
			for (Starships starship : people.getStarshipses()) {
				System.out.println(starship.getName());
				if (!starship.getName().isEmpty()) {
					resultadoNaves++;
				}
			}

		}
		System.out.println("Total de naves: " + resultadoNaves);
		System.out.println("Total de coches: " + resultadoCoche);

	}

	public static void compruebaVehicles() {
		Query<Vehicles> consultaFilms = manejador.session.createQuery("from Vehicles");
		java.util.List<Vehicles> resultadoVehicle = consultaFilms.list();

		for (Vehicles vehicle : resultadoVehicle) {
			System.out.println(vehicle.getCodigo() + " - " + vehicle.getName());
			System.out.println("-------PEOPLES-------");
			for (People people : vehicle.getPeoples()) {
				System.out.println(people.getCodigo() + " - " + people.getName());
			}
			System.out.println("--------FILMS--------");
			for (Films film : vehicle.getFilmses()) {
				System.out.println(film.getCodigo() + " - " + film.getTitle() + " - " + film.getEpisodeId());
			}
		}
	}

	public static void compruebaStarships() {

		Query<Starships> consultaFilms = manejador.session.createQuery("from Starships");
		java.util.List<Starships> resultadoFilms = consultaFilms.list();

		for (Starships starships : resultadoFilms) {
			System.out.println(starships.getCodigo() + " - " + starships.getName());
			System.out.println("-------PEOPLES-------");
			for (People people : starships.getPeoples()) {
				System.out.println(people.getCodigo() + " - " + people.getName());
			}
			System.out.println("--------FILMS--------");
			for (Films film : starships.getFilmses()) {
				System.out.println(film.getCodigo() + " - " + film.getTitle() + " - " + film.getEpisodeId());
			}
		}
	}

	public static void compruebaFilms() {

		HandlerBD manejador = HandlerBD.getInstance();
		manejador.tearUp();
		manejador.comprobarSession();

		Query<Films> consultaFilms = manejador.session.createQuery("from Films");
		java.util.List<Films> resultadoFilms = consultaFilms.list();

		for (Films film : resultadoFilms) {
			System.out.println(film.getCodigo() + " - " + film.getTitle() + " -> " + film.getEpisodeId());
			System.out.println("-----------PEOPLE-------");
			for (People people : film.getPeoples()) {
				System.out.println(people.getName());
			}
			System.out.println("--------VEHICLES---------");
			for (Vehicles vehicle : film.getVehicleses()) {
				System.out.println(vehicle.getName());
			}
			System.out.println("---------PLANETS---------");
			for (Planets planet : film.getPlanetses()) {
				System.out.println(planet.getName());
			}
			System.out.println("---------STARSHPS-------");
			for (Starships starship : film.getStarshipses()) {
				System.out.println(starship.getName());
			}
		}
		manejador.tearDown();
	}

}
