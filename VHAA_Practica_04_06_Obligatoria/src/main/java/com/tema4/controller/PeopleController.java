package com.tema4.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

import org.hibernate.Transaction;

import com.tema4.constants.KConstants;
import com.tema4.models.Films;
import com.tema4.models.People;
import com.tema4.models.Planets;
import com.tema4.models.Species;
import com.tema4.models.Starships;
import com.tema4.models.Vehicles;
import com.tema4.services.HandlerBD;
import com.tema4.utils.Utiles;

@SuppressWarnings("unchecked")
public class PeopleController implements ICRUDController {
	static PeopleController peopleController;
	static HandlerBD manejador;
	public static Scanner teclado;

	PeopleController() {
		teclado = new Scanner(System.in);
		manejador = HandlerBD.getInstance();
	}

	public static PeopleController getInstance() {
		if (peopleController == null) {
			peopleController = new PeopleController();
			return peopleController;
		}
		return peopleController;
	}

	@Override
	public void create() {
		manejador.tearUp();
		String deseaIngresar = "";
		Transaction trans = manejador.session.beginTransaction();

		People peopleToInsert = getPeopleToInsert();

		System.out.println("Desea ingresar el planeta del people S/N: ");
		deseaIngresar = teclado.nextLine();
		if ("S".equalsIgnoreCase(deseaIngresar.trim())) {
			final String sqlQuery = "FROM Planets";
			List<Planets> planetsInsertados = new ArrayList<Planets>();
			planetsInsertados = manejador.session.createQuery(sqlQuery).list();
			Planets planet = PlanetsController.cargarPlanet(planetsInsertados);
			if (planet != null) {
				peopleToInsert.setPlanets(planet);
			}
		}

		manejador.session.save(peopleToInsert);
		Set<People> peoples = new HashSet<People>();
		peoples.add(peopleToInsert);

		System.out.println("Desea ingresar starships que conduce S/N: ");
		deseaIngresar = teclado.nextLine();
		if ("S".equalsIgnoreCase(deseaIngresar.trim())) {
			final String sqlQuery = "FROM Starships";
			List<Starships> navesInsertadas = new ArrayList<Starships>();
			navesInsertadas = manejador.session.createQuery(sqlQuery).list();
			List<Starships> listaNaves = StarshipsController.cargarStarships(navesInsertadas);
			if (!listaNaves.isEmpty()) {
				listaNaves.stream().forEach(nave -> {
					nave.setPeoples(peoples);
					manejador.session.save(nave);
				});
			}
		}

		System.out.println("Desea ingresar vehicles que conduce S/N: ");
		deseaIngresar = teclado.nextLine();
		if ("S".equalsIgnoreCase(deseaIngresar.trim())) {
			final String sqlQuery = "FROM Vehicles";
			List<Vehicles> vehiclesInsertadas = new ArrayList<Vehicles>();
			vehiclesInsertadas = manejador.session.createQuery(sqlQuery).list();
			List<Vehicles> listaVehicles = VehiclesController.cargarVehicles(vehiclesInsertadas);
			if (!listaVehicles.isEmpty()) {
				listaVehicles.stream().forEach(vehicle -> {
					vehicle.setPeoples(peoples);
					manejador.session.save(vehicle);
				});
			}
		}

		System.out.println("Desea ingresar especies en el people S/N: ");
		deseaIngresar = teclado.nextLine();
		if ("S".equalsIgnoreCase(deseaIngresar.trim())) {
			final String sqlQuery = "FROM Planets";
			List<Species> speciesInsertadas = new ArrayList<Species>();
			speciesInsertadas = manejador.session.createQuery(sqlQuery).list();
			List<Species> listaSpecies = SpeciesController.cargarEspecies(speciesInsertadas);
			if (!listaSpecies.isEmpty()) {
				listaSpecies.stream().forEach(specie -> {
					specie.setPeoples(peoples);
					manejador.session.save(specie);
				});
			}
		}

		System.out.println("Desea ingresar films donde aparece S/N: ");
		deseaIngresar = teclado.nextLine();
		if ("S".equalsIgnoreCase(deseaIngresar.trim())) {
			final String sqlQuery = "FROM Films";
			List<Films> filmsInsertadas = new ArrayList<Films>();
			filmsInsertadas = manejador.session.createQuery(sqlQuery).list();
			List<Films> listaFilms = FilmsController.cargarPeoples(filmsInsertadas);
			if (!listaFilms.isEmpty()) {
				listaFilms.stream().forEach(film -> {
					film.setCodigo(film.getCodigo());
					film.setPeoples(peoples);
					manejador.session.save(film);
				});
			}
		}

		trans.commit();
		System.out.println("registro ingresado...");
		manejador.tearDown();
	}

	private People getPeopleToInsert() {
		People people = new People();
		boolean valido = false;
		String nombre = "";
		do {
			System.out.println("Ingrese el nombre de la people: ");
			nombre = teclado.nextLine();

			valido = !nombre.trim().isEmpty();
			if (!valido) {
				System.out.println(KConstants.Common.NOT_VALID_DATA);
			}

		} while (!valido);

		System.out.println("Ingrese la fecha de nacimiento: ");
		String fechaNacimiento = teclado.nextLine();
		System.out.println("Ingrese el genero: ");
		String genero = teclado.nextLine();
		System.out.println("Ingrese los colores de pelo: ");
		String colorPelo = teclado.nextLine();
		System.out.println("Ingrese los colores de ojos: ");
		String colorOjos = teclado.nextLine();
		System.out.println("Ingrese los colores de piel: ");
		String colorPiel = teclado.nextLine();
		System.out.println("Ingrese la altura: ");
		String altura = teclado.nextLine();
		System.out.println("Ingrese el peso: ");
		String peso = teclado.nextLine();
		String fechaCreacion = Utiles.parseDate(new Date().toString(), KConstants.FormatDate.FORMAT_BD);
		String fechaEdicion = Utiles.parseDate(new Date().toString(), KConstants.FormatDate.FORMAT_BD);

		people.setName(nombre);
		people.setBirthYear(Utiles.controlData(fechaNacimiento, true, false));
		people.setGender(genero);
		people.setHairColor(Utiles.controlData(colorPelo, true, false));
		people.setEyeColor(Utiles.controlData(colorOjos, true, false));
		people.setSkinColor(Utiles.controlData(colorPiel, true, false));
		people.setHeight(Utiles.controlData(altura, true, true));
		people.setMass(Utiles.controlData(peso, true, true));
		people.setCreated(fechaCreacion);
		people.setEdited(fechaEdicion);

		return people;
	}

	@Override
	public void delete() {
		manejador.tearUp();
		Optional<People> peopleEncontrado = getPeopleFromInserts();

		if (peopleEncontrado.isPresent()) {
			System.out.println(KConstants.Common.ARE_YOU_SURE);
			String seguro = teclado.nextLine();
			if ("S".equalsIgnoreCase(seguro.trim())) {
				Transaction trans = manejador.session.beginTransaction();
				manejador.session.delete(peopleEncontrado.get());
				trans.commit();
				System.out.println("People borrado...");
			}
		}
		manejador.tearDown();
	}

	@Override
	public void update() {
		manejador.tearUp();
		Optional<People> peopleEncontrado = getPeopleFromInserts();

		if (peopleEncontrado.isPresent()) {
			String deseaIngresar = "";
			Transaction trans = manejador.session.beginTransaction();

			People peopleToUpdate = getPeopleToUpdate(peopleEncontrado.get());

			System.out.println("Desea ingresar el planeta del people S/N: ");
			deseaIngresar = teclado.nextLine();
			if ("S".equalsIgnoreCase(deseaIngresar.trim())) {
				final String sqlQuery = "FROM Planets";
				List<Planets> planetsInsertados = new ArrayList<Planets>();
				planetsInsertados = manejador.session.createQuery(sqlQuery).list();
				Planets planet = PlanetsController.cargarPlanet(planetsInsertados);
				if (planet != null) {
					peopleToUpdate.setPlanets(planet);
				}
			}

			manejador.session.save(peopleToUpdate);
			Set<People> peoples = new HashSet<People>();
			peoples.add(peopleToUpdate);

			System.out.println("Desea ingresar starships que conduce S/N: ");
			deseaIngresar = teclado.nextLine();
			if ("S".equalsIgnoreCase(deseaIngresar.trim())) {
				final String sqlQuery = "FROM Starships";
				List<Starships> navesInsertadas = new ArrayList<Starships>();
				navesInsertadas = manejador.session.createQuery(sqlQuery).list();
				List<Starships> listaNaves = StarshipsController.cargarStarships(navesInsertadas);
				if (!listaNaves.isEmpty()) {
					listaNaves.stream().forEach(nave -> {
						nave.setPeoples(peoples);
						manejador.session.save(nave);
					});
				}
			}

			System.out.println("Desea ingresar vehicles que conduce S/N: ");
			deseaIngresar = teclado.nextLine();
			if ("S".equalsIgnoreCase(deseaIngresar.trim())) {
				final String sqlQuery = "FROM Vehicles";
				List<Vehicles> vehiclesInsertadas = new ArrayList<Vehicles>();
				vehiclesInsertadas = manejador.session.createQuery(sqlQuery).list();
				List<Vehicles> listaVehicles = VehiclesController.cargarVehicles(vehiclesInsertadas);
				if (!listaVehicles.isEmpty()) {
					listaVehicles.stream().forEach(vehicle -> {
						vehicle.setPeoples(peoples);
						manejador.session.save(vehicle);
					});
				}
			}

			System.out.println("Desea ingresar especies en el people S/N: ");
			deseaIngresar = teclado.nextLine();
			if ("S".equalsIgnoreCase(deseaIngresar.trim())) {
				final String sqlQuery = "FROM Planets";
				List<Species> speciesInsertadas = new ArrayList<Species>();
				speciesInsertadas = manejador.session.createQuery(sqlQuery).list();
				List<Species> listaSpecies = SpeciesController.cargarEspecies(speciesInsertadas);
				if (!listaSpecies.isEmpty()) {
					listaSpecies.stream().forEach(specie -> {
						specie.setPeoples(peoples);
						manejador.session.save(specie);
					});
				}
			}

			System.out.println("Desea ingresar films donde aparece S/N: ");
			deseaIngresar = teclado.nextLine();
			if ("S".equalsIgnoreCase(deseaIngresar.trim())) {
				final String sqlQuery = "FROM Films";
				List<Films> filmsInsertadas = new ArrayList<Films>();
				filmsInsertadas = manejador.session.createQuery(sqlQuery).list();
				List<Films> listaFilms = FilmsController.cargarPeoples(filmsInsertadas);
				if (!listaFilms.isEmpty()) {
					listaFilms.stream().forEach(film -> {
						film.setCodigo(film.getCodigo());
						film.setPeoples(peoples);
						manejador.session.save(film);
					});
				}
			}

			trans.commit();
			System.out.println("registro modificado...");
		}
		manejador.tearDown();
	}

	private People getPeopleToUpdate(People people) {
		System.out.println("Ingrese el nombre de la people: ");
		String nombre = teclado.nextLine();
		System.out.println("Ingrese la fecha de nacimiento: ");
		String fechaNacimiento = teclado.nextLine();
		System.out.println("Ingrese el genero: ");
		String genero = teclado.nextLine();
		System.out.println("Ingrese los colores de pelo: ");
		String colorPelo = teclado.nextLine();
		System.out.println("Ingrese los colores de ojos: ");
		String colorOjos = teclado.nextLine();
		System.out.println("Ingrese los colores de piel: ");
		String colorPiel = teclado.nextLine();
		System.out.println("Ingrese la altura: ");
		String altura = teclado.nextLine();
		System.out.println("Ingrese el peso: ");
		String peso = teclado.nextLine();
		String fechaEdicion = Utiles.parseDate(new Date().toString(), KConstants.FormatDate.FORMAT_BD);

		if (!nombre.isEmpty())
			people.setName(nombre);
		if (!fechaNacimiento.isEmpty())
			people.setBirthYear(Utiles.controlData(fechaNacimiento, true, false));
		if (!genero.isEmpty())
			people.setGender(Utiles.controlData(genero, true, false));
		if (!colorPelo.isEmpty())
			people.setHairColor(Utiles.controlData(colorPelo, true, false));
		if (!colorOjos.isEmpty())
			people.setEyeColor(Utiles.controlData(colorOjos, true, false));
		if (!colorPiel.isEmpty())
			people.setSkinColor(Utiles.controlData(colorPiel, true, false));
		if (!altura.isEmpty())
			people.setHeight(Utiles.controlData(altura, true, true));
		if (!peso.isEmpty())
			people.setMass(Utiles.controlData(peso, true, true));
		people.setEdited(fechaEdicion);

		return people;
	}

	private static Optional<People> getPeopleFromInserts() {
		Optional<People> peopleEncontrado = Optional.empty();

		List<People> listaPeoples = obtenerRegistros();
		listaPeoples.stream().forEach(People::imprimeCodValor);

		boolean valido = false;
		do {
			System.out.println("Introduce el código de People: ");
			final String codigoABorrar = teclado.nextLine();

			if (!codigoABorrar.trim().isEmpty() && Utiles.isNumeric(codigoABorrar)) {
				peopleEncontrado = listaPeoples.stream().filter(f -> f.getCodigo() == Integer.valueOf(codigoABorrar))
						.findFirst();
				valido = peopleEncontrado.isPresent() ? true : false;
			}

			if (!valido) {
				System.out.println(KConstants.Common.CODE_NOT_FOUND);
				System.out.println(KConstants.Common.INSERT_OTHER);
				String otro = teclado.nextLine();
				valido = !"S".equalsIgnoreCase(otro.trim());
			}
		} while (!valido);
		return peopleEncontrado;
	}

	public void findbyName(String name) {
		if (name.trim().isEmpty()) {
			System.out.println(KConstants.Common.NOT_DATA_FIND);
			return;
		}

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

	public void getPeopleWithoutSpecies() {
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

	public void getPeopleMoreFilmsParticipated() {
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

	public void getMoreRepeatedHair() {
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

	public void showRegisters() {
		mostrarTodos(getRegisters());
	}

	private static void mostrarTodos(List<People> consultaPeople) {
		if (consultaPeople.isEmpty()) {
			System.out.println(KConstants.Common.NOT_REGISTER);
		} else {
			Utiles.getCabeceraRegistroPeople();
			consultaPeople.stream().forEach(People::imprimeRegistroCompleto);
		}
	}

	public static List<People> getRegisters() {
		manejador.tearUp();
		List<People> consultaPeople = obtenerRegistros();
		manejador.tearDown();

		return consultaPeople;
	}

	private static List<People> obtenerRegistros() {
		final String sqlQuery = "FROM People";
		List<People> consultaPeople = new ArrayList<People>();
		try {
			consultaPeople = manejador.session.createQuery(sqlQuery).list();
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
		return consultaPeople;
	}

	public static List<People> cargarPeoples(List<People> peoplesInsertadas) {
		if (teclado == null) {
			teclado = new Scanner(System.in);
		}
		List<People> listaPeoples = new ArrayList<People>();
		boolean valido = false;

		peoplesInsertadas.stream().forEach(People::imprimeCodValor);

		Optional<People> peopleEncontrada;
		do {
			System.out.println("Ingrese el código del People: ");
			final String codigo = teclado.nextLine();
			valido = !codigo.trim().isEmpty() && Utiles.isNumeric(codigo);

			if (valido) {
				peopleEncontrada = peoplesInsertadas.stream().filter(n -> n.getCodigo() == Integer.valueOf(codigo))
						.findAny();

				if (!peopleEncontrada.isPresent()) {
					valido = false;
					System.out.println("El código introducido no es válido");
				} else {
					listaPeoples.add(peopleEncontrada.get());

					System.out.println("Desea ingresar otra People S/N");
					String otraNave = teclado.nextLine();
					if ("S".equalsIgnoreCase(otraNave.trim())) {
						valido = false;
					} else {
						valido = true;
					}
				}
			}
		} while (!valido);
		return listaPeoples;
	}
}
