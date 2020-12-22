package com.tema4.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

import javax.persistence.PersistenceException;

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

/**
 * Controlador de PeopleController
 * 
 * Clase que controla el CRUD completo para la clase People
 * 
 * 
 * @author Victor Hugo Aguilar Aguilar
 *
 */
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

	/**
	 * Método: Create
	 * 
	 * Creación de un registro del tipo People con sus relaciones
	 */
	public void create() {
		manejador.tearUp();
		try {
			Transaction trans = manejador.session.beginTransaction();

			People peopleToInsert = getPeopleToInsert();

			insertPlanetPeople(peopleToInsert);

			manejador.session.save(peopleToInsert);
			Set<People> peoples = new HashSet<People>();
			peoples.add(peopleToInsert);

			insertStarshipPeople(peoples);
			insertVehiclesPeople(peoples);
			insertSpeciePeople(peoples);
			insertFilmPeople(peoples);

			trans.commit();
			System.out.println("registro ingresado...");
		} catch (PersistenceException e) {
			System.err.println("Fallo en el insert en la BD");
		} catch (Exception e) {
			System.err.println(KConstants.Common.FAIL_CONECTION);
		}
		manejador.tearDown();
	}

	/**
	 * Método: Delete
	 * 
	 * Eliminación de un registro del tipo People no elimina sus relaciones
	 */
	public void delete() {
		manejador.tearUp();
		try {
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
		} catch (PersistenceException e) {
			System.err.println(KConstants.Common.DELETE_ERROR);
		} catch (Exception e) {
			System.err.println(KConstants.Common.FAIL_CONECTION);
		}
		manejador.tearDown();
	}

	/**
	 * Método: Update
	 * 
	 * Modificación de un registro del tipo Planet
	 */
	public void update() {
		manejador.tearUp();
		try {
			Optional<People> peopleEncontrado = getPeopleFromInserts();

			if (peopleEncontrado.isPresent()) {
				Transaction trans = manejador.session.beginTransaction();

				People peopleToUpdate = getPeopleToUpdate(peopleEncontrado.get());

				insertPlanetPeople(peopleToUpdate);

				manejador.session.save(peopleToUpdate);
				Set<People> peoples = new HashSet<People>();
				peoples.add(peopleToUpdate);

				insertStarshipPeople(peoples);
				insertVehiclesPeople(peoples);
				insertSpeciePeople(peoples);
				insertFilmPeople(peoples);

				trans.commit();
				System.out.println("registro modificado...");
			}
		} catch (PersistenceException e) {
			System.err.println("Error en la consulta de actualización a la BD");
		} catch (Exception e) {
			System.err.println(KConstants.Common.FAIL_CONECTION);
		}
		manejador.tearDown();
	}

	/**
	 * Método: Insert film in people
	 * 
	 * @param peoples
	 */
	private void insertFilmPeople(Set<People> peoples) {
		String deseaIngresar;
		System.out.println("Desea ingresar films donde aparece S/N: ");
		deseaIngresar = teclado.nextLine();
		if ("S".equalsIgnoreCase(deseaIngresar.trim())) {
			List<Films> listaFilms = new ArrayList<Films>();
			listaFilms = FilmsController.obtenerRegistros(manejador);
			if (!listaFilms.isEmpty()) {
				listaFilms.stream().forEach(film -> {
					film.setCodigo(film.getCodigo());
					film.setPeoples(peoples);
					manejador.session.save(film);
				});
			}
		}
	}

	/**
	 * Método: Insert species in people
	 * 
	 * @param peoples
	 */
	private void insertSpeciePeople(Set<People> peoples) {
		String deseaIngresar;
		System.out.println("Desea ingresar especies en el people S/N: ");
		deseaIngresar = teclado.nextLine();
		if ("S".equalsIgnoreCase(deseaIngresar.trim())) {
			List<Species> listaSpecies = new ArrayList<Species>();
			listaSpecies = SpeciesController.obtenerRegistros(manejador);
			if (!listaSpecies.isEmpty()) {
				listaSpecies.stream().forEach(specie -> {
					specie.setPeoples(peoples);
					manejador.session.save(specie);
				});
			}
		}
	}

	/**
	 * Método: Insert vehicles in people
	 * 
	 * @param peoples
	 */
	private void insertVehiclesPeople(Set<People> peoples) {
		String deseaIngresar;
		System.out.println("Desea ingresar vehicles que conduce S/N: ");
		deseaIngresar = teclado.nextLine();
		if ("S".equalsIgnoreCase(deseaIngresar.trim())) {
			List<Vehicles> listaVehicles = new ArrayList<Vehicles>();
			listaVehicles = VehiclesController.obtenerRegistros(manejador);
			if (!listaVehicles.isEmpty()) {
				listaVehicles.stream().forEach(vehicle -> {
					vehicle.setPeoples(peoples);
					manejador.session.save(vehicle);
				});
			}
		}
	}

	/**
	 * Método: Insert starships in people
	 * 
	 * @param peoples
	 */
	private void insertStarshipPeople(Set<People> peoples) {
		String deseaIngresar;
		System.out.println("Desea ingresar starships que conduce S/N: ");
		deseaIngresar = teclado.nextLine();
		if ("S".equalsIgnoreCase(deseaIngresar.trim())) {
			List<Starships> listaNaves = new ArrayList<Starships>();
			listaNaves = StarshipsController.obtenerRegistros(manejador);
			if (!listaNaves.isEmpty()) {
				listaNaves.stream().forEach(nave -> {
					nave.setPeoples(peoples);
					manejador.session.save(nave);
				});
			}
		}
	}

	/**
	 * Método: Insert planet in people
	 * 
	 * @param peopleToInsert
	 */
	private void insertPlanetPeople(People peopleToInsert) {
		String deseaIngresar;
		System.out.println("Desea ingresar el planeta del people S/N: ");
		deseaIngresar = teclado.nextLine();
		if ("S".equalsIgnoreCase(deseaIngresar.trim())) {
			Planets planet = PlanetsController.obtenerRegistro(manejador);
			if (planet != null) {
				peopleToInsert.setPlanets(planet);
			}
		}
	}

	/**
	 * Método: obtener un objeto del tipo People, con los datos obtenidos del
	 * usuario
	 * 
	 * @return People
	 */
	private People getPeopleToInsert() {
		People people = new People();
		boolean valido = false;
		String nombre = "";
		do {
			System.out.println("Ingrese el nombre de la people(obligatorio): ");
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

	/**
	 * Método: modificar el objeto del tipo people con los nuevos datos del usuario
	 * 
	 * @param people
	 * @return People
	 */
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

	/**
	 * Método: obtener un objeto people de la lista insertada
	 * 
	 * @return Optional<People>
	 */
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
				valido = peopleEncontrado.isPresent();
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

	/**
	 * Método: buscar por nombre, método expuesto
	 */
	public void findbyName(String name) {
		if (name.trim().isEmpty()) {
			System.out.println(KConstants.Common.NOT_DATA_FIND);
			return;
		}
		manejador.tearUp();
		buscarPeopleName(name);
		manejador.tearDown();
	}

	/**
	 * Método: buscar por nombre, método no expuesto
	 * 
	 * @param name
	 */
	private static void buscarPeopleName(String name) {
		try {
			final String sqlQuery = "SELECT * FROM people WHERE UPPER(name) like :name";

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

	/**
	 * Método: mostrar los peoples sin especies, método expuesto
	 */
	public void getPeopleWithoutSpecies() {
		manejador.tearUp();
		mostrarPeopleSinEspecie();
		manejador.tearDown();
	}

	/**
	 * Método: mostrar los peoples sin especies, método no expuesto
	 */
	private static void mostrarPeopleSinEspecie() {
		try {
			final String sqlQuery = "SELECT p.* FROM people p LEFT OUTER JOIN species_people pe "
					+ " ON	(p.codigo = pe.codigo_people) WHERE pe.codigo_people IS NULL ORDER BY p.codigo ";

			List<People> consultaPeople = manejador.session.createNativeQuery(sqlQuery).addEntity(People.class).list();

			if (consultaPeople.isEmpty()) {
				System.out.println(KConstants.Common.NOT_REGISTER);
			} else {
				System.out.println("Los personajes que no tienen especies son");
				consultaPeople.stream().forEach(People::imprimeCodValor);
			}
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
	}

	/**
	 * Método: mostrar los people en más films partiparon, método expuesto
	 */
	public void getPeopleMoreFilmsParticipated() {
		manejador.tearUp();
		mostrarPeoplesMasPeliculasParticipado();
		manejador.tearDown();
	}

	/**
	 * Método: mostrar los people en más films partiparon, método no expuesto
	 */
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

	/**
	 * Método: mostrar los cabellos más repetidos, método expuesto
	 */
	public void getMoreRepeatedHair() {
		manejador.tearUp();
		mostrarCabellosRepetidos();
		manejador.tearDown();
	}

	/**
	 * Método: mostrar los cabellos más repetidos, método no expuesto
	 */
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

	/**
	 * Método: Mostrar todos los registros, método expuesto
	 */
	public void showRegisters() {
		mostrarTodos(getRegisters());
	}

	/**
	 * Método: Imprime la lista que le pasamos si tienen si no nos imprime un aviso
	 * 
	 * @param consultaPeople
	 */
	private static void mostrarTodos(List<People> consultaPeople) {
		if (consultaPeople.isEmpty()) {
			System.out.println(KConstants.Common.NOT_REGISTER);
		} else {
			Utiles.getCabeceraRegistroPeople();
			consultaPeople.stream().forEach(People::imprimeRegistroCompleto);
		}
	}

	/**
	 * Método: Obtiene la lista de todos los peoples almacenados, método expuesto
	 * 
	 * @return List<People>
	 */
	private static List<People> getRegisters() {
		manejador.tearUp();
		List<People> consultaPeople = obtenerRegistros();
		manejador.tearDown();

		return consultaPeople;
	}

	/**
	 * Método: Obtiene la lista de todos los peoples almacenados, método no expuesto
	 * 
	 * @return List<People>
	 */
	private static List<People> obtenerRegistros() {
		List<People> consultaPeople = new ArrayList<People>();
		try {
			final String sqlQuery = "FROM People ORDER BY codigo";
			consultaPeople = manejador.session.createQuery(sqlQuery).list();
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
		return consultaPeople;
	}

	/**
	 * Método: Obtiene la lista de todos los peoples almacenados, método no expuesto
	 * 
	 * @return List<People>
	 */
	public static List<People> obtenerRegistros(HandlerBD manejador) {
		List<People> consultaPeople = new ArrayList<People>();
		try {
			final String sqlQuery = "FROM People ORDER BY codigo";
			consultaPeople = manejador.session.createQuery(sqlQuery).list();
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
		return cargarPeoples(consultaPeople);
	}

	/**
	 * Método: Carga una lista de peoples a partir de los peoples insertados
	 * 
	 * @param peoplesInsertadas
	 * @return List<People>
	 */
	private static List<People> cargarPeoples(List<People> peoplesInsertadas) {
		if (teclado == null) {
			teclado = new Scanner(System.in);
		}
		List<People> listaPeoples = new ArrayList<People>();
		Optional<People> peopleEncontrada = Optional.empty();
		boolean valido = false;

		peoplesInsertadas.stream().forEach(People::imprimeCodValor);

		do {
			System.out.println(KConstants.Common.INSERT_CODE);
			final String codigo = teclado.nextLine();
			valido = !codigo.trim().isEmpty() && Utiles.isNumeric(codigo);

			if (valido) {
				peopleEncontrada = peoplesInsertadas.stream().filter(n -> n.getCodigo() == Integer.valueOf(codigo))
						.findAny();

				valido = peopleEncontrada.isPresent();

				if (valido) {
					listaPeoples.add(peopleEncontrada.get());
				} else {
					System.out.println(KConstants.Common.CODE_NOT_FOUND);
				}

				System.out.println(KConstants.Common.INSERT_OTHER);
				String otro = teclado.nextLine();
				valido = !"S".equalsIgnoreCase(otro.trim());

			} else {
				System.out.println(KConstants.Common.INVALID_CODE);
			}
		} while (!valido);
		return listaPeoples;
	}
}
