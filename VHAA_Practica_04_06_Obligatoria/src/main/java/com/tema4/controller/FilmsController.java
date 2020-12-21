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
import com.tema4.models.Starships;
import com.tema4.models.Vehicles;
import com.tema4.services.HandlerBD;
import com.tema4.utils.Utiles;

/**
 * Controlador de FilmsController
 * 
 * Clase que controla el CRUD completo para la clase Films
 * 
 * 
 * @author Victor Hugo Aguilar Aguilar
 *
 */
@SuppressWarnings("unchecked")
public class FilmsController implements ICRUDController {
	static FilmsController filmsController;
	static HandlerBD manejador;
	public static Scanner teclado;

	FilmsController() {
		teclado = new Scanner(System.in);
		manejador = HandlerBD.getInstance();
	}

	public static FilmsController getInstance() {
		if (filmsController == null) {
			filmsController = new FilmsController();
			return filmsController;
		}
		return filmsController;
	}

	/**
	 * Método: Create
	 * 
	 * Creación de un registro del tipo Films con sus relaciones
	 */
	public void create() {
		manejador.tearUp();
		try {
			Transaction trans = manejador.session.beginTransaction();

			Films filmToInsert = getFilmToInsert();
			manejador.session.save(filmToInsert);
			Set<Films> films = new HashSet<Films>();
			films.add(filmToInsert);

			insertStarshipFilms(films);
			insertVehicleFilms(films);
			insertPlanetFilms(films);
			insertPeopleFilms(films);

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
	 * Eliminación de un registro del tipo Films no elimina sus relaciones
	 */
	public void delete() {
		manejador.tearUp();
		try {
			Optional<Films> filmEncontrado = getFilmsFromInserts();

			if (filmEncontrado.isPresent()) {
				System.out.println(KConstants.Common.ARE_YOU_SURE);
				String seguro = teclado.nextLine();
				if ("S".equalsIgnoreCase(seguro.trim())) {
					Transaction trans = manejador.session.beginTransaction();
					manejador.session.delete(filmEncontrado.get());
					//trans.commit();
					System.out.println("Films borrado...");
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
			Optional<Films> filmEncontrado = getFilmsFromInserts();

			if (filmEncontrado.isPresent()) {
				Films filmModificado = obtenerDatosModificados(filmEncontrado.get());

				Transaction trans = manejador.session.beginTransaction();

				manejador.session.save(filmModificado);
				Set<Films> films = new HashSet<Films>();
				films.add(filmModificado);

				insertStarshipFilms(films);
				insertVehicleFilms(films);
				insertPlanetFilms(films);
				insertPeopleFilms(films);

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
	 * Método:Insert films in peoples
	 * 
	 * @param films
	 */
	private void insertPeopleFilms(Set<Films> films) {
		String deseaIngresar;
		System.out.println("Desea ingresar people en el films S/N: ");
		deseaIngresar = teclado.nextLine();
		if ("S".equalsIgnoreCase(deseaIngresar.trim())) {
			final String sqlQuery = "FROM People";
			List<People> peoplesInsertadas = new ArrayList<People>();
			peoplesInsertadas = manejador.session.createQuery(sqlQuery).list();
			List<People> listaPeoples = PeopleController.cargarPeoples(peoplesInsertadas);
			if (!listaPeoples.isEmpty()) {
				listaPeoples.stream().forEach(people -> {
					people.setCodigo(people.getCodigo());
					people.setFilmses(films);
					manejador.session.save(people);
				});
			}
		}
	}

	/**
	 * Método: Insert films in planets
	 * 
	 * @param films
	 */
	private void insertPlanetFilms(Set<Films> films) {
		String deseaIngresar;
		System.out.println("Desea ingresar planets en el films S/N: ");
		deseaIngresar = teclado.nextLine();
		if ("S".equalsIgnoreCase(deseaIngresar.trim())) {
			final String sqlQuery = "FROM Planets";
			List<Planets> planetsInsertadas = new ArrayList<Planets>();
			planetsInsertadas = manejador.session.createQuery(sqlQuery).list();
			List<Planets> listaPlanets = PlanetsController.cargarPlanets(planetsInsertadas);
			if (!listaPlanets.isEmpty()) {
				listaPlanets.stream().forEach(planet -> {
					planet.setFilmses(films);
					manejador.session.save(planet);
				});
			}
		}
	}

	/**
	 * Método: Insert films in starships
	 * 
	 * @param films
	 */
	private void insertStarshipFilms(Set<Films> films) {
		String deseaIngresar;
		System.out.println("Desea ingresar starships en el films S/N: ");
		deseaIngresar = teclado.nextLine();
		if ("S".equalsIgnoreCase(deseaIngresar.trim())) {
			final String sqlQuery = "FROM Starships";
			List<Starships> navesInsertadas = new ArrayList<Starships>();
			navesInsertadas = manejador.session.createQuery(sqlQuery).list();
			List<Starships> listaNaves = StarshipsController.cargarStarships(navesInsertadas);
			if (!listaNaves.isEmpty()) {
				listaNaves.stream().forEach(nave -> {
					nave.setFilmses(films);
					manejador.session.save(nave);
				});
			}
		}
	}

	/**
	 * Método: Insert films in vehicles
	 * 
	 * @param films
	 */
	private void insertVehicleFilms(Set<Films> films) {
		String deseaIngresar;
		System.out.println("Desea ingresar vehicles en el films S/N: ");
		deseaIngresar = teclado.nextLine();
		if ("S".equalsIgnoreCase(deseaIngresar.trim())) {
			final String sqlQuery = "FROM Vehicles";
			List<Vehicles> vehiclesInsertadas = new ArrayList<Vehicles>();
			vehiclesInsertadas = manejador.session.createQuery(sqlQuery).list();
			List<Vehicles> listaVehicles = VehiclesController.cargarVehicles(vehiclesInsertadas);
			if (!listaVehicles.isEmpty()) {
				listaVehicles.stream().forEach(vehicle -> {
					vehicle.setFilmses(films);
					manejador.session.save(vehicle);
				});
			}
		}
	}

	/**
	 * Método: Crear un objeto del tipo Films con los datos que introduce el usuario
	 * 
	 * @return Films
	 */
	private Films getFilmToInsert() {
		Films films = new Films();
		boolean valido = false;
		String titulo = "";
		do {
			System.out.println("Ingrese el título: ");
			titulo = teclado.nextLine();

			valido = !titulo.trim().isEmpty();
			if (!valido) {
				System.out.println(KConstants.Common.NOT_VALID_DATA);
			}

		} while (!valido);

		String episodio = "";
		do {
			System.out.println("Ingrese el número de episodio: ");
			episodio = teclado.nextLine();

			valido = !episodio.trim().isEmpty() && Utiles.isNumeric(episodio);
			if (!valido) {
				System.out.println(KConstants.Common.NOT_VALID_DATA);
			}

		} while (!valido);

		String director = "";
		do {
			System.out.println("Ingrese el nombre del director/es: ");
			director = teclado.nextLine();

			valido = !director.trim().isEmpty();
			if (!valido) {
				System.out.println(KConstants.Common.NOT_VALID_DATA);
			}

		} while (!valido);

		String productor = "";
		do {
			System.out.println("Ingrese el nombre del productor/es: ");
			productor = teclado.nextLine();

			valido = !productor.trim().isEmpty();
			if (!valido) {
				System.out.println(KConstants.Common.NOT_VALID_DATA);
			}

		} while (!valido);

		String sinopsis = "";
		do {
			System.out.println("Ingrese la sinopsis: ");
			sinopsis = teclado.nextLine();

			valido = !sinopsis.trim().isEmpty();
			if (!valido) {
				System.out.println(KConstants.Common.NOT_VALID_DATA);
			}

		} while (!valido);

		String fechaSalida = "";
		do {
			System.out.println("Ingrese la fecha de estreno dd/mm/aaaa: ");
			fechaSalida = teclado.nextLine();

			valido = !fechaSalida.trim().isEmpty() && Utiles.isFormatedDateOk(fechaSalida.trim());
			if (!valido) {
				System.out.println(KConstants.Common.NOT_VALID_DATA);
			} else {
				fechaSalida = Utiles.parseDate(fechaSalida, KConstants.FormatDate.YYYYMMDDD);
			}

		} while (!valido);

		String fechaCreación = Utiles.parseDate(new Date().toString(), KConstants.FormatDate.FORMAT_BD);
		String fechaEdicion = Utiles.parseDate(new Date().toString(), KConstants.FormatDate.FORMAT_BD);

		films.setTitle(titulo);
		films.setEpisodeId(episodio);
		films.setDirector(director);
		films.setProducer(productor);
		films.setOpeningCrawl(sinopsis);
		films.setReleaseDate(fechaSalida);
		films.setCreated(fechaCreación);
		films.setEdited(fechaEdicion);
		return films;
	}

	/**
	 * Método: Obtiene un objeto con el código que introduce el usuario
	 * 
	 * @return Optional<Films>
	 */
	private static Optional<Films> getFilmsFromInserts() {
		Optional<Films> filmEncontrado = Optional.empty();

		List<Films> listaFilms = obtenerRegistros();
		listaFilms.stream().forEach(Films::imprimeCodValor);

		boolean valido = false;
		do {
			System.out.println("Introduce el código de Films: ");
			final String codigoABorrar = teclado.nextLine();

			if (!codigoABorrar.trim().isEmpty() && Utiles.isNumeric(codigoABorrar)) {
				filmEncontrado = listaFilms.stream().filter(f -> f.getCodigo() == Integer.valueOf(codigoABorrar))
						.findFirst();
				valido = filmEncontrado.isPresent();
			}
			if (!valido) {
				System.out.println(KConstants.Common.CODE_NOT_FOUND);
				System.out.println(KConstants.Common.INSERT_OTHER);
				String otro = teclado.nextLine();
				valido = !"S".equalsIgnoreCase(otro.trim());
			}
		} while (!valido);
		return filmEncontrado;
	}

	/**
	 * Método: Modifica el objeto del tipo Films con los datos pasado por el usuario
	 * 
	 * @param films
	 * @return Films
	 */
	private Films obtenerDatosModificados(Films films) {
		boolean valido = false;
		System.out.println("Ingrese el título de la película: ");
		String titulo = teclado.nextLine();
		System.out.println("Ingrese el número de episodio");
		String episodio = teclado.nextLine();
		if (!episodio.isEmpty()) {
			do {
				valido = Utiles.isNumeric(episodio);
				if (!valido) {
					System.out.println(KConstants.Common.NOT_VALID_DATA);
					System.out.println("Ingrese el número de episodio");
					episodio = teclado.nextLine();
				}
			} while (!valido);
		}
		System.out.println("Ingrese el nombre del director/es: ");
		String director = teclado.nextLine();
		System.out.println("Ingrese el nombre del productor/es: ");
		String productor = teclado.nextLine();
		System.out.println("Ingrese la sinopsis de la pelicula: ");
		String sinopsis = teclado.nextLine();
		System.out.println("Ingrese la fecha de salida dd/mm/aaaa: ");
		String fechaSalida = teclado.nextLine();
		if (!fechaSalida.isEmpty()) {
			do {
				valido = !fechaSalida.trim().isEmpty() && Utiles.isFormatedDateOk(fechaSalida.trim());
				if (!valido) {
					System.out.println(KConstants.Common.NOT_VALID_DATA);
					System.out.println("Ingrese la fecha de salida dd/mm/aaaa: ");
					fechaSalida = teclado.nextLine();
				} else {
					fechaSalida = Utiles.parseDate(fechaSalida, KConstants.FormatDate.YYYYMMDDD);
				}

			} while (!valido);
		}
		String fechaEdicion = Utiles.parseDate(new Date().toString(), KConstants.FormatDate.FORMAT_BD);
		if (!titulo.isEmpty())
			films.setTitle(titulo);
		if (!episodio.isEmpty())
			films.setEpisodeId(episodio);
		if (!director.isEmpty())
			films.setDirector(director);
		if (!productor.isEmpty())
			films.setProducer(productor);
		if (!sinopsis.isEmpty())
			films.setOpeningCrawl(sinopsis);
		if (!fechaSalida.isEmpty())
			films.setReleaseDate(fechaSalida);
		films.setEdited(fechaEdicion);
		return films;
	}

	/**
	 * Método: busqueda por nombre, método expuesto
	 */
	public void findbyName(String title) {
		if (title.trim().isEmpty()) {
			System.out.println(KConstants.Common.NOT_DATA_FIND);
			return;
		}
		buscarStarshipsName(title);
	}

	/**
	 * Método: busqueda por nombre, método no expuesto
	 */
	private static void buscarStarshipsName(String title) {
		manejador.tearUp();
		try {
			final String sqlQuery = "FROM Films where UPPER(title) like '%" + title.toUpperCase() + "%'";
			List<Films> consultaFilms = manejador.session.createQuery(sqlQuery).list();
			if (consultaFilms.isEmpty()) {
				System.out.println(KConstants.Common.NOT_REGISTER + " para el nombre " + title);
			} else {
				consultaFilms.stream().forEach(Films::imprimeRegistroDetallado);
			}
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
		manejador.tearDown();
	}

	/**
	 * Método: mostar todos los registros, método expuesto
	 */
	public void showRegisters() {
		mostrarTodos(getRegisters());
	}

	/**
	 * Método: imprime los registros que le pasamos por parámetro
	 * 
	 * @param consultaFilms
	 */
	private static void mostrarTodos(List<Films> consultaFilms) {
		if (consultaFilms.isEmpty()) {
			System.out.println(KConstants.Common.NOT_REGISTER);
		} else {
			Utiles.getCabeceraRegistroFilms();
			consultaFilms.stream().forEach(Films::imprimeRegistro);
		}
	}

	/**
	 * Método: Obtiene una lista con todo los registros, método expuesto
	 * 
	 * @return List<Films>
	 */
	public static List<Films> getRegisters() {
		manejador.tearUp();
		List<Films> consultaFilms = obtenerRegistros();
		manejador.tearDown();
		return consultaFilms;
	}

	/**
	 * Método: Obtiene una lista con todo los registros, método no expuesto
	 * 
	 * @return List<Films>
	 */
	private static List<Films> obtenerRegistros() {
		List<Films> consultaFilms = new ArrayList<Films>();
		try {
			final String sqlQuery = "FROM Films";
			consultaFilms = manejador.session.createQuery(sqlQuery).list();
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
		return consultaFilms;
	}

	/**
	 * Método: Carga en una lista las personas del films.
	 * 
	 * @param filmsInsertadas
	 * @return List<Films>
	 */
	public static List<Films> cargarPeoples(List<Films> filmsInsertadas) {
		if (teclado == null) {
			teclado = new Scanner(System.in);
		}
		Optional<Films> filmsEncontrada= Optional.empty();
		List<Films> listaFilms = new ArrayList<Films>();
		boolean valido = false;

		filmsInsertadas.stream().forEach(Films::imprimeCodValor);

		do {
			System.out.println(KConstants.Common.INSERT_CODE);
			final String codigo = teclado.nextLine();
			valido = !codigo.trim().isEmpty() && Utiles.isNumeric(codigo);

			if (valido) {
				filmsEncontrada = filmsInsertadas.stream().filter(n -> n.getCodigo() == Integer.valueOf(codigo))
						.findAny();

				valido = filmsEncontrada.isPresent();

				if (valido) {
					listaFilms.add(filmsEncontrada.get());
				} else {
					System.out.println(KConstants.Common.CODE_NOT_FOUND);
				}

				System.out.println(KConstants.Common.INSERT_OTHER);
				String otrofilm = teclado.nextLine();
				valido = !"S".equalsIgnoreCase(otrofilm.trim());

			} else {
				System.out.println(KConstants.Common.INVALID_CODE);
			}
		} while (!valido);
		return listaFilms;
	}

}
