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
import com.tema4.models.Starships;
import com.tema4.services.HandlerBD;
import com.tema4.utils.Utiles;

/**
 * Controlador de StarshipsController
 * 
 * Clase que controla el CRUD completo para la clase Starships
 * 
 * @author Victor Hugo Aguilar Aguilar
 *
 */
@SuppressWarnings("unchecked")
public class StarshipsController implements ICRUDController {
	private static StarshipsController startshipsController;
	private static HandlerBD manejador;
	private static Scanner teclado;

	StarshipsController() {
		teclado = new Scanner(System.in);
		manejador = HandlerBD.getInstance();
	}

	public static StarshipsController getInstance() {
		if (startshipsController == null) {
			startshipsController = new StarshipsController();
			return startshipsController;
		}
		return startshipsController;
	}

	/**
	 * Método: Create
	 * 
	 * Creación de un registro del tipo Starships con sus relaciones
	 */
	public void create() {
		try {
			manejador.tearUp();

			Transaction trans = manejador.session.beginTransaction();

			Starships starshipToInsert = getStarshipToInsert();
			starshipToInsert = getRelations(starshipToInsert);

			manejador.session.save(starshipToInsert);
			trans.commit();

			System.out.println("Nave ingresada...");
			manejador.tearDown();
		} catch (PersistenceException e) {
			System.err.println("Fallo en el insert en la BD");
		} catch (Exception e) {
			System.err.println(KConstants.Common.FAIL_CONECTION);
		}
	}

	/**
	 * Método: Delete
	 * 
	 * Eliminación de un registro del tipo Starships no elimina sus relaciones
	 */
	public void delete() {
		manejador.tearUp();
		try {
			Optional<Starships> starshipEncontrado = getStarshipFromInserts();

			if (starshipEncontrado.isPresent()) {
				System.out.println(KConstants.Common.ARE_YOU_SURE);
				String seguro = teclado.nextLine();
				if ("S".equalsIgnoreCase(seguro.trim())) {
					Transaction trans = manejador.session.beginTransaction();
					manejador.session.delete(starshipEncontrado.get());
					trans.commit();
					System.out.println("Nave borrada...");
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
	 * Modificación de un registro del tipo Starships
	 */
	public void update() {
		manejador.tearUp();
		try {

			Optional<Starships> starshipEncontrado = getStarshipFromInserts();

			if (starshipEncontrado.isPresent()) {
				Transaction trans = manejador.session.beginTransaction();

				Starships starshipToUpdate = getStarshipToUpdate(starshipEncontrado.get());
				starshipToUpdate = getRelations(starshipToUpdate);

				manejador.session.update(starshipToUpdate);
				trans.commit();

				System.out.println("Nave modificada...");
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
	private Starships getRelations(Starships starships) {
		Set<People> selectedPeople = PeopleController.getPeoples(manejador);
		if (!selectedPeople.isEmpty())
			starships.setPeoples(selectedPeople);

		Set<Films> selectedFilms = FilmsController.getFilms(manejador);
		if (!selectedFilms.isEmpty())
			starships.setFilmses(selectedFilms);

		return starships;
	}

	/**
	 * Método: Crea un objeto del tipo Starships con los datos introducidos por el
	 * usuario
	 * 
	 * @return Starships
	 */
	private Starships getStarshipToInsert() {
		Starships starships = new Starships();
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

		String modelo = "";
		do {
			System.out.println("Ingrese el modelo(obligatorio): ");
			modelo = teclado.nextLine();

			valido = !modelo.trim().isEmpty();
			if (!valido) {
				System.out.println(KConstants.Common.NOT_VALID_DATA);
			}

		} while (!valido);

		String clase = "";
		do {
			System.out.println("Ingrese la clase(obligatorio): ");
			clase = teclado.nextLine();

			valido = !clase.trim().isEmpty();
			if (!valido) {
				System.out.println(KConstants.Common.NOT_VALID_DATA);
			}

		} while (!valido);

		String fabricado = "";
		do {
			System.out.println("Ingrese la empresa de fabricación(obligatorio): ");
			fabricado = teclado.nextLine();

			valido = !fabricado.trim().isEmpty();
			if (!valido) {
				System.out.println(KConstants.Common.NOT_VALID_DATA);
			}

		} while (!valido);

		System.out.println("Ingrese el coste: ");
		String coste = teclado.nextLine();
		System.out.println("Ingrese el largo: ");
		String largo = teclado.nextLine();
		System.out.println("Ingrese el número de tripulacion: ");
		String tripulacion = teclado.nextLine();
		System.out.println("Ingrese el número de pasajeros: ");
		String pasajeros = teclado.nextLine();
		System.out.println("Ingrese la velocidad máxima: ");
		String velocidadMax = teclado.nextLine();
		System.out.println("Ingrese el hyperDriver: ");
		String hyperdriver = teclado.nextLine();
		System.out.println("Ingrese el MLGT: ");
		String mglts = teclado.nextLine();
		System.out.println("Ingrese la capacidad: ");
		String capacidad = teclado.nextLine();
		System.out.println("Ingrese las duracion de los consumibles: ");
		String consumibles = teclado.nextLine();
		String fechaCreación = Utiles.parseDate(new Date().toString(), KConstants.FormatDate.FORMAT_BD);
		String fechaEdicion = Utiles.parseDate(new Date().toString(), KConstants.FormatDate.FORMAT_BD);

		starships.setName(nombre);
		starships.setModel(modelo);
		starships.setStarshipClass(clase);
		starships.setManufacturer(fabricado);
		starships.setCostInCredits(Utiles.controlData(coste, true, true));
		starships.setLength(Utiles.controlData(largo, true, true));
		starships.setCrew(Utiles.controlData(tripulacion, true, true));
		starships.setPassengers(Utiles.controlData(pasajeros, true, true));
		starships.setMaxAtmospheringSpeed(Utiles.controlData(velocidadMax, true, true));
		starships.setHyperdriveRating(Utiles.controlData(hyperdriver, true, true));
		starships.setMglt(Utiles.controlData(mglts, true, true));
		starships.setCargoCapacity(Utiles.controlData(capacidad, true, true));
		starships.setConsumables(Utiles.controlData(consumibles, true, false));
		starships.setCreated(fechaCreación);
		starships.setEdited(fechaEdicion);

		return starships;
	}

	/**
	 * Método: Obtiene un objeto del tipo Starships de los insertados
	 * 
	 * @return Optional<Starships>
	 */
	private Optional<Starships> getStarshipFromInserts() {
		Optional<Starships> starshipEncontrado = Optional.empty();

		List<Starships> listaStarship = obtenerRegistros();
		listaStarship.stream().forEach(Starships::imprimeCodValor);

		boolean valido = false;
		do {
			System.out.println("Introduce el código de Nave: ");
			final String codigoABorrar = teclado.nextLine();

			if (!codigoABorrar.trim().isEmpty() && Utiles.isNumeric(codigoABorrar)) {
				starshipEncontrado = listaStarship.stream().filter(f -> f.getCodigo() == Integer.valueOf(codigoABorrar))
						.findFirst();
				valido = starshipEncontrado.isPresent();
			}
			if (!valido) {
				System.out.println(KConstants.Common.CODE_NOT_FOUND);
				System.out.println(KConstants.Common.INSERT_OTHER);
				String otro = teclado.nextLine();
				valido = !"S".equalsIgnoreCase(otro.trim());
			}
		} while (!valido);
		return starshipEncontrado;
	}

	/**
	 * Método: Modifica un objeto del tipo Starships con los datos del usuario a
	 * partir del objeto recuperado
	 * 
	 * @param Starships
	 * @return Starships
	 */
	private Starships getStarshipToUpdate(Starships starships) {
		System.out.println("Ingrese el nombre: ");
		String nombre = teclado.nextLine();
		System.out.println("Ingrese el modelo: ");
		String modelo = teclado.nextLine();
		System.out.println("Ingrese la clase: ");
		String clase = teclado.nextLine();
		System.out.println("Ingrese la empresa de fabricación: ");
		String fabricado = teclado.nextLine();

		System.out.println("Ingrese el coste: ");
		String coste = teclado.nextLine();
		System.out.println("Ingrese el largo: ");
		String largo = teclado.nextLine();
		System.out.println("Ingrese el número de tripulacion: ");
		String tripulacion = teclado.nextLine();
		System.out.println("Ingrese el número de pasajeros: ");
		String pasajeros = teclado.nextLine();
		System.out.println("Ingrese la velocidad máxima: ");
		String velocidadMax = teclado.nextLine();
		System.out.println("Ingrese el hyperDriver: ");
		String hyperdriver = teclado.nextLine();
		System.out.println("Ingrese el MLGT: ");
		String mglts = teclado.nextLine();
		System.out.println("Ingrese la capacidad: ");
		String capacidad = teclado.nextLine();
		System.out.println("Ingrese las duracion de los consumibles: ");
		String consumibles = teclado.nextLine();

		String fechaEdicion = Utiles.parseDate(new Date().toString(), KConstants.FormatDate.FORMAT_BD);

		if (!nombre.isEmpty())
			starships.setName(nombre);
		if (!modelo.isEmpty())
			starships.setModel(modelo);
		if (!clase.isEmpty())
			starships.setStarshipClass(clase);
		if (!fabricado.isEmpty())
			starships.setManufacturer(fabricado);
		if (!coste.isEmpty())
			starships.setCostInCredits(Utiles.controlData(coste, true, true));
		if (!largo.isEmpty())
			starships.setLength(Utiles.controlData(largo, true, true));
		if (!tripulacion.isEmpty())
			starships.setCrew(Utiles.controlData(tripulacion, true, true));
		if (!pasajeros.isEmpty())
			starships.setPassengers(Utiles.controlData(pasajeros, true, true));
		if (!velocidadMax.isEmpty())
			starships.setMaxAtmospheringSpeed(Utiles.controlData(velocidadMax, true, true));
		if (!hyperdriver.isEmpty())
			starships.setHyperdriveRating(Utiles.controlData(hyperdriver, true, true));
		if (!mglts.isEmpty())
			starships.setMglt(Utiles.controlData(mglts, true, true));
		if (!capacidad.isEmpty())
			starships.setCargoCapacity(Utiles.controlData(capacidad, true, true));
		if (!consumibles.isEmpty())
			starships.setConsumables(Utiles.controlData(consumibles, true, false));
		starships.setEdited(fechaEdicion);

		return starships;
	}

	/**
	 * Método: Obtiene una lista de Straships a partir del nombre pasado, método
	 * expuesto
	 */
	public void findbyName(String name) {
		if (name.trim().isEmpty()) {
			System.out.println(KConstants.Common.NOT_DATA_FIND);
			return;
		}
		manejador.tearUp();
		buscarStarshipsName(name);
		manejador.tearDown();
	}

	/**
	 * Método: Obtiene una lista de Straships a partir del nombre pasado, método no
	 * expuesto
	 */
	private static void buscarStarshipsName(String name) {
		try {
			final String sqlQuery = "FROM Starships where UPPER(name) like '%" + name.toUpperCase() + "%'";
			List<Starships> consultaStarships = manejador.session.createQuery(sqlQuery).list();
			if (consultaStarships.isEmpty()) {
				System.out.println(KConstants.Common.NOT_REGISTER + " para el nombre " + name);
			} else {
				consultaStarships.stream().forEach(Starships::imprimeRegistroDetallado);
			}
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
	}

	/**
	 * Método: Imprime una lista de Straships, método expuesto
	 * 
	 * @return List<Starships>
	 */
	public void showRegisters() {
		mostrarTodos(getRegisters());
	}

	/**
	 * Método: Imprime una lista de Straships, método no expuesto
	 * 
	 * @return List<Starships>
	 */
	private static void mostrarTodos(List<Starships> consultaStarships) {
		if (consultaStarships.isEmpty()) {
			System.out.println(KConstants.Common.NOT_REGISTER);
		} else {
			Utiles.getCabeceraRegistroStarships();
			consultaStarships.stream().forEach(Starships::imprimeRegistro);
		}
	}

	/**
	 * Método: Obtiene una lista de Straships, método expuesto
	 * 
	 * @return List<Starships>
	 */
	private static List<Starships> getRegisters() {
		manejador.tearUp();
		List<Starships> consultaStarships = obtenerRegistros();
		manejador.tearDown();

		return consultaStarships;
	}

	/**
	 * Método: Obtiene una lista de Straships, método no expuesto
	 * 
	 * @return List<Starships>
	 */
	private static List<Starships> obtenerRegistros() {
		List<Starships> consultaStarships = new ArrayList<Starships>();
		try {
			final String sqlQuery = "FROM Starships ORDER BY codigo";
			consultaStarships = manejador.session.createQuery(sqlQuery).list();
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
		return consultaStarships;
	}

	/**
	 * Método: Obtenemos una lista Set de Starship seleccionadas
	 * 
	 * @param peoples
	 * @return Set<Starships>
	 */
	public static Set<Starships> getStarship(HandlerBD manejador) {
		if (teclado == null) {
			teclado = new Scanner(System.in);
		}
		Set<Starships> listaNaves = new HashSet<Starships>();
		String deseaIngresar;
		System.out.println("Desea ingresar Naves que pilota S/N: ");
		deseaIngresar = teclado.nextLine();
		if ("S".equalsIgnoreCase(deseaIngresar.trim())) {
			listaNaves = obtenerRegistrosSet(manejador);
		}
		return listaNaves;
	}

	/**
	 * Método: Obtiene una lista de Straships, método expuesto
	 * 
	 * @return List<Starships>
	 */
	private static Set<Starships> obtenerRegistrosSet(HandlerBD manejador) {
		List<Starships> consultaStarships = new ArrayList<Starships>();
		try {
			final String sqlQuery = "FROM Starships ORDER BY codigo";
			consultaStarships = manejador.session.createQuery(sqlQuery).list();
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
		return cargarStarshipsSet(consultaStarships);
	}

	/**
	 * Método: Carga una lista Starships a partir de la lista de Starships
	 * insertadas
	 * 
	 * @param StarshipsInsertadas
	 * @return List<Starships>
	 */
	private static Set<Starships> cargarStarshipsSet(List<Starships> navesInsertadas) {
		Set<Starships> listaNaves = new HashSet<Starships>();
		Optional<Starships> naveEncontrada = Optional.empty();
		boolean valido = false;

		navesInsertadas.stream().forEach(Starships::imprimeCodValor);

		do {
			System.out.println(KConstants.Common.INSERT_CODE);
			String codigo = teclado.nextLine();
			valido = !codigo.trim().isEmpty() && Utiles.isNumeric(codigo);

			if (valido) {
				naveEncontrada = navesInsertadas.stream().filter(n -> n.getCodigo() == Integer.valueOf(codigo))
						.findAny();

				valido = naveEncontrada.isPresent();

				if (valido) {
					listaNaves.add(naveEncontrada.get());
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
		return listaNaves;
	}
}
