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
import com.tema4.models.Starships;
import com.tema4.models.Vehicles;
import com.tema4.services.HandlerBD;
import com.tema4.utils.Utiles;

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

	@Override
	public void create() {
		String deseaIngresar = "";
		manejador.tearUp();
		Transaction trans = manejador.session.beginTransaction();

		Films filmToInsert = getFilmToInsert();
		manejador.session.save(filmToInsert);
		Set<Films> films = new HashSet<Films>();
		films.add(filmToInsert);

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

		trans.commit();
		System.out.println("registro ingresado...");
		manejador.tearDown();
	}

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

	@Override
	public void delete() {
		manejador.tearUp();
		Optional<Films> filmEncontrado = getFilmsFromInserts();

		if (filmEncontrado.isPresent()) {
			System.out.println(KConstants.Common.ARE_YOU_SURE);
			String seguro = teclado.nextLine();
			if ("S".equalsIgnoreCase(seguro.trim())) {
				Transaction trans = manejador.session.beginTransaction();
				manejador.session.delete(filmEncontrado.get());
				trans.commit();
				System.out.println("Films borrado...");
			}
		}
		manejador.tearDown();
	}

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
				valido = filmEncontrado.isPresent() ? true : false;
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

	@Override
	public void update() {
		manejador.tearUp();
		Optional<Films> filmEncontrado = getFilmsFromInserts();

		if (filmEncontrado.isPresent()) {
			Films filmModificado = obtenerDatosModificados(filmEncontrado.get());

			String deseaIngresar = "";
			Transaction trans = manejador.session.beginTransaction();

			manejador.session.save(filmModificado);
			Set<Films> films = new HashSet<Films>();
			films.add(filmModificado);

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

			trans.commit();
			System.out.println("registro modificado...");
		}
		manejador.tearDown();
	}

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

	public void findbyName(String title) {
		if (title.trim().isEmpty()) {
			System.out.println(KConstants.Common.NOT_DATA_FIND);
			return;
		}
		manejador.tearUp();
		buscarStarshipsName(title);
		manejador.tearDown();
	}

	private static void buscarStarshipsName(String title) {
		final String sqlQuery = "FROM Films where UPPER(title) like '%" + title.toUpperCase() + "%'";
		try {
			List<Films> consultaFilms = manejador.session.createQuery(sqlQuery).list();
			if (consultaFilms.isEmpty()) {
				System.out.println(KConstants.Common.NOT_REGISTER + " para el nombre " + title);
			} else {
				consultaFilms.stream().forEach(Films::imprimeRegistroDetallado);
			}
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
	}

	public void showRegisters() {
		mostrarTodos(getRegisters());
	}

	private static void mostrarTodos(List<Films> consultaFilms) {
		if (consultaFilms.isEmpty()) {
			System.out.println(KConstants.Common.NOT_REGISTER);
		} else {
			Utiles.getCabeceraRegistroFilms();
			consultaFilms.stream().forEach(Films::imprimeRegistro);
		}
	}

	public static List<Films> getRegisters() {
		manejador.tearUp();
		List<Films> consultaFilms = obtenerRegistros();
		manejador.tearDown();
		return consultaFilms;
	}

	private static List<Films> obtenerRegistros() {
		final String sqlQuery = "FROM Films";
		List<Films> consultaFilms = new ArrayList<Films>();
		try {
			consultaFilms = manejador.session.createQuery(sqlQuery).list();
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
		return consultaFilms;
	}

	public static List<Films> cargarPeoples(List<Films> filmsInsertadas) {
		if (teclado == null) {
			teclado = new Scanner(System.in);
		}
		List<Films> listaFilms = new ArrayList<Films>();
		boolean valido = false;

		filmsInsertadas.stream().forEach(Films::imprimeCodValor);

		Optional<Films> filmsEncontrada;
		do {
			System.out.println("Ingrese el código del Films: ");
			final String codigo = teclado.nextLine();
			valido = !codigo.trim().isEmpty() && Utiles.isNumeric(codigo);

			if (valido) {
				filmsEncontrada = filmsInsertadas.stream().filter(n -> n.getCodigo() == Integer.valueOf(codigo))
						.findAny();

				if (!filmsEncontrada.isPresent()) {
					valido = false;
					System.out.println("El código introducido no es válido");
				} else {
					listaFilms.add(filmsEncontrada.get());

					System.out.println("Desea ingresar otra films S/N: ");
					String otraNave = teclado.nextLine();
					if ("S".equalsIgnoreCase(otraNave.trim())) {
						valido = false;
					} else {
						valido = true;
					}
				}
			}
		} while (!valido);
		return listaFilms;
	}

}
