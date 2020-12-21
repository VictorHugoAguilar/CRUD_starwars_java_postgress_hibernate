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
import com.tema4.models.Vehicles;
import com.tema4.services.HandlerBD;
import com.tema4.utils.Utiles;

/**
 * Controlador de VehiclesController
 * 
 * Clase que controla el CRUD completo para la clase Vehicle
 * 
 * 
 * @author Victor Hugo Aguilar Aguilar
 *
 */
@SuppressWarnings("unchecked")
public class VehiclesController implements ICRUDController {
	private static VehiclesController vehiclesController;
	private static HandlerBD manejador;
	private static Scanner teclado;

	VehiclesController() {
		teclado = new Scanner(System.in);
		manejador = HandlerBD.getInstance();
	}

	public static VehiclesController getInstance() {
		if (vehiclesController == null) {
			vehiclesController = new VehiclesController();
			return vehiclesController;
		}
		return vehiclesController;
	}

	/**
	 * Método: Create
	 * 
	 * Creación de un registro del tipo Vehicles con sus relaciones
	 */
	public void create() {
		manejador.tearUp();
		try {
			Transaction trans = manejador.session.beginTransaction();

			Vehicles vehicleToInsert = getVehicleToInsert();
			manejador.session.save(vehicleToInsert);
			Set<Vehicles> vehicle = new HashSet<Vehicles>();
			vehicle.add(vehicleToInsert);

			insertPeopleVehicle(vehicle);
			insertFilmVehicle(vehicle);

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
	 * Eliminación de un registro del tipo Planet no elimina sus relaciones
	 */
	public void delete() {
		manejador.tearUp();
		try {
			Optional<Vehicles> vehicleEncontrado = getVehicleFromInsert();

			if (vehicleEncontrado.isPresent()) {
				System.out.println(KConstants.Common.ARE_YOU_SURE);
				String seguro = teclado.nextLine();
				if ("S".equalsIgnoreCase(seguro.trim())) {
					Transaction trans = manejador.session.beginTransaction();
					manejador.session.delete(vehicleEncontrado.get());
					//trans.commit();
					System.out.println("Vehicle borrado...");
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
	 * Modificación de un registro del tipo Vehicles
	 */
	public void update() {
		manejador.tearUp();
		try {
			Optional<Vehicles> vehicleEncontrado = getVehicleFromInsert();

			if (vehicleEncontrado.isPresent()) {
				Transaction trans = manejador.session.beginTransaction();

				Vehicles vehicleToUpdate = getVehicleToUpdate(vehicleEncontrado.get());
				manejador.session.save(vehicleToUpdate);
				Set<Vehicles> vehicle = new HashSet<Vehicles>();
				vehicle.add(vehicleToUpdate);

				insertPeopleVehicle(vehicle);
				insertFilmVehicle(vehicle);

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
	 * Método: Insert film in vehicles
	 * 
	 * @param vehicle
	 */
	private void insertFilmVehicle(Set<Vehicles> vehicle) {
		String deseaIngresar;
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
					film.setVehicleses(vehicle);
					manejador.session.save(film);
				});
			}
		}
	}

	/**
	 * Método: Insert people in vehicles
	 * 
	 * @param vehicle
	 */
	private void insertPeopleVehicle(Set<Vehicles> vehicle) {
		String deseaIngresar;
		System.out.println("Desea ingresar people que conduce el vehículo S/N: ");
		deseaIngresar = teclado.nextLine();
		if ("S".equalsIgnoreCase(deseaIngresar.trim())) {
			final String sqlQuery = "FROM People";
			List<People> peoplesInsertadas = new ArrayList<People>();
			peoplesInsertadas = manejador.session.createQuery(sqlQuery).list();
			List<People> listaPeoples = PeopleController.cargarPeoples(peoplesInsertadas);
			if (!listaPeoples.isEmpty()) {
				listaPeoples.stream().forEach(people -> {
					people.setCodigo(people.getCodigo());
					people.setVehicleses(vehicle);
					manejador.session.save(people);
				});
			}
		}
	}

	/**
	 * Método: Constructor de un objeto del tipo vehicle con los datos pasados por
	 * el usuario
	 * 
	 * @return Vehicles
	 */
	private Vehicles getVehicleToInsert() {
		Vehicles vehicle = new Vehicles();
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

		String modelo = "";
		do {
			System.out.println("Ingrese el modelo: ");
			modelo = teclado.nextLine();

			valido = !modelo.trim().isEmpty();
			if (!valido) {
				System.out.println(KConstants.Common.NOT_VALID_DATA);
			}

		} while (!valido);

		String clase = "";
		do {
			System.out.println("Ingrese la clase: ");
			clase = teclado.nextLine();

			valido = !clase.trim().isEmpty();
			if (!valido) {
				System.out.println(KConstants.Common.NOT_VALID_DATA);
			}

		} while (!valido);

		String fabricado = "";
		do {
			System.out.println("Ingrese la empresa de fabricación: ");
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
		System.out.println("Ingrese la velocidad maxima: ");
		String velocidadMax = teclado.nextLine();
		System.out.println("Ingrese la capacidad: ");
		String capacidad = teclado.nextLine();
		System.out.println("Ingrese las duracion de los consumibles: ");
		String consumibles = teclado.nextLine();
		String fechaCreación = Utiles.parseDate(new Date().toString(), KConstants.FormatDate.FORMAT_BD);
		String fechaEdicion = Utiles.parseDate(new Date().toString(), KConstants.FormatDate.FORMAT_BD);

		vehicle.setName(nombre);
		vehicle.setModel(modelo);
		vehicle.setVehicleClass(clase);
		vehicle.setManufacturer(fabricado);
		vehicle.setCostInCredits(Utiles.controlData(coste, true, true));
		vehicle.setLength(Utiles.controlData(largo, true, true));
		vehicle.setCrew(Utiles.controlData(tripulacion, true, true));
		vehicle.setPassengers(Utiles.controlData(pasajeros, true, true));
		vehicle.setMaxAtmospheringSpeed(Utiles.controlData(velocidadMax, true, true));
		vehicle.setCargoCapacity(Utiles.controlData(capacidad, true, true));
		vehicle.setConsumables(Utiles.controlData(consumibles, true, false));
		vehicle.setCreated(fechaCreación);
		vehicle.setEdited(fechaEdicion);

		return vehicle;
	}

	/**
	 * Método: Obtener un objeto a partir del código seleccionado por el usuario
	 * 
	 * @return Optional<Vehicles>
	 */
	private static Optional<Vehicles> getVehicleFromInsert() {
		Optional<Vehicles> vehicleEncontrado = Optional.empty();

		List<Vehicles> listaVehicles = obtenerRegistros();
		listaVehicles.stream().forEach(Vehicles::imprimeCodValor);

		boolean valido = false;
		do {
			System.out.println("Introduce el código de Vehicles: ");
			final String codigoABorrar = teclado.nextLine();

			if (!codigoABorrar.trim().isEmpty() && Utiles.isNumeric(codigoABorrar)) {
				vehicleEncontrado = listaVehicles.stream().filter(f -> f.getCodigo() == Integer.valueOf(codigoABorrar))
						.findFirst();
				valido = vehicleEncontrado.isPresent();
			}
			if (!valido) {
				System.out.println(KConstants.Common.CODE_NOT_FOUND);
				System.out.println(KConstants.Common.INSERT_OTHER);
				String otro = teclado.nextLine();
				valido = !"S".equalsIgnoreCase(otro.trim());
			}
		} while (!valido);
		return vehicleEncontrado;
	}

	/**
	 * Método: Modificamos el objeto del tipo Vehicles con datos pasado por el
	 * usuario
	 * 
	 * @return Vehicles
	 */
	private Vehicles getVehicleToUpdate(Vehicles vehicle) {
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
		System.out.println("Ingrese el número de tripulación: ");
		String tripulacion = teclado.nextLine();
		System.out.println("Ingrese el número de pasajeros: ");
		String pasajeros = teclado.nextLine();
		System.out.println("Ingrese la velocidad máxima: ");
		String velocidadMax = teclado.nextLine();
		System.out.println("Ingrese la capacidad: ");
		String capacidad = teclado.nextLine();
		System.out.println("Ingrese las duración de los consumibles: ");
		String consumibles = teclado.nextLine();
		String fechaEdicion = Utiles.parseDate(new Date().toString(), KConstants.FormatDate.FORMAT_BD);

		if (!nombre.isEmpty())
			vehicle.setName(nombre);
		if (!modelo.isEmpty())
			vehicle.setModel(modelo);
		if (!clase.isEmpty())
			vehicle.setVehicleClass(clase);
		if (!fabricado.isEmpty())
			vehicle.setManufacturer(fabricado);
		if (!coste.isEmpty())
			vehicle.setCostInCredits(Utiles.controlData(coste, true, true));
		if (!largo.isEmpty())
			vehicle.setLength(Utiles.controlData(largo, true, true));
		if (!tripulacion.isEmpty())
			vehicle.setCrew(Utiles.controlData(tripulacion, true, true));
		if (!pasajeros.isEmpty())
			vehicle.setPassengers(Utiles.controlData(pasajeros, true, true));
		if (!velocidadMax.isEmpty())
			vehicle.setMaxAtmospheringSpeed(Utiles.controlData(velocidadMax, true, true));
		if (!capacidad.isEmpty())
			vehicle.setCargoCapacity(Utiles.controlData(capacidad, true, true));
		if (!consumibles.isEmpty())
			vehicle.setConsumables(Utiles.controlData(consumibles, true, false));
		vehicle.setEdited(fechaEdicion);

		return vehicle;
	}

	/**
	 * Método: Buscar por nombre, método expuesto
	 */
	public void findbyName(String name) {
		if (name.trim().isEmpty()) {
			System.out.println(KConstants.Common.NOT_DATA_FIND);
			return;
		}
		buscarVehicleName(name);
	}

	/**
	 * Método para obtener los vehiculos a partir de un nombre pasado, método no
	 * expuesto
	 * 
	 * @param name
	 */
	private static void buscarVehicleName(String name) {
		manejador.tearUp();
		try {

			final String sqlQuery = "FROM Vehicles where UPPER(name) like '%" + name.toUpperCase() + "%'";
			List<Vehicles> consultaVehicles = manejador.session.createQuery(sqlQuery).list();
			if (consultaVehicles.isEmpty()) {
				System.out.println(KConstants.Common.NOT_REGISTER + " para el nombre " + name);
			} else {
				consultaVehicles.stream().forEach(Vehicles::imprimeRegistroDetallado);
			}
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
		manejador.tearDown();
	}

	/**
	 * Método: Muestra todos los registros, método expuesto
	 */
	public void showRegisters() {
		mostrarTodos(getRegisters());
	}

	/**
	 * Método: Imprime la lista de vehicles
	 * 
	 * @param consultaVehicles
	 */
	private static void mostrarTodos(List<Vehicles> consultaVehicles) {
		if (consultaVehicles.isEmpty()) {
			System.out.println(KConstants.Common.NOT_REGISTER);
		} else {
			Utiles.getCabeceraRegistroVehicles();
			consultaVehicles.stream().forEach(Vehicles::imprimeRegistro);
		}
	}

	/**
	 * Método: Obtener todos los registro, método expuesto
	 * 
	 * @return
	 */
	public static List<Vehicles> getRegisters() {
		manejador.tearUp();
		List<Vehicles> consultaVehicles = obtenerRegistros();
		manejador.tearDown();

		return consultaVehicles;
	}

	/**
	 * Método: obtener la lista de vehicles completa
	 * 
	 * @return List<Vehicles>
	 */
	private static List<Vehicles> obtenerRegistros() {
		List<Vehicles> consultaVehicles = new ArrayList<Vehicles>();
		try {
			final String sqlQuery = "FROM Vehicles";
			consultaVehicles = manejador.session.createQuery(sqlQuery).list();
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
		return consultaVehicles;
	}

	/**
	 * Método: Carga a partir de una lista los seleccionados.
	 * 
	 * @param vehiclesInsertados
	 * @return List<Vehicles>
	 */
	public static List<Vehicles> cargarVehicles(List<Vehicles> vehiclesInsertados) {
		if (teclado == null) {
			teclado = new Scanner(System.in);
		}
		List<Vehicles> listVehicles = new ArrayList<Vehicles>();
		Optional<Vehicles> vehicleEncontrado = Optional.empty();
		boolean valido = false;

		vehiclesInsertados.stream().forEach(Vehicles::imprimeCodValor);

		do {
			System.out.println(KConstants.Common.INSERT_CODE);
			final String codigo = teclado.nextLine();
			valido = !codigo.trim().isEmpty() && Utiles.isNumeric(codigo);

			if (valido) {
				vehicleEncontrado = vehiclesInsertados.stream().filter(n -> n.getCodigo() == Integer.valueOf(codigo))
						.findAny();

				valido = vehicleEncontrado.isPresent();
				
				if (valido) {
					listVehicles.add(vehicleEncontrado.get());
				} else {
					System.out.println(KConstants.Common.CODE_NOT_FOUND);
				}

				System.out.println(KConstants.Common.INSERT_OTHER);
				String otroVehicle = teclado.nextLine();
				valido = !"S".equalsIgnoreCase(otroVehicle.trim());

			} else {
				System.out.println(KConstants.Common.INVALID_CODE);
			}
		} while (!valido);
		return listVehicles;
	}

}
