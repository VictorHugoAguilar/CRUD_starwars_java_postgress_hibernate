package com.tema4.models;

import java.io.Serializable;

// Generated 15 dic. 2020 19:28:04 by Hibernate Tools 5.2.12.Final

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.tema4.utils.Utiles;

@Entity
@Table(name = "vehicles")
public class Vehicles implements Serializable {

	private static final long serialVersionUID = 1L;
	private int codigo;
	private String name;
	private String model;
	private String vehicleClass;
	private String manufacturer;
	private String length;
	private String costInCredits;
	private String crew;
	private String passengers;
	private String maxAtmospheringSpeed;
	private String cargoCapacity;
	private String consumables;
	private String created;
	private String edited;
	private Set<People> peoples = new HashSet<People>(0);
	private Set<Films> filmses = new HashSet<Films>(0);

	public Vehicles() {
	}

	public Vehicles(int codigo) {
		this.codigo = codigo;
	}

	public Vehicles(int codigo, String name, String model, String vehicleClass, String manufacturer, String length,
			String costInCredits, String crew, String passengers, String maxAtmospheringSpeed, String cargoCapacity,
			String consumables, String created, String edited, Set<People> peoples, Set<Films> filmses) {
		this.codigo = codigo;
		this.name = name;
		this.model = model;
		this.vehicleClass = vehicleClass;
		this.manufacturer = manufacturer;
		this.length = length;
		this.costInCredits = costInCredits;
		this.crew = crew;
		this.passengers = passengers;
		this.maxAtmospheringSpeed = maxAtmospheringSpeed;
		this.cargoCapacity = cargoCapacity;
		this.consumables = consumables;
		this.created = created;
		this.edited = edited;
		this.peoples = peoples;
		this.filmses = filmses;
	}

	@Id
	@GenericGenerator(name = "kaugen", strategy = "increment")
	@GeneratedValue(generator = "kaugen")
	@Column(name = "codigo", unique = true, nullable = false)
	public int getCodigo() {
		return this.codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	@Column(name = "name", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "model", length = 100)
	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Column(name = "vehicle_class", length = 100)
	public String getVehicleClass() {
		return this.vehicleClass;
	}

	public void setVehicleClass(String vehicleClass) {
		this.vehicleClass = vehicleClass;
	}

	@Column(name = "manufacturer", length = 100)
	public String getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Column(name = "length", length = 100)
	public String getLength() {
		return this.length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	@Column(name = "cost_in_credits", length = 100)
	public String getCostInCredits() {
		return this.costInCredits;
	}

	public void setCostInCredits(String costInCredits) {
		this.costInCredits = costInCredits;
	}

	@Column(name = "crew", length = 100)
	public String getCrew() {
		return this.crew;
	}

	public void setCrew(String crew) {
		this.crew = crew;
	}

	@Column(name = "passengers", length = 100)
	public String getPassengers() {
		return this.passengers;
	}

	public void setPassengers(String passengers) {
		this.passengers = passengers;
	}

	@Column(name = "max_atmosphering_speed", length = 100)
	public String getMaxAtmospheringSpeed() {
		return this.maxAtmospheringSpeed;
	}

	public void setMaxAtmospheringSpeed(String maxAtmospheringSpeed) {
		this.maxAtmospheringSpeed = maxAtmospheringSpeed;
	}

	@Column(name = "cargo_capacity", length = 100)
	public String getCargoCapacity() {
		return this.cargoCapacity;
	}

	public void setCargoCapacity(String cargoCapacity) {
		this.cargoCapacity = cargoCapacity;
	}

	@Column(name = "consumables", length = 100)
	public String getConsumables() {
		return this.consumables;
	}

	public void setConsumables(String consumables) {
		this.consumables = consumables;
	}

	@Column(name = "created", length = 100)
	public String getCreated() {
		return this.created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	@Column(name = "edited", length = 100)
	public String getEdited() {
		return this.edited;
	}

	public void setEdited(String edited) {
		this.edited = edited;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "vehicles_people", joinColumns = {
			@JoinColumn(name = "codigo_vehicle", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "codigo_people", nullable = false, updatable = false) })
	public Set<People> getPeoples() {
		return this.peoples;
	}

	public void setPeoples(Set<People> peoples) {
		this.peoples = peoples;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "vehicles_films", joinColumns = {
			@JoinColumn(name = "codigo_vehicle", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "codigo_film", nullable = false, updatable = false) })
	public Set<Films> getFilmses() {
		return this.filmses;
	}

	public void setFilmses(Set<Films> filmses) {
		this.filmses = filmses;
	}

	@Override
	public String toString() {
		return "Vehicles [codigo=" + codigo + ", name=" + name + ", model=" + model + ", vehicleClass=" + vehicleClass
				+ ", manufacturer=" + manufacturer + ", length=" + length + ", costInCredits=" + costInCredits
				+ ", crew=" + crew + ", passengers=" + passengers + ", maxAtmospheringSpeed=" + maxAtmospheringSpeed
				+ ", cargoCapacity=" + cargoCapacity + ", consumables=" + consumables + ", created=" + created
				+ ", edited=" + edited + ", peoples=" + peoples + ", filmses=" + filmses + "]";
	}

	public void imprimeRegistroDetallado() {
		StringBuilder sb = new StringBuilder();
		Integer intCodigo = getCodigo();
		sb.append(String.format("%-20s", "Codigo: " + intCodigo));
		String nombre = getName();
		sb.append(String.format("%-40s", "Nombre:" + nombre) + "\n");
		sb.append("\nCaracteristicas\n");
		String modelo = getModel();
		sb.append(String.format("%-50s", "Model: " + modelo));
		String clase = getVehicleClass();
		sb.append(String.format("%-50s", "Clase: " + clase));
		String fabricado = getManufacturer();
		sb.append(String.format("%-50s", "Fabricado por: " + fabricado) + "\n");
		String coste = Utiles.checkUnknown(getCostInCredits());
		sb.append(String.format("%-30s", "Coste: " + coste));
		String largo = Utiles.checkUnknown(getLength());
		sb.append(String.format("%-30s", "Largo: " + largo));
		String tripulacion = Utiles.checkUnknown(getCrew());
		sb.append(String.format("%-30s", "Tripulación :" + tripulacion));
		String velocidad = Utiles.checkUnknown(getMaxAtmospheringSpeed());
		sb.append(String.format("%-30s", "Velocidad Max: " + velocidad) + "\n");
		String capacidad = Utiles.checkUnknown(getCargoCapacity());
		sb.append(String.format("%-30s", "Capacidad: " + capacidad));
		String consumibles = Utiles.checkUnknown(getConsumables());
		sb.append(String.format("%-30s", "Consumibles: " + consumibles) + "\n");
		String conductores = "";
		for (People p : getPeoples()) {
			conductores += p.getName() + "   ";
		}
		if (!conductores.isEmpty()) {
			sb.append("\nConducido por: \n");
			sb.append(conductores + "\n");
		}
		String filmsAparicion = "";
		for (Films f : getFilmses()) {
			filmsAparicion += f.getTitle() + "  ";
		}
		if (!filmsAparicion.isEmpty()) {
			sb.append("\nAparece en películas: \n");
			sb.append(filmsAparicion + "\n");
		}
		String creado = getCreated();
		sb.append(String.format("%-50s", "\nCreado: " + creado) + "\n");
		String editado = getCreated();
		sb.append(String.format("%-50s", "Editado: " + editado) + "\n");

		System.out.println(sb.toString());
	}

	public void imprimeRegistro() {
		StringBuilder sb = new StringBuilder();
		Integer intCodigo = getCodigo();
		sb.append(String.format("%-10s", intCodigo));
		String nombre = Utiles.formatedTextSize(getName(), 30);
		sb.append(String.format("%-30s", nombre));
		String modelo = Utiles.formatedTextSize(getModel(), 29);
		sb.append(String.format("%-30s", modelo));
		String clase = Utiles.formatedTextSize(getVehicleClass(), 29);
		sb.append(String.format("%-30s", clase));
		String fabricado = Utiles.formatedTextSize(getManufacturer(), 29);
		sb.append(String.format("%-30s", fabricado));
		String coste = Utiles.checkUnknown(getCostInCredits());
		sb.append(String.format("%-20s", coste));
		String largo = Utiles.checkUnknown(getLength());
		sb.append(String.format("%-20s", largo));
		String tripulacion = Utiles.checkUnknown(getCrew());
		sb.append(String.format("%-20s", tripulacion));
		String velocidad = Utiles.checkUnknown(getMaxAtmospheringSpeed());
		sb.append(String.format("%-20s", velocidad));
		String capacidad = Utiles.checkUnknown(getCargoCapacity());
		sb.append(String.format("%-20s", capacidad));
		String consumibles = Utiles.checkUnknown(getConsumables());
		sb.append(String.format("%-20s", consumibles));
		String creado = getCreated();
		sb.append(String.format("%-40s", creado));
		String editado = getCreated();
		sb.append(String.format("%-40s", editado));
		System.out.println(sb.toString());
	}

	public void imprimeCodValor() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%-5s", getCodigo()) + " - ");
		String nombre = Utiles.formatedTextSize(getName(), 30);
		sb.append(String.format("%-30s", nombre));
		System.out.println(sb.toString());
	}

}
