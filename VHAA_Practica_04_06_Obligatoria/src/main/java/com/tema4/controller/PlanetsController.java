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
import com.tema4.services.HandlerBD;
import com.tema4.utils.Utiles;

/**
 * Controlador de PlanetsController
 * 
 * Clase que controla el CRUD completo para la clase Planets
 * 
 * @author Victor Hugo Aguilar Aguilar
 *
 */
@SuppressWarnings("unchecked")
public class PlanetsController implements ICRUDController {
	private static PlanetsController planetsController;
	private static HandlerBD manejador;
	private static Scanner teclado;

	PlanetsController() {
		teclado = new Scanner(System.in);
		manejador = HandlerBD.getInstance();
	}

	public static PlanetsController getIntance() {
		if (planetsController == null) {
			planetsController = new PlanetsController();
			return planetsController;
		}
		return planetsController;
	}

	/**
	 * Método: Create
	 * 
	 * Creación de un registro del tipo Planet con sus relaciones
	 */
	public void create() {
		manejador.tearUp();
		try {
			Transaction trans = manejador.session.beginTransaction();
			Planets planetToInsert = getPlanetToInsert();

			planetToInsert = getRelations(planetToInsert);

			manejador.session.save(planetToInsert);
			trans.commit();

			System.out.println("Planeta ingresado...");
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
	 * Eliminación de un registro del tipo Planet no elimina sus relaciones
	 */
	public void delete() {
		manejador.tearUp();
		try {
			Optional<Planets> planetsEncontrado = getPlanetFromInserts();
			if (planetsEncontrado.isPresent()) {
				System.out.println(KConstants.Common.ARE_YOU_SURE);
				String seguro = teclado.nextLine();
				if ("S".equalsIgnoreCase(seguro.trim())) {

					Transaction trans = manejador.session.beginTransaction();
					manejador.session.delete(planetsEncontrado.get());
					trans.commit();
					System.out.println("Planeta borrado...");
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
			Optional<Planets> planetsEncontrado = getPlanetFromInserts();

			if (planetsEncontrado.isPresent()) {
				Transaction trans = manejador.session.beginTransaction();

				Planets planetToUpdate = getPlanetToUpdate(planetsEncontrado.get());
				planetToUpdate = getRelations(planetToUpdate);

				manejador.session.update(planetToUpdate);
				trans.commit();

				System.out.println("Planeta modificado...");
			}
		} catch (PersistenceException e) {
			System.err.println("Error en la consulta de actualización a la BD");
		} catch (Exception e) {
			System.err.println(KConstants.Common.FAIL_CONECTION);
		}
		manejador.tearDown();
	}

	/**
	 * Método: inserta o actualiza las relaciones con el objeto pasado por el
	 * parámetro
	 * 
	 * @param Planets
	 * @return Planets con la relaciones
	 */
	private Planets getRelations(Planets planet) {
		Set<People> selectedPeople = PeopleController.getPeoples(manejador);
		if (!selectedPeople.isEmpty())
			planet.setPeoples(selectedPeople);

		Set<Films> selectedFilms = FilmsController.getFilms(manejador);
		if (!selectedFilms.isEmpty())
			planet.setFilmses(selectedFilms);

		Set<Species> selectedSpecies = SpeciesController.getSpecies(manejador);
		if (!selectedSpecies.isEmpty())
			planet.setSpecieses(selectedSpecies);

		return planet;
	}

	/**
	 * Método: Obtener un objeto del tipo planets preguntado al usuario.
	 * 
	 * @return Planets
	 */
	private Planets getPlanetToInsert() {
		Planets planet = new Planets();

		boolean valido = false;
		String nombre = "";
		do {
			System.out.println("Ingrese el nombre(obligatorio): ");
			nombre = teclado.nextLine();

			valido = !nombre.trim().isEmpty();
			if (!valido) {
				System.out.println(KConstants.Common.NOT_VALID_DATA);
			}

		} while (!valido);

		System.out.println("Ingrese el diámetro: ");
		String diameter = teclado.nextLine();
		System.out.println("Ingrese el período de rotación: ");
		String rotationPeriod = teclado.nextLine();
		System.out.println("Periodo en orbita: ");
		String orbitalPeriod = teclado.nextLine();
		System.out.println("Ingrese la gravedad: ");
		String gravity = teclado.nextLine();
		System.out.println("Ingrese la población: ");
		String population = teclado.nextLine();
		System.out.println("Ingrese el clima: ");
		String climate = teclado.nextLine();
		System.out.println("Ingrese los terrenos: ");
		String terrain = teclado.nextLine();
		System.out.println("Ingrese las superficies: ");
		String surfaceWater = teclado.nextLine();
		String fechaCreación = Utiles.parseDate(new Date().toString(), KConstants.FormatDate.FORMAT_BD);
		String fechaEdicion = Utiles.parseDate(new Date().toString(), KConstants.FormatDate.FORMAT_BD);

		planet.setName(nombre);
		planet.setDiameter(Utiles.controlData(diameter, true, true));
		planet.setRotationPeriod(Utiles.controlData(rotationPeriod, true, true));
		planet.setOrbitalPeriod(Utiles.controlData(orbitalPeriod, true, true));
		planet.setGravity(Utiles.controlData(gravity, true, false));
		planet.setPopulation(Utiles.controlData(population, true, true));
		planet.setClimate(Utiles.controlData(climate, true, false));
		planet.setTerrain(Utiles.controlData(terrain, true, false));
		planet.setSurfaceWater(Utiles.controlData(surfaceWater, true, false));
		planet.setCreated(fechaCreación);
		planet.setEdited(fechaEdicion);
		return planet;
	}

	/**
	 * Método: Actualizamos el objeto del tipo planet preguntando sus atributos al
	 * usuario.
	 * 
	 * @param planets
	 * @return Planets
	 * 
	 */
	private Planets getPlanetToUpdate(Planets planets) {
		boolean valido = false;

		System.out.println("Ingrese el nombre: ");
		String nombre = teclado.nextLine();

		System.out.println("Ingrese el diámetro: ");
		String diameter = teclado.nextLine();
		if (!diameter.isEmpty()) {
			do {
				valido = Utiles.isNumeric(diameter);
				if (!valido) {
					System.out.println(KConstants.Common.NOT_VALID_DATA);
					System.out.println("Ingrese el diámetro númerico: ");
					diameter = teclado.nextLine();
				}
			} while (!valido);
		}

		System.out.println("Ingrese el período de rotación: ");
		String rotationPeriod = teclado.nextLine();
		if (!rotationPeriod.isEmpty()) {
			do {
				valido = Utiles.isNumeric(rotationPeriod);
				if (!valido) {
					System.out.println(KConstants.Common.NOT_VALID_DATA);
					System.out.println("Ingrese el periodo de rotación númerico: ");
					nombre = teclado.nextLine();
				}
			} while (!valido);
		}

		System.out.println("Periodo en orbita: ");
		String orbitalPeriod = teclado.nextLine();
		if (!orbitalPeriod.isEmpty()) {
			do {
				valido = Utiles.isNumeric(orbitalPeriod);
				if (!valido) {
					System.out.println(KConstants.Common.NOT_VALID_DATA);
					System.out.println("Ingrese el periodo en orbital númerico: ");
					nombre = teclado.nextLine();
				}

			} while (!valido);
		}
		System.out.println("Ingrese la Gravedad: ");
		String gravity = teclado.nextLine();
		System.out.println("Ingrese la población: ");
		String population = teclado.nextLine();
		if (!population.isEmpty()) {
			do {
				valido = Utiles.isNumeric(population);
				if (!valido) {
					System.out.println(KConstants.Common.NOT_VALID_DATA);
					System.out.println("Ingrese la población númerico: ");
					nombre = teclado.nextLine();
				}
			} while (!valido);
		}
		System.out.println("Ingrese el clima: ");
		String climate = teclado.nextLine();
		System.out.println("Ingrese los terrenos ");
		String terrain = teclado.nextLine();
		System.out.println("Ingrese las superficies: ");
		String surfaceWater = teclado.nextLine();
		String fechaEdicion = Utiles.parseDate(new Date().toString(), KConstants.FormatDate.FORMAT_BD);

		if (!nombre.isEmpty())
			planets.setName(nombre);
		if (!diameter.isEmpty())
			planets.setDiameter(diameter);
		if (!rotationPeriod.isEmpty())
			planets.setRotationPeriod(rotationPeriod);
		if (!orbitalPeriod.isEmpty())
			planets.setOrbitalPeriod(orbitalPeriod);
		if (!gravity.isEmpty())
			planets.setGravity(gravity);
		if (!population.isEmpty())
			planets.setPopulation(population);
		if (!climate.isEmpty())
			planets.setClimate(climate);
		if (!terrain.isEmpty())
			planets.setTerrain(terrain);
		if (!surfaceWater.isEmpty())
			planets.setSurfaceWater(surfaceWater);

		planets.setEdited(fechaEdicion);

		return planets;
	}

	/**
	 * Método: Obtención de un objeto del tipo planet consultado al usuario
	 * 
	 * @return Optional<Planets>
	 */
	private static Optional<Planets> getPlanetFromInserts() {
		Optional<Planets> planetsEncontrado = Optional.empty();

		List<Planets> listaPlanets = obtenerRegistros();
		listaPlanets.stream().forEach(Planets::imprimeCodValor);

		boolean valido = false;
		do {
			System.out.println("Introduce el código de planeta: ");
			final String codigoABorrar = teclado.nextLine();

			if (!codigoABorrar.trim().isEmpty() && Utiles.isNumeric(codigoABorrar)) {
				planetsEncontrado = listaPlanets.stream().filter(f -> f.getCodigo() == Integer.valueOf(codigoABorrar))
						.findFirst();
				valido = planetsEncontrado.isPresent();
			}
			if (!valido) {
				System.out.println(KConstants.Common.CODE_NOT_FOUND);
				System.out.println(KConstants.Common.INSERT_OTHER);
				String otro = teclado.nextLine();
				valido = !"S".equalsIgnoreCase(otro.trim());
			}
		} while (!valido);

		return planetsEncontrado;
	}

	/**
	 * Método: Buscar planetas por nombre expuesto
	 */
	public void findbyName(String name) {
		if (name.trim().isEmpty()) {
			System.out.println(KConstants.Common.NOT_DATA_FIND);
			return;
		}
		manejador.tearUp();
		buscarPlanetName(name);
		manejador.tearDown();
	}

	/**
	 * Método: Buscar planetas por nombre no expuesto realizamos la consulta
	 * 
	 */
	private static void buscarPlanetName(String name) {
		try {
			final String sqlQuery = "FROM Planets where UPPER(name) like '%" + name.toUpperCase() + "%'";
			List<Planets> consultaPlanets = manejador.session.createQuery(sqlQuery).list();
			if (consultaPlanets.isEmpty()) {
				System.out.println(KConstants.Common.NOT_REGISTER + " para el nombre " + name);
			} else {
				consultaPlanets.stream().forEach(Planets::imprimeRegistroDetallado);
			}
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
	}

	/**
	 * Método: Mostrar todos los registro método expuesto
	 */
	public void showRegisters() {
		mostrarTodos(getRegisters());
	}

	/**
	 * Método: Mostrar todos los registro método no expuesto
	 * 
	 * @param consultaPlanets
	 */
	private static void mostrarTodos(List<Planets> consultaPlanets) {
		if (consultaPlanets.isEmpty()) {
			System.out.println(KConstants.Common.NOT_REGISTER);
		} else {
			Utiles.getCabeceraRegistroPlanet();
			consultaPlanets.stream().forEach(Planets::imprimeRegistro);
		}
	}

	/**
	 * Método: Obtener los registros método expuesto
	 * 
	 * @return List<Planets>
	 */
	private static List<Planets> getRegisters() {
		List<Planets> consultaPlanets = new ArrayList<Planets>();
		manejador.tearUp();
		consultaPlanets = obtenerRegistros();
		manejador.tearDown();
		System.out.println(KConstants.Common.FAIL_CONECTION);
		return consultaPlanets;
	}

	/**
	 * Método: Obtener una lista de registro
	 * 
	 * @return List<Planets>
	 */
	private static List<Planets> obtenerRegistros() {
		List<Planets> consultaPlanets = new ArrayList<Planets>();
		try {
			final String sqlQuery = "FROM Planets ORDER BY codigo";
			consultaPlanets = manejador.session.createQuery(sqlQuery).list();
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
		return consultaPlanets;
	}

	/**
	 * Método: Insert films in planets
	 * 
	 * @param films
	 */
	public static Set<Planets> getPlanets(HandlerBD manejador) {
		Set<Planets> listaPlanets = new HashSet<Planets>();
		String deseaIngresar;
		System.out.println("Desea ingresar Planetas S/N: ");
		deseaIngresar = teclado.nextLine();
		if ("S".equalsIgnoreCase(deseaIngresar.trim())) {
			listaPlanets = obtenerRegistros(manejador);
		}
		return listaPlanets;
	}

	/**
	 * Método: Obtener una lista de registro
	 * 
	 * @return List<Planets>
	 */
	private static Set<Planets> obtenerRegistros(HandlerBD manejador) {
		List<Planets> consultaPlanets = new ArrayList<Planets>();
		try {
			final String sqlQuery = "FROM Planets ORDER BY codigo";
			consultaPlanets = manejador.session.createQuery(sqlQuery).list();
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}

		return cargarPlanets(consultaPlanets);
	}

	/**
	 * Método: Carga de planet de los que hemos insertado
	 * 
	 * @param planetsInsertados
	 * @return List<Planets>
	 */
	private static Set<Planets> cargarPlanets(List<Planets> planetsInsertados) {
		if (teclado == null) {
			teclado = new Scanner(System.in);
		}
		Set<Planets> listPlanets = new HashSet<Planets>();
		Optional<Planets> planetEncontrado = Optional.empty();
		boolean valido = false;

		planetsInsertados.stream().forEach(Planets::imprimeCodValor);

		do {
			System.out.println(KConstants.Common.INSERT_CODE);
			final String codigo = teclado.nextLine();
			valido = !codigo.trim().isEmpty() && Utiles.isNumeric(codigo);

			if (valido) {
				planetEncontrado = planetsInsertados.stream().filter(n -> n.getCodigo() == Integer.valueOf(codigo))
						.findAny();

				valido = planetEncontrado.isPresent();

				if (valido) {
					listPlanets.add(planetEncontrado.get());
				} else {
					System.out.println(KConstants.Common.CODE_NOT_FOUND);
				}

				System.out.println(KConstants.Common.INSERT_OTHER);
				String otroPlaneta = teclado.nextLine();
				valido = !"S".equalsIgnoreCase(otroPlaneta.trim());
			} else {
				System.out.println(KConstants.Common.INVALID_CODE);
			}
		} while (!valido);
		return listPlanets;

	}

	/**
	 * Método: Insert planet in people
	 * 
	 * @param peopleToInsert
	 */
	public static Optional<Planets> getPlanet(HandlerBD manejador) {
		if (teclado == null) {
			teclado = new Scanner(System.in);
		}

		Optional<Planets> planet = Optional.empty();
		String deseaIngresar;
		System.out.println("Desea ingresar el Planeta del Personaje S/N: ");
		deseaIngresar = teclado.nextLine();
		if ("S".equalsIgnoreCase(deseaIngresar.trim())) {
			planet = Optional.of(obtenerRegistro(manejador));
		}
		return planet;
	}

	/**
	 * Método: Obtener una lista de registro
	 * 
	 * @return List<Planets>
	 */
	private static Planets obtenerRegistro(HandlerBD manejador) {
		List<Planets> consultaPlanets = new ArrayList<Planets>();
		try {
			final String sqlQuery = "FROM Planets ORDER BY codigo";
			consultaPlanets = manejador.session.createQuery(sqlQuery).list();
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}

		return cargarPlanet(consultaPlanets);
	}

	/**
	 * Método: Carga de Planet a partir de los insertado
	 * 
	 * @param planetsInsertados
	 * @return Planets
	 */
	private static Planets cargarPlanet(List<Planets> planetsInsertados) {

		Optional<Planets> planetEncontrado = Optional.empty();
		Planets planets = new Planets();
		boolean valido = false;

		planetsInsertados.stream().forEach(Planets::imprimeCodValor);

		do {
			System.out.println(KConstants.Common.INSERT_CODE);
			final String codigo = teclado.nextLine();
			valido = !codigo.trim().isEmpty() && Utiles.isNumeric(codigo);

			if (valido) {
				planetEncontrado = planetsInsertados.stream().filter(n -> n.getCodigo() == Integer.valueOf(codigo))
						.findAny();

				valido = planetEncontrado.isPresent();

				if (valido) {
					planets = planetEncontrado.get();
				} else {
					System.out.println(KConstants.Common.CODE_NOT_FOUND);
				}

			} else {
				System.out.println(KConstants.Common.INVALID_CODE);
			}
		} while (!valido);
		return planets;
	}
}
