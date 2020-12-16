package com.tema4.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Starships generated by hbm2java
 */

@Entity
@Table(name = "starships")
public class Starships implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int codigo;
	private String name;
	private String model;
	private String starshipClass;
	private String manufacturer;
	private String costInCredits;
	private String length;
	private String crew;
	private String passengers;
	private String maxAtmospheringSpeed;
	private String hyperdriveRating;
	private String mglt;
	private String cargoCapacity;
	private String consumables;
	private String created;
	private String edited;
	private Set<Films> filmses = new HashSet<Films>(0);
	private Set<People> peoples = new HashSet<People>(0);

	public Starships() {
	}

	public Starships(int codigo) {
		this.codigo = codigo;
	}

	public Starships(int codigo, String name, String model, String starshipClass, String manufacturer,
			String costInCredits, String length, String crew, String passengers, String maxAtmospheringSpeed,
			String hyperdriveRating, String mglt, String cargoCapacity, String consumables, String created,
			String edited, Set<Films> filmses, Set<People> peoples) {
		this.codigo = codigo;
		this.name = name;
		this.model = model;
		this.starshipClass = starshipClass;
		this.manufacturer = manufacturer;
		this.costInCredits = costInCredits;
		this.length = length;
		this.crew = crew;
		this.passengers = passengers;
		this.maxAtmospheringSpeed = maxAtmospheringSpeed;
		this.hyperdriveRating = hyperdriveRating;
		this.mglt = mglt;
		this.cargoCapacity = cargoCapacity;
		this.consumables = consumables;
		this.created = created;
		this.edited = edited;
		this.filmses = filmses;
		this.peoples = peoples;
	}

	@Id
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

	@Column(name = "starship_class", length = 100)
	public String getStarshipClass() {
		return this.starshipClass;
	}

	public void setStarshipClass(String starshipClass) {
		this.starshipClass = starshipClass;
	}

	@Column(name = "manufacturer", length = 100)
	public String getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Column(name = "cost_in_credits", length = 100)
	public String getCostInCredits() {
		return this.costInCredits;
	}

	public void setCostInCredits(String costInCredits) {
		this.costInCredits = costInCredits;
	}

	@Column(name = "length", length = 100)
	public String getLength() {
		return this.length;
	}

	public void setLength(String length) {
		this.length = length;
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

	@Column(name = "hyperdrive_rating", length = 100)
	public String getHyperdriveRating() {
		return this.hyperdriveRating;
	}

	public void setHyperdriveRating(String hyperdriveRating) {
		this.hyperdriveRating = hyperdriveRating;
	}

	@Column(name = "mglt", length = 100)
	public String getMglt() {
		return this.mglt;
	}

	public void setMglt(String mglt) {
		this.mglt = mglt;
	}

	@Column(name = "cargo_capacity", length = 100)
	public String getCargoCapacity() {
		return this.cargoCapacity;
	}

	public void setCargoCapacity(String cargoCapacity) {
		this.cargoCapacity = cargoCapacity;
	}

	@Column(name = "consumables ", length = 100)
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

	@ManyToMany(fetch = FetchType.LAZY, targetEntity = Films.class, cascade = CascadeType.ALL)
	@JoinTable(name = "starships_films", joinColumns = {
			@JoinColumn(name = "codigo_starship", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "codigo_film", nullable = false, updatable = false) })
	public Set<Films> getFilmses() {
		return this.filmses;
	}

	public void setFilmses(Set<Films> filmses) {
		this.filmses = filmses;
	}

	@ManyToMany(fetch = FetchType.LAZY, targetEntity = People.class, cascade = CascadeType.ALL)
	@JoinTable(name = "starships_people", joinColumns = {
			@JoinColumn(name = "codigo_starship", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "codigo_people", nullable = false, updatable = false) })
	public Set<People> getPeoples() {
		return this.peoples;
	}

	public void setPeoples(Set<People> peoples) {
		this.peoples = peoples;
	}

}
