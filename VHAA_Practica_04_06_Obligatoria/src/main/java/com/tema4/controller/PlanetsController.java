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

	@Override
	public void create() {
		String deseaIngresar = "";
		manejador.tearUp();
		Transaction trans = manejador.session.beginTransaction();
		Planets planetToInsert = getPlanetToInsert();

		manejador.session.save(planetToInsert);
		Set<Planets> planets = new HashSet<Planets>();
		planets.add(planetToInsert);

		System.out.println("Desea ingresar especies que pertenece al Planet S/N: ");
		deseaIngresar = teclado.nextLine();
		if ("S".equalsIgnoreCase(deseaIngresar.trim())) {
			final String sqlQuery = "FROM Species";
			List<Species> speciesInsertadas = new ArrayList<Species>();
			speciesInsertadas = manejador.session.createQuery(sqlQuery).list();
			List<Species> listaSpecies = SpeciesController.cargarEspecies(speciesInsertadas);
			if (!listaSpecies.isEmpty()) {
				listaSpecies.stream().forEach(specie -> {
					specie.setCodigo(specie.getCodigo());
					specie.setPlanets(planetToInsert);
					manejador.session.save(specie);
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
					people.setPlanets(planetToInsert);
					manejador.session.save(people);
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
					film.setPlanetses(planets);
					manejador.session.save(film);
				});
			}
		}

		trans.commit();
		System.out.println("registro ingresado...");
		manejador.tearDown();
	}

	private Planets getPlanetToInsert() {
		Planets planet = new Planets();

		boolean valido = false;
		String nombre = "";
		do {
			System.out.println("Ingrese el nombre: ");
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
		System.out.println("Ingrese la Gravedad: ");
		String gravity = teclado.nextLine();
		System.out.println("Ingrese la población: ");
		String population = teclado.nextLine();
		System.out.println("Ingrese el clima: ");
		String climate = teclado.nextLine();
		System.out.println("Ingrese los terros ");
		String terrain = teclado.nextLine();
		System.out.println("Ingrese las superficies: ");
		String surfaceWater = teclado.nextLine();
		planet.setName(nombre);
		String fechaCreación = Utiles.parseDate(new Date().toString(), KConstants.FormatDate.FORMAT_BD);
		String fechaEdicion = Utiles.parseDate(new Date().toString(), KConstants.FormatDate.FORMAT_BD);
		planet.setDiameter(diameter.isEmpty() || Utiles.isNumeric(diameter) ? KConstants.Common.UNKNOWN : diameter);
		planet.setRotationPeriod(
				rotationPeriod.isEmpty() || Utiles.isNumeric(rotationPeriod) ? KConstants.Common.UNKNOWN
						: rotationPeriod);
		planet.setOrbitalPeriod(
				orbitalPeriod.isEmpty() || Utiles.isNumeric(orbitalPeriod) ? KConstants.Common.UNKNOWN : orbitalPeriod);
		planet.setGravity(gravity.isEmpty() || Utiles.isNumeric(gravity) ? KConstants.Common.UNKNOWN : gravity);
		planet.setPopulation(
				population.isEmpty() || Utiles.isNumeric(population) ? KConstants.Common.UNKNOWN : population);
		planet.setClimate(climate.isEmpty() ? KConstants.Common.UNKNOWN : climate);
		planet.setTerrain(terrain.isEmpty() ? KConstants.Common.UNKNOWN : terrain);
		planet.setSurfaceWater(surfaceWater.isEmpty() ? KConstants.Common.UNKNOWN : surfaceWater);
		planet.setCreated(fechaCreación);
		planet.setEdited(fechaEdicion);
		return planet;
	}

	@Override
	public void delete() {
		List<Planets> listaPlanets = getRegisters();
		listaPlanets.stream().forEach(Planets::imprimeCodValor);

		Optional<Planets> planetsEncontrado = null;
		boolean valido = false;
		do {
			System.out.println("Introduce el código de Films ha eliminar");
			final String codigoABorrar = teclado.nextLine();

			if (!codigoABorrar.trim().isEmpty() && Utiles.isNumeric(codigoABorrar)) {
				planetsEncontrado = listaPlanets.stream().filter(f -> f.getCodigo() == Integer.valueOf(codigoABorrar))
						.findFirst();
				valido = true;
			} else {
				System.out.println(KConstants.Common.CODE_NOT_FOUND);
				System.out.println("Desea introducir otro S/N: ");
				String otro = teclado.nextLine();
				if ("S".equalsIgnoreCase(otro.trim())) {
					valido = false;
				} else {
					valido = true;
				}
			}
		} while (!valido);
		manejador.tearUp();
		if (valido && planetsEncontrado.isPresent()) {
			System.out.println(KConstants.Common.ARE_YOU_SURE);
			String seguro = teclado.nextLine();
			if ("S".equalsIgnoreCase(seguro.trim())) {
				Transaction trans = manejador.session.beginTransaction();
				manejador.session.delete(planetsEncontrado.get());
				trans.commit();
				System.out.println("Films borrado...");
			}
		}
		manejador.tearDown();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	public void findbyName(String name) {
		if (name.trim().isEmpty()) {
			System.out.println(KConstants.Common.NOT_DATA_FIND);
			return;
		}
		manejador = HandlerBD.getInstance();
		manejador.tearUp();
		buscarPlanetName(name);
		manejador.tearDown();
	}

	private static void buscarPlanetName(String name) {
		final String sqlQuery = "FROM Planets where UPPER(name) like '%" + name.toUpperCase() + "%'";
		try {
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

	public void showRegisters() {
		mostrarTodos(getRegisters());
	}

	private static void mostrarTodos(List<Planets> consultaPlanets) {
		if (consultaPlanets.isEmpty()) {
			System.out.println(KConstants.Common.NOT_REGISTER);
		} else {
			Utiles.getCabeceraRegistroPlanet();
			consultaPlanets.stream().forEach(Planets::imprimeRegistro);
		}
	}

	public static List<Planets> getRegisters() {
		manejador = HandlerBD.getInstance();
		manejador.tearUp();
		List<Planets> consultaPlanets = obtenerRegistros();
		manejador.tearDown();
		return consultaPlanets;
	}

	private static List<Planets> obtenerRegistros() {
		final String sqlQuery = "FROM Planets ";
		List<Planets> consultaPlanets = new ArrayList<Planets>();
		try {
			consultaPlanets = manejador.session.createQuery(sqlQuery).list();
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
		return consultaPlanets;
	}

	public static List<Planets> cargarPlanets(List<Planets> planetsInsertados) {
		if (teclado == null) {
			teclado = new Scanner(System.in);
		}
		List<Planets> listPlanets = new ArrayList<Planets>();

		planetsInsertados.stream().forEach(Planets::imprimeCodValor);

		boolean valido = false;
		Optional<Planets> planetEncontrado;
		do {
			System.out.println("Ingrese el código del Planets: ");
			final String codigo = teclado.nextLine();
			valido = !codigo.trim().isEmpty() && Utiles.isNumeric(codigo);

			if (valido) {
				planetEncontrado = planetsInsertados.stream().filter(n -> n.getCodigo() == Integer.valueOf(codigo))
						.findAny();

				if (!planetEncontrado.isPresent()) {
					valido = false;
					System.out.println("El código introducido no es válido");
				} else {
					listPlanets.add(planetEncontrado.get());

					System.out.println("Desea ingresar otra planeta S/N");
					String otroPlaneta = teclado.nextLine();
					if ("S".equalsIgnoreCase(otroPlaneta.trim())) {
						valido = false;
					} else {
						valido = true;
					}
				}
			}
		} while (!valido);
		return listPlanets;
	}

	public static Planets cargarPlanet(List<Planets> planetsInsertados) {
		if (teclado == null) {
			teclado = new Scanner(System.in);
		}
		Planets planets = new Planets();

		planetsInsertados.stream().forEach(Planets::imprimeCodValor);

		boolean valido = false;
		Optional<Planets> planetEncontrado;
		do {
			System.out.println("Ingrese el código del Planets: ");
			final String codigo = teclado.nextLine();
			valido = !codigo.trim().isEmpty() && Utiles.isNumeric(codigo);

			if (valido) {
				planetEncontrado = planetsInsertados.stream().filter(n -> n.getCodigo() == Integer.valueOf(codigo))
						.findAny();

				if (!planetEncontrado.isPresent()) {
					valido = false;
					System.out.println("El código introducido no es válido");
				} else {
					planets = planetEncontrado.get();
					valido = true;
				}
			}
		} while (!valido);
		return planets;
	}

}
