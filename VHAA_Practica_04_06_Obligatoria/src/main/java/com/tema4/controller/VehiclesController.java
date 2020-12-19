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
import com.tema4.models.Vehicles;
import com.tema4.services.HandlerBD;
import com.tema4.utils.Utiles;

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

	@Override
	public void create() {
		String deseaIngresar = "";
		manejador.tearUp();
		Transaction trans = manejador.session.beginTransaction();

		Vehicles vehicleToInsert = getVehicleToInsert();
		manejador.session.save(vehicleToInsert);
		Set<Vehicles> vehicle = new HashSet<Vehicles>();
		vehicle.add(vehicleToInsert);

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

		trans.commit();
		System.out.println("registro ingresado...");
		manejador.tearDown();
	}

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
		vehicle.setCostInCredits(coste.isEmpty() || !Utiles.isNumeric(coste) ? KConstants.Common.UNKNOWN : coste);
		vehicle.setLength(largo.isEmpty() || !Utiles.isNumeric(largo) ? KConstants.Common.UNKNOWN : largo);
		vehicle.setCrew(
				tripulacion.isEmpty() || !Utiles.isNumeric(tripulacion) ? KConstants.Common.UNKNOWN : tripulacion);
		vehicle.setPassengers(
				pasajeros.isEmpty() || !Utiles.isNumeric(pasajeros) ? KConstants.Common.UNKNOWN : pasajeros);
		vehicle.setMaxAtmospheringSpeed(
				velocidadMax.isEmpty() || !Utiles.isNumeric(velocidadMax) ? KConstants.Common.UNKNOWN : velocidadMax);
		vehicle.setCargoCapacity(
				capacidad.isEmpty() || !Utiles.isNumeric(capacidad) ? KConstants.Common.UNKNOWN : capacidad);
		vehicle.setConsumables(
				consumibles.isEmpty() || !Utiles.isNumeric(consumibles) ? KConstants.Common.UNKNOWN : consumibles);
		vehicle.setCreated(fechaCreación);
		vehicle.setEdited(fechaEdicion);

		return vehicle;
	}

	@Override
	public void delete() {
		List<Vehicles> listaVehicles = getRegisters();
		listaVehicles.stream().forEach(Vehicles::imprimeCodValor);

		Optional<Vehicles> vehicleEncontrado = null;
		boolean valido = false;
		do {
			System.out.println("Introduce el código de Vehicles ha eliminar");
			final String codigoABorrar = teclado.nextLine();

			if (!codigoABorrar.trim().isEmpty() && Utiles.isNumeric(codigoABorrar)) {
				vehicleEncontrado = listaVehicles.stream().filter(f -> f.getCodigo() == Integer.valueOf(codigoABorrar))
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
		if (valido && vehicleEncontrado.isPresent()) {
			System.out.println(KConstants.Common.ARE_YOU_SURE);
			String seguro = teclado.nextLine();
			if ("S".equalsIgnoreCase(seguro.trim())) {
				Transaction trans = manejador.session.beginTransaction();
				manejador.session.delete(vehicleEncontrado.get());
				trans.commit();
				System.out.println("Vehicle borrado...");
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
		buscarVehicleName(name);
		manejador.tearDown();
	}

	private static void buscarVehicleName(String name) {
		final String sqlQuery = "FROM Vehicles where UPPER(name) like '%" + name.toUpperCase() + "%'";
		try {
			List<Vehicles> consultaVehicles = manejador.session.createQuery(sqlQuery).list();
			if (consultaVehicles.isEmpty()) {
				System.out.println(KConstants.Common.NOT_REGISTER + " para el nombre " + name);
			} else {
				consultaVehicles.stream().forEach(Vehicles::imprimeRegistroDetallado);
			}
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
	}

	public void showRegisters() {
		mostrarTodos(getRegisters());
	}

	private static void mostrarTodos(List<Vehicles> consultaVehicles) {
		if (consultaVehicles.isEmpty()) {
			System.out.println(KConstants.Common.NOT_REGISTER);
		} else {
			Utiles.getCabeceraRegistroVehicles();
			consultaVehicles.stream().forEach(Vehicles::imprimeRegistro);
		}
	}

	public static List<Vehicles> getRegisters() {
		manejador = HandlerBD.getInstance();
		manejador.tearUp();
		List<Vehicles> consultaVehicles = obtenerRegistros();
		manejador.tearDown();

		return consultaVehicles;
	}

	private static List<Vehicles> obtenerRegistros() {
		final String sqlQuery = "FROM Vehicles";
		List<Vehicles> consultaVehicles = new ArrayList<Vehicles>();
		try {
			consultaVehicles = manejador.session.createQuery(sqlQuery).list();
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
		return consultaVehicles;
	}

	public static List<Vehicles> cargarVehicles(List<Vehicles> vehiclesInsertados) {
		if (teclado == null) {
			teclado = new Scanner(System.in);
		}
		List<Vehicles> listVehicles = new ArrayList<Vehicles>();
		boolean valido = false;

		vehiclesInsertados.stream().forEach(Vehicles::imprimeCodValor);
		Optional<Vehicles> vehicleEncontrado;

		do {
			System.out.println("Ingrese el código de nave: ");
			final String codigo = teclado.nextLine();
			valido = !codigo.trim().isEmpty() && Utiles.isNumeric(codigo);

			if (valido) {
				vehicleEncontrado = vehiclesInsertados.stream().filter(n -> n.getCodigo() == Integer.valueOf(codigo))
						.findAny();

				if (!vehicleEncontrado.isPresent()) {
					valido = false;
					System.out.println("El código introducido no es válido");
				} else {
					listVehicles.add(vehicleEncontrado.get());

					System.out.println("Desea ingresar otro Vehicle S/N");
					String otroVehicle = teclado.nextLine();
					if ("S".equalsIgnoreCase(otroVehicle.trim())) {
						valido = false;
					} else {
						valido = true;
					}
				}
			}
		} while (!valido);
		return listVehicles;
	}

}
