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
			specieToInsert = getRelations(specieToInsert);

			manejador.session.save(specieToInsert);
			trans.commit();

			System.out.println("Especie ingresada...");
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
		manejador.tearUp();
		try {
			Optional<Species> speciesEncontrado = getSpecieFromInsert();

			if (speciesEncontrado.isPresent()) {
				System.out.println(KConstants.Common.ARE_YOU_SURE);
				String seguro = teclado.nextLine();
				if ("S".equalsIgnoreCase(seguro.trim())) {
					Transaction trans = manejador.session.beginTransaction();
					manejador.session.delete(speciesEncontrado.get());
					trans.commit();
					System.out.println("Especie borrada...");
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
	 * Modificación de un registro del tipo Species
	 */
	public void update() {
		manejador.tearUp();
		try {
			Optional<Species> speciesEncontrado = getSpecieFromInsert();

			if (speciesEncontrado.isPresent()) {
				Transaction trans = manejador.session.beginTransaction();

				Species specieToUpdate = getSpecieToUpdate(speciesEncontrado.get());
				specieToUpdate = getRelations(specieToUpdate);

				manejador.session.update(specieToUpdate);
				trans.commit();

				System.out.println("Especie modificada...");
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
	private Species getRelations(Species species) {
		Set<People> selectedPeople = PeopleController.getPeoples(manejador);
		if (!selectedPeople.isEmpty())
			species.setPeoples(selectedPeople);

		Optional<Planets> selectedPlanet = PlanetsController.getPlanet(manejador);
		if (selectedPlanet.isPresent()) {
			species.setPlanets(selectedPlanet.get());
		}

		return species;
	}

	/**
	 * Método: Crea un objeto del tipo Species con los datos introducidos por el
	 * usuario
	 * 
	 * @return Species
	 */
	private Species getSpecieToInsert() {
		Species specie = new Species();
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
		String clasificacion = "";
		do {
			System.out.println("Ingrese la clasificación(obligatorio): ");
			clasificacion = teclado.nextLine();

			valido = !clasificacion.trim().isEmpty();
			if (!valido) {
				System.out.println(KConstants.Common.NOT_VALID_DATA);
			}

		} while (!valido);
		String designacion = "";
		do {
			System.out.println("Ingrese la designación(obligatorio): ");
			designacion = teclado.nextLine();

			valido = !designacion.trim().isEmpty();
			if (!valido) {
				System.out.println(KConstants.Common.NOT_VALID_DATA);
			}

		} while (!valido);
		System.out.println("Ingrese la altura promedia(obligatorio): ");
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
		System.out.println("Ingrese la esperanza de vida(obligatorio): ");
		String esperanza = teclado.nextLine();
		if (!esperanza.isEmpty()) {
			do {
				valido = Utiles.isNumeric(esperanza);
				if (!valido) {
					System.out.println(KConstants.Common.NOT_VALID_DATA);
					System.out.println("Ingrese la esperanza de vida numérica: ");
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

	/**
	 * Método: Obtiene un objeto del tipo species de los insertados
	 * 
	 * @return Optional<Species>
	 */
	private static Optional<Species> getSpecieFromInsert() {
		Optional<Species> speciesEncontrado = Optional.empty();

		List<Species> listaSpecies = obtenerRegistros();
		listaSpecies.stream().forEach(Species::imprimeCodValor);

		boolean valido = false;
		do {
			System.out.println("Introduce el código de la Película: ");
			final String codigoABorrar = teclado.nextLine();

			if (!codigoABorrar.trim().isEmpty() && Utiles.isNumeric(codigoABorrar)) {
				speciesEncontrado = listaSpecies.stream().filter(f -> f.getCodigo() == Integer.valueOf(codigoABorrar))
						.findFirst();
				valido = speciesEncontrado.isPresent();
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

	/**
	 * Método: Modifica un objeto del tipo species con los datos del usuario a
	 * partir del objeto recuperado
	 * 
	 * @param species
	 * @return Species
	 */
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

	/**
	 * Método: Busca un registro, método expuesto
	 */
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

	/**
	 * Método: Busca un registro, método no expuesto
	 * 
	 * @param name
	 */
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

	/**
	 * Método: Obtiene las species si people, método expuesto
	 */
	public void getSpeciesWithoutPeople() {
		manejador.tearUp();
		mostrarEspeciesSinPersonajes();
		manejador.tearDown();
	}

	/**
	 * Método: Obtiene las species si people, método no expuesto
	 */
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

	/**
	 * Método: Imprime los registro del tipo species, método expuesto
	 */
	public void showRegisters() {
		mostrarTodos(getRegisters());
	}

	/**
	 * Método: Imprime los registro del tipo species, método no expuesto
	 */
	private static void mostrarTodos(List<Species> consultaSpecies) {
		if (consultaSpecies.isEmpty()) {
			System.out.println(KConstants.Common.NOT_REGISTER);
		} else {
			Utiles.getCabeceraRegistroSpecie();
			consultaSpecies.stream().forEach(Species::imprimeRegistro);
		}
	}

	/**
	 * Método: Obtiene una lista de Species, método no expuesto
	 */
	private static List<Species> getRegisters() {
		manejador.tearUp();
		List<Species> consultaSpecies = obtenerRegistros();
		manejador.tearDown();
		return consultaSpecies;
	}

	/**
	 * Método: Obtiene una lista de Species, método no expuesto
	 * 
	 * @param manejador
	 */
	private static List<Species> obtenerRegistros() {
		List<Species> consultaSpecies = new ArrayList<Species>();
		try {
			final String sqlQuery = "FROM Species ORDER BY codigo";
			consultaSpecies = manejador.session.createQuery(sqlQuery).list();
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
		return consultaSpecies;
	}

	/**
	 * Método: Carga una especies a partir de la lista de especies insertadas
	 * 
	 * @param speciesInsertadas
	 * @return Species
	 */
	public static Species cargarEspecie(List<Species> speciesInsertadas) {
		if (teclado == null) {
			teclado = new Scanner(System.in);
		}
		Optional<Species> planetEncontrado = Optional.empty();
		Species species = new Species();
		boolean valido = false;

		speciesInsertadas.stream().forEach(Species::imprimeCodValor);

		do {
			System.out.println(KConstants.Common.INSERT_CODE);
			final String codigo = teclado.nextLine();
			valido = !codigo.trim().isEmpty() && Utiles.isNumeric(codigo);

			if (valido) {
				planetEncontrado = speciesInsertadas.stream().filter(n -> n.getCodigo() == Integer.valueOf(codigo))
						.findAny();

				valido = planetEncontrado.isPresent();

				if (valido) {
					species = planetEncontrado.get();
				} else {
					System.out.println(KConstants.Common.CODE_NOT_FOUND);
				}

			} else {
				System.out.println(KConstants.Common.INVALID_CODE);
			}
		} while (!valido);
		return species;
	}

	/**
	 * Método: Insert species in people
	 * 
	 * @param peoples
	 */
	public static Set<Species> getSpecies(HandlerBD manejador) {
		if (teclado == null) {
			teclado = new Scanner(System.in);
		}
		Set<Species> listaSpecies = new HashSet<Species>();
		String deseaIngresar;
		System.out.println("Desea ingresar Especies en el Personaje S/N: ");
		deseaIngresar = teclado.nextLine();
		if ("S".equalsIgnoreCase(deseaIngresar.trim())) {
			listaSpecies = obtenerRegistrosSet(manejador);
		}
		return listaSpecies;
	}

	/**
	 * Método: Obtiene una lista de Species, método expuesto
	 * 
	 * @param HandlerBD
	 */
	private static Set<Species> obtenerRegistrosSet(HandlerBD manejador) {
		List<Species> consultaSpecies = new ArrayList<Species>();
		try {
			final String sqlQuery = "FROM Species ORDER BY codigo";
			consultaSpecies = manejador.session.createQuery(sqlQuery).list();
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
		return cargarEspeciesSet(consultaSpecies);
	}

	/**
	 * Método: Carga una lista especies a partir de la lista de especies insertadas
	 * 
	 * @param speciesInsertadas
	 * @return List<Species>
	 */
	private static Set<Species> cargarEspeciesSet(List<Species> speciesInsertadas) {

		Set<Species> listaSpecies = new HashSet<Species>();
		Optional<Species> especieEncontrada = Optional.empty();
		boolean valido = false;

		speciesInsertadas.stream().forEach(Species::imprimeCodValor);

		do {
			System.out.println(KConstants.Common.INSERT_CODE);
			final String codigo = teclado.nextLine();
			valido = !codigo.trim().isEmpty() && Utiles.isNumeric(codigo);

			if (valido) {
				especieEncontrada = speciesInsertadas.stream().filter(n -> n.getCodigo() == Integer.valueOf(codigo))
						.findAny();

				valido = especieEncontrada.isPresent();

				if (valido) {
					listaSpecies.add(especieEncontrada.get());
				} else {
					System.out.println(KConstants.Common.CODE_NOT_FOUND);
				}

				System.out.println(KConstants.Common.INSERT_OTHER);
				String otraNave = teclado.nextLine();
				valido = !"S".equalsIgnoreCase(otraNave.trim());

			} else {
				System.out.println(KConstants.Common.INVALID_CODE);
			}
		} while (!valido);
		return listaSpecies;
	}

}
