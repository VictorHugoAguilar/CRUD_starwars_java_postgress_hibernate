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
import com.tema4.models.Starships;
import com.tema4.services.HandlerBD;
import com.tema4.utils.Utiles;

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

	@Override
	public void create() {
		String deseaIngresar = "";
		manejador.tearUp();
		Transaction trans = manejador.session.beginTransaction();

		Starships starshipToInsert = getStarshipToInsert();
		manejador.session.save(starshipToInsert);
		Set<Starships> starships = new HashSet<Starships>();
		starships.add(starshipToInsert);

		System.out.println("Desea ingresar people que conduce la starship S/N: ");
		deseaIngresar = teclado.nextLine();
		if ("S".equalsIgnoreCase(deseaIngresar.trim())) {
			final String sqlQuery = "FROM People";
			List<People> peoplesInsertadas = new ArrayList<People>();
			peoplesInsertadas = manejador.session.createQuery(sqlQuery).list();
			List<People> listaPeoples = PeopleController.cargarPeoples(peoplesInsertadas);
			if (!listaPeoples.isEmpty()) {
				listaPeoples.stream().forEach(people -> {
					people.setCodigo(people.getCodigo());
					people.setStarshipses(starships);
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
					film.setStarshipses(starships);
					manejador.session.save(film);
				});
			}
		}

		trans.commit();
		System.out.println("registro ingresado...");
		manejador.tearDown();
	}

	private Starships getStarshipToInsert() {
		Starships starships = new Starships();
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
		System.out.println("Ingrese el hyperDriver: ");
		String hyperdriver = teclado.nextLine();
		System.out.println("Ingrese el mlgt: ");
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
		starships.setCostInCredits(coste.isEmpty() || !Utiles.isNumeric(coste) ? KConstants.Common.UNKNOWN : coste);
		starships.setLength(largo.isEmpty() || !Utiles.isNumeric(largo) ? KConstants.Common.UNKNOWN : largo);
		starships.setCrew(
				tripulacion.isEmpty() || !Utiles.isNumeric(tripulacion) ? KConstants.Common.UNKNOWN : tripulacion);
		starships.setPassengers(
				pasajeros.isEmpty() || !Utiles.isNumeric(pasajeros) ? KConstants.Common.UNKNOWN : pasajeros);
		starships.setMaxAtmospheringSpeed(
				velocidadMax.isEmpty() || !Utiles.isNumeric(velocidadMax) ? KConstants.Common.UNKNOWN : velocidadMax);
		starships.setHyperdriveRating(
				hyperdriver.isEmpty() || !Utiles.isNumeric(hyperdriver) ? KConstants.Common.UNKNOWN : hyperdriver);
		starships.setMglt(mglts.isEmpty() || !Utiles.isNumeric(mglts) ? KConstants.Common.UNKNOWN : mglts);
		starships.setCargoCapacity(
				capacidad.isEmpty() || !Utiles.isNumeric(capacidad) ? KConstants.Common.UNKNOWN : capacidad);
		starships.setConsumables(
				consumibles.isEmpty() || !Utiles.isNumeric(consumibles) ? KConstants.Common.UNKNOWN : consumibles);
		starships.setCreated(fechaCreación);
		starships.setEdited(fechaEdicion);

		return starships;
	}

	@Override
	public void delete() {
		List<Starships> listaStarship = getRegisters();
		listaStarship.stream().forEach(Starships::imprimeCodValor);

		Optional<Starships> starshipEncontrado = null;
		boolean valido = false;
		do {
			System.out.println("Introduce el código de Starship ha eliminar");
			final String codigoABorrar = teclado.nextLine();

			if (!codigoABorrar.trim().isEmpty() && Utiles.isNumeric(codigoABorrar)) {
				starshipEncontrado = listaStarship.stream().filter(f -> f.getCodigo() == Integer.valueOf(codigoABorrar))
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
		if (valido && starshipEncontrado.isPresent()) {
			System.out.println(KConstants.Common.ARE_YOU_SURE);
			String seguro = teclado.nextLine();
			if ("S".equalsIgnoreCase(seguro.trim())) {
				Transaction trans = manejador.session.beginTransaction();
				manejador.session.delete(starshipEncontrado.get());
				trans.commit();
				System.out.println("Starship borrado...");
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
		buscarStarshipsName(name);
		manejador.tearDown();
	}

	private static void buscarStarshipsName(String name) {
		final String sqlQuery = "FROM Starships where UPPER(name) like '%" + name.toUpperCase() + "%'";
		try {
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

	public void showRegisters() {
		mostrarTodos(getRegisters());
	}

	private static void mostrarTodos(List<Starships> consultaStarships) {
		if (consultaStarships.isEmpty()) {
			System.out.println(KConstants.Common.NOT_REGISTER);
		} else {
			Utiles.getCabeceraRegistroStarships();
			consultaStarships.stream().forEach(Starships::imprimeRegistro);
		}
	}

	public static List<Starships> getRegisters() {
		manejador.tearUp();
		List<Starships> consultaStarships = obtenerRegistros();
		manejador.tearDown();

		return consultaStarships;
	}

	private static List<Starships> obtenerRegistros() {
		final String sqlQuery = "FROM Starships";
		List<Starships> consultaStarships = new ArrayList<Starships>();
		try {
			consultaStarships = manejador.session.createQuery(sqlQuery).list();
		} catch (Exception e) {
			System.out.println(KConstants.Common.FAIL_CONECTION);
		}
		return consultaStarships;
	}

	public static List<Starships> cargarStarships(List<Starships> navesInsertadas) {
		if (teclado == null) {
			teclado = new Scanner(System.in);
		}
		navesInsertadas.stream().forEach(Starships::imprimeCodValor);

		boolean valido = false;
		List<Starships> listaNaves = new ArrayList<Starships>();
		Optional<Starships> naveEncontrada;
		do {
			System.out.println("Ingrese el código de nave: ");
			String codigo = teclado.nextLine();
			valido = !codigo.trim().isEmpty() && Utiles.isNumeric(codigo);

			if (valido) {
				naveEncontrada = navesInsertadas.stream().filter(n -> n.getCodigo() == Integer.valueOf(codigo))
						.findAny();

				if (!naveEncontrada.isPresent()) {
					valido = false;
					System.out.println("El código introducido no es válido");
				} else {
					listaNaves.add(naveEncontrada.get());

					System.out.println("Desea ingresar otra nave S/N");
					String otraNave = teclado.nextLine();
					if ("S".equalsIgnoreCase(otraNave.trim())) {
						valido = false;
					} else {
						valido = true;
					}
				}
			}
		} while (!valido);
		return listaNaves;
	}
}
