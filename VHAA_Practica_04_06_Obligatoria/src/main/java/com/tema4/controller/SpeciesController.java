package com.tema4.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import org.hibernate.Transaction;

import com.tema4.constants.KConstants;
import com.tema4.models.People;
import com.tema4.models.Planets;
import com.tema4.models.Species;
import com.tema4.services.HandlerBD;
import com.tema4.utils.Utiles;

/**
 * Controlador de SpeciesController
 * 
 * Clase que controla el CRUD completo para la clase Species
 * 
 * @author Victor Hugo Aguilar Aguilar
 *
 */
@SuppressWarnings("unchecked")
public class SpeciesController implements ICRUDController {
	private static SpeciesController speciesController;
	private static HandlerBD manejador = null;
	private static Scanner teclado;

	SpeciesController() {
		teclado = new Scanner(System.in);
		manejador = HandlerBD.getInstance();
	}

	public static SpeciesController getInstance() {
		if (speciesController == null) {
			speciesController = new SpeciesController();
			return speciesController;
		}
		return speciesController;
	}

	/**
	 * Método: Create
	 * 
	 * Creación de un registro del tipo Species con sus relaciones
	 */
	public void create() {
		manejador.tearUp();
		try {
			Transaction trans = manejador.session.beginTransaction();

			Species specieToInsert = getSpecieToInsert();

			insertSpeciePlanet(specieToInsert);

			manejador.session.save(specieToInsert);
			Set<Species> species = new HashSet<Species>();
			species.add(specieToInsert);

			insertSpeciePeople(species);

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
	 * Eliminación de un registro del tipo Species no elimina sus relaciones
	 */
	public void delete() {
		try {
			manejador.tearUp();
			Optional<Species> speciesEncontrado = getSpecieFromInsert();

			if (speciesEncontrado.isPresent()) {
				System.out.println(KConstants.Common.ARE_YOU_SURE);
				String seguro = teclado.nextLine();
				if ("S".equalsIgnoreCase(seguro.trim())) {
					Transaction trans = manejador.session.beginTransaction();
					manejador.session.delete(speciesEncontrado.get());
					trans.commit();
					System.out.println("Films borrado...");
				}
			}
			manejador.tearDown();
		} catch (PersistenceException e) {
			System.err.println("Error en el borrado por contener una clave foránea");
		} catch (Exception e) {
			System.err.println(KConstants.Common.FAIL_CONECTION);
		}
	}

	/**
	 * Método: Update
	 * 
	 * Modificación de un registro del tipo Species
	 */
	public void update() {
		manejador.tearUp();
		try {
			Optional<Species> speciesEncontrado = getSpecieFromInsert();

			if (speciesEncontrado.isPresent()) {
				Transaction trans = manejador.session.beginTransaction();

				Species specieToUpdate = getSpecieToUpdate(speciesEncontrado.get());

				insertSpeciePlanet(specieToUpdate);

				manejador.session.save(specieToUpdate);
				Set<Species> species = new HashSet<Species>();
				species.add(specieToUpdate);

				insertSpeciePeople(species);

				trans.commit();
				System.out.println("registro ingresado...");
			}
		} catch (PersistenceException e) {
			System.err.println("Error en la consulta de actualización a la BD");
		} catch (Exception e) {
			System.err.println(KConstants.Common.FAIL_CONECTION);
		}
		manejador.tearDown();
	}

	private void insertSpeciePeople(Set<Species> species) {
		String deseaIngresar;
		System.out.println("Desea ingresar people que pertenece a la especie S/N: ");
		deseaIngresar = teclado.nextLine();
		if ("S".equalsIgnoreCase(deseaIngresar.trim())) {
			final String sqlQuery = "FROM People";
			List<People> peoplesInsertadas = new ArrayList<People>();
			peoplesInsertadas = manejador.session.createQuery(sqlQuery).list();
			List<People> listaPeoples = PeopleController.cargarPeoples(peoplesInsertadas);
			if (!listaPeoples.isEmpty()) {
				listaPeoples.stream().forEach(people -> {
					people.setCodigo(people.getCodigo());
					people.setSpecieses(species);
					manejador.session.save(people);
				});
			}
		}
	}

	private void insertSpeciePlanet(Species specieToInsert) {
		String deseaIngresar;
		System.out.println("Desea ingresar el planeta de la especie S/N: ");
		deseaIngresar = teclado.nextLine();
		if ("S".equalsIgnoreCase(deseaIngresar.trim())) {
			final String sqlQuery = "FROM Planets";
			List<Planets> planetsInsertados = new ArrayList<Planets>();
			planetsInsertados = manejador.session.createQuery(sqlQuery).list();
			Planets planet = PlanetsController.cargarPlanet(planetsInsertados);
			if (planet != null) {
				specieToInsert.setPlanets(planet);
			}
		}
	}

	private Species getSpecieToInsert() {
		Species specie = new Species();
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
		String clasificacion = "";
		do {
			System.out.println("Ingrese la clasificación: ");
			clasificacion = teclado.nextLine();

			valido = !clasificacion.trim().isEmpty();
			if (!valido) {
				System.out.println(KConstants.Common.NOT_VALID_DATA);
			}

		} while (!valido);
		String designacion = "";
		do {
			System.out.println("Ingrese la designación: ");
			designacion = teclado.nextLine();

			valido = !designacion.trim().isEmpty();
			if (!valido) {
				System.out.println(KConstants.Common.NOT_VALID_DATA);
			}

		} while (!valido);
		System.out.println("Ingrese la altura promedia: ");
		String altura = teclado.nextLine();
		if (!altura.isEmpty()) {
			do {
				valido = Utiles.isNumeric(altura);
				if (!valido) {
					System.out.println(KConstants.Common.NOT_VALID_DATA);
					System.out.println("Ingrese la altura numérica: ");
					altura = teclado.nextLine();
				}

			} while (!valido);
		}
		System.out.println("Ingrese la esperanza de vida: ");
		String esperanza = teclado.nextLine();
		if (!esperanza.isEmpty()) {
			do {
				valido = Utiles.isNumeric(esperanza);
				if (!valido) {
					System.out.println(KConstants.Common.NOT_VALID_DATA);
					System.out.println("Ingrese la esperanza numérica: ");
					esperanza = teclado.nextLine();
				}

			} while (!valido);
		}
		System.out.println("Ingrese los colores de ojos: ");
		String colorOjos = teclado.nextLine();
		System.out.println("Ingrese los colores de pelo: ");
		String colorPelo = teclado.nextLine();
		System.out.println("Ingrese los colores de piel: ");
		String colorPiel = teclado.nextLine();
		System.out.println("Ingrese el lenguaje: ");
		String lenguaje = teclado.nextLine();
		String fechaCreación = Utiles.parseDate(new Date().toString(), KConstants.FormatDate.FORMAT_BD);
		String fechaEdicion = Utiles.parseDate(new Date().toString(), KConstants.FormatDate.FORMAT_BD);

		specie.setName(nombre);
		specie.setClassification(clasificacion);
		specie.setDesignation(designacion);
		specie.setAverageHeight(Utiles.controlData(altura, true, true));
		specie.setAverageLifespan(Utiles.controlData(esperanza, true, true));
		specie.setEyeColors(Utiles.controlData(colorOjos, true, false));
		specie.setHairColors(Utiles.controlData(colorPelo, true, false));
		specie.setSkinColors(Utiles.controlData(colorPiel, true, false));
		specie.setLanguage(Utiles.controlData(lenguaje, true, false));
		specie.setCreated(fechaCreación);
		specie.setEdited(fechaEdicion);

		return specie;
	}

	private static Optional<Species> getSpecieFromInsert() {
		Optional<Species> speciesEncontrado = Optional.empty();

		List<Species> listaSpecies = obtenerRegistros();
		listaSpecies.stream().forEach(Species::imprimeCodValor);

		boolean valido = false;
		do {
			System.out.println("Introduce el código de Films: ");
			final String codigoABorrar = teclado.nextLine();

			if (!codigoABorrar.trim().isEmpty() && Utiles.isNumeric(codigoABorrar)) {
				speciesEncontrado = listaSpecies.stream().filter(f -> f.getCodigo() == Integer.valueOf(codigoABorrar))
						.findFirst();
				valido = speciesEncontrado.isPresent() ? true : false;
			}
			if (!valido) {
				System.out.println(KConstants.Common.CODE_NOT_FOUND);
				System.out.println(KConstants.Common.INSERT_OTHER);
				String otro = teclado.nextLine();
				valido = !"S".equalsIgnoreCase(otro.trim());
			}
		} while (!valido);
		return speciesEncontrado;
	}

	private Species getSpecieToUpdate(Species species) {
		boolean valido = false;
		System.out.println("Ingrese el nombre: ");
		String nombre = teclado.nextLine();
		System.out.println("Ingrese la clasificación: ");
		String clasificacion = teclado.nextLine();
		System.out.println("Ingrese la designación: ");
		String designacion = teclado.nextLine();
		System.out.println("Ingrese la altura promedia: ");
		String altura = teclado.nextLine();
		if (!altura.isEmpty()) {
			do {
				valido = Utiles.isNumeric(altura);
				if (!valido) {
					System.out.println(KConstants.Common.NOT_VALID_DATA);
					System.out.println("Ingrese la altura numérica: ");
					altura = teclado.nextLine();
				}

			} while (!valido);
		}
		System.out.println("Ingrese la esperanza de vida: ");
		String esperanza = teclado.nextLine();
		if (!esperanza.isEmpty()) {
			do {
				valido = Utiles.isNumeric(esperanza);
				if (!valido) {
					System.out.println(KConstants.Common.NOT_VALID_DATA);
					System.out.println("Ingrese la esperanza numérica: ");
					esperanza = teclado.nextLine();
				}

			} while (!valido);
		}
		System.out.println("Ingrese los colores de ojos: ");
		String colorOjos = teclado.nextLine();
		System.out.println("Ingrese los colores de pelo: ");
		String colorPelo = teclado.nextLine();
		System.out.println("Ingrese los colores de piel: ");
		String colorPiel = teclado.nextLine();
		System.out.println("Ingrese el lenguaje: ");
		String lenguaje = teclado.nextLine();
		String fechaEdicion = Utiles.parseDate(new Date().toString(), KConstants.FormatDate.FORMAT_BD);

		if (!nombre.isEmpty())
			species.setName(nombre);
		if (!clasificacion.isEmpty())
			species.setClassification(clasificacion);
		if (!designacion.isEmpty())
			species.setDesignation(designacion);
		if (!altura.isEmpty())
			species.setAverageHeight(altura);
		if (!esperanza.isEmpty())
			species.setAverageLifespan(esperanza);
		if (!colorOjos.isEmpty())
			species.setEyeColors(colorOjos);
		if (!colorPelo.isEmpty())
			species.setHairColors(colorPelo);
		if (!colorPiel.isEmpty())
			species.setSkinColors(colorPiel);
		if (!lenguaje.isEmpty())
			species.setLanguage(lenguaje);
		species.setEdited(fechaEdicion);

		return species;
	}

	public void findbyName(String name) {
		if (name.trim().isEmpty()) {
			System.out.println(KConstants.Common.NOT_DATA_FIND);
			return;
		}
		try {
			manejador.tearUp();
			buscarSpecieName(name);
			manejador.tearDown();
		} catch (PersistenceException e) {
			System.err.println("Fallo en el insert en la BD");
		} catch (Exception e) {
			System.err.println(KConstants.Common.FAIL_CONECTION);
		}
	}

	private static void buscarSpecieName(String name) {
		final String sqlQuery = "FROM Species where UPPER(name) like '%" + name.toUpperCase() + "%'";
		try {
			List<Species> consultaSpecies = manejador.session.createQuery(sqlQuery).list();
			if (consultaSpecies.isEmpty()) {
				System.out.println(KConstants.Common.NOT_REGISTER + " para el nombre " + name);
			} else {
				consultaSpecies.stream().forEach(Species::imprimeRegistroDetallado);
			}
		} catch (Exception e) {
			System.err.println(KConstants.Common.FAIL_CONECTION);
		}
	}

	public void getSpeciesWithoutPeople() {
		manejador.tearUp();
		mostrarEspeciesSinPersonajes();
		manejador.tearDown();
	}

	private static void mostrarEspeciesSinPersonajes() {
		try {
			final String sqlQuery = "FROM Species";
			List<Species> consultaSpecies = manejador.session.createQuery(sqlQuery).list();
			List<Species> speciesSinPersonajes = consultaSpecies.stream().filter(s -> s.getPeoples().isEmpty())
					.collect(Collectors.toList());

			if (speciesSinPersonajes.isEmpty()) {
				System.out.println(KConstants.Common.NOT_REGISTER);
			} else {
				Utiles.getCabeceraRegistroSpecie();
				speciesSinPersonajes.stream().forEach(Species::imprimeRegistro);
			}
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
	}

	public void showRegisters() {
		mostrarTodos(getRegisters());
	}

	private static void mostrarTodos(List<Species> consultaSpecies) {
		if (consultaSpecies.isEmpty()) {
			System.out.println(KConstants.Common.NOT_REGISTER);
		} else {
			Utiles.getCabeceraRegistroSpecie();
			consultaSpecies.stream().forEach(Species::imprimeRegistro);
		}
	}

	public static List<Species> getRegisters() {
		manejador.tearUp();
		List<Species> consultaSpecies = obtenerRegistros();
		manejador.tearDown();
		return consultaSpecies;
	}

	private static List<Species> obtenerRegistros() {
		List<Species> consultaSpecies = new ArrayList<Species>();
		try {
			final String sqlQuery = "FROM Species";
			consultaSpecies = manejador.session.createQuery(sqlQuery).list();
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
		return consultaSpecies;
	}

	public static List<Species> cargarEspecies(List<Species> speciesInsertadas) {
		if (teclado == null) {
			teclado = new Scanner(System.in);
		}
		List<Species> listaSpecies = new ArrayList<Species>();
		boolean valido = false;

		speciesInsertadas.stream().forEach(Species::imprimeCodValor);

		Optional<Species> especieEncontrada;
		do {
			System.out.println("Ingrese el código de Specie: ");
			final String codigo = teclado.nextLine();
			valido = !codigo.trim().isEmpty() && Utiles.isNumeric(codigo);

			if (valido) {
				especieEncontrada = speciesInsertadas.stream().filter(n -> n.getCodigo() == Integer.valueOf(codigo))
						.findAny();

				if (!especieEncontrada.isPresent()) {
					valido = false;
					System.out.println("El código introducido no es válido");
				} else {
					listaSpecies.add(especieEncontrada.get());

					System.out.println("Desea ingresar otra Specie S/N");
					String otraNave = teclado.nextLine();
					if ("S".equalsIgnoreCase(otraNave.trim())) {
						valido = false;
					} else {
						valido = true;
					}
				}
			}
		} while (!valido);
		return listaSpecies;
	}

	public static Species cargarEspecie(List<Species> speciesInsertadas) {
		if (teclado == null) {
			teclado = new Scanner(System.in);
		}
		Species species = new Species();

		speciesInsertadas.stream().forEach(Species::imprimeCodValor);

		boolean valido = false;
		Optional<Species> planetEncontrado;
		do {
			System.out.println("Ingrese el código del Planets: ");
			final String codigo = teclado.nextLine();
			valido = !codigo.trim().isEmpty() && Utiles.isNumeric(codigo);

			if (valido) {
				planetEncontrado = speciesInsertadas.stream().filter(n -> n.getCodigo() == Integer.valueOf(codigo))
						.findAny();

				if (!planetEncontrado.isPresent()) {
					valido = false;
					System.out.println("El código introducido no es válido");
				} else {
					species = planetEncontrado.get();
					valido = true;
				}
			}
		} while (!valido);
		return species;
	}

}
