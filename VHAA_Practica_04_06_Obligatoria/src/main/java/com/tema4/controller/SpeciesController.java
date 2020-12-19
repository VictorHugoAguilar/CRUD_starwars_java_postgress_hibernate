package com.tema4.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.Transaction;

import com.tema4.constants.KConstants;
import com.tema4.models.People;
import com.tema4.models.Planets;
import com.tema4.models.Species;
import com.tema4.services.HandlerBD;
import com.tema4.utils.Utiles;

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

	@Override
	public void create() {
		String deseaIngresar = "";
		manejador.tearUp();
		Transaction trans = manejador.session.beginTransaction();

		Species specieToInsert = getSpecieToInsert();

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

		manejador.session.save(specieToInsert);
		Set<Species> species = new HashSet<Species>();
		species.add(specieToInsert);

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

		trans.commit();
		System.out.println("registro ingresado...");
		manejador.tearDown();
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
		System.out.println("Ingrese la esperanza de vida: ");
		String esperanza = teclado.nextLine();
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
		specie.setAverageHeight(altura.isEmpty() || !Utiles.isNumeric(altura) ? KConstants.Common.UNKNOWN : altura);
		specie.setAverageLifespan(
				esperanza.isEmpty() || !Utiles.isNumeric(esperanza) ? KConstants.Common.UNKNOWN : esperanza);
		specie.setEyeColors(colorOjos.isEmpty() ? KConstants.Common.UNKNOWN : colorOjos);
		specie.setHairColors(colorPelo.isEmpty() ? KConstants.Common.UNKNOWN : colorPelo);
		specie.setSkinColors(colorPiel.isEmpty() ? KConstants.Common.UNKNOWN : colorPiel);
		specie.setLanguage(lenguaje.isEmpty() ? KConstants.Common.UNKNOWN : lenguaje);
		specie.setCreated(fechaCreación);
		specie.setEdited(fechaEdicion);

		return specie;
	}

	@Override
	public void delete() {
		List<Species> listaSpecies = getRegisters();
		listaSpecies.stream().forEach(Species::imprimeCodValor);

		Optional<Species> speciesEncontrado = null;
		boolean valido = false;
		do {
			System.out.println("Introduce el código de Films ha eliminar");
			final String codigoABorrar = teclado.nextLine();

			if (!codigoABorrar.trim().isEmpty() && Utiles.isNumeric(codigoABorrar)) {
				speciesEncontrado = listaSpecies.stream().filter(f -> f.getCodigo() == Integer.valueOf(codigoABorrar))
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
		if (valido && speciesEncontrado.isPresent()) {
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
		manejador.tearUp();
		buscarSpecieName(name);
		manejador.tearDown();
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
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
	}

	public void getSpeciesWithoutPeople() {
		manejador.tearUp();
		mostrarEspeciesSinPersonajes();
		manejador.tearDown();
	}

	private static void mostrarEspeciesSinPersonajes() {
		final String sqlQuery = "FROM Species";
		try {
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
		final String sqlQuery = "FROM Species";
		List<Species> consultaSpecies = new ArrayList<Species>();
		try {
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
			System.out.println("Ingrese el código del People: ");
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
