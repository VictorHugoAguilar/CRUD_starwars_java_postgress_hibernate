package com.tema4.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.tema4.constants.KConstants;
import com.tema4.models.Species;
import com.tema4.services.HandlerBD;
import com.tema4.utils.Utiles;

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
		// TODO Auto-generated method stub

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

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
		manejador = HandlerBD.getInstance();
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
		manejador = HandlerBD.getInstance();
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

}
