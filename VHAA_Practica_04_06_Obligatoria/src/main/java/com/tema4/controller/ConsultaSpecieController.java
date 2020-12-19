package com.tema4.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.tema4.constants.KConstants;
import com.tema4.models.Planets;
import com.tema4.models.Species;
import com.tema4.services.HandlerBD;
import com.tema4.utils.Utiles;

@SuppressWarnings("unchecked")
public class ConsultaSpecieController {

	static HandlerBD manejador = null;

	ConsultaSpecieController() {
	}

	public static void getSpecieByName(String name) {
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

	public static void getSpeciesWithoutPeople() {
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

	public static void showRegisters() {
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
	
	public static void main(String[] args) {
		showRegisters();
		getSpecieByName("hu");
	}

}
