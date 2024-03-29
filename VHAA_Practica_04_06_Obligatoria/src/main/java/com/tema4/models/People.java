package com.tema4.models;

import java.io.Serializable;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.tema4.utils.Utiles;

@Entity
@Table(name = "people")
public class People implements Serializable {

	private static final long serialVersionUID = 1L;
	private int codigo;
	private Planets planets;
	private String name;
	private String birthYear;
	private String eyeColor;
	private String gender;
	private String hairColor;
	private String height;
	private String mass;
	private String skinColor;
	private String created;
	private String edited;
	private Set<Starships> starshipses = new HashSet<Starships>(0);
	private Set<Species> specieses = new HashSet<Species>(0);
	private Set<Vehicles> vehicleses = new HashSet<Vehicles>(0);
	private Set<Films> filmses = new HashSet<Films>(0);

	public People() {
	}

	public People(int codigo) {
		this.codigo = codigo;
	}

	public People(int codigo, Planets planets, String name, String birthYear, String eyeColor, String gender,
			String hairColor, String height, String mass, String skinColor, String created, String edited,
			Set<Starships> starshipses, Set<Species> specieses, Set<Vehicles> vehicleses, Set<Films> filmses) {
		this.codigo = codigo;
		this.planets = planets;
		this.name = name;
		this.birthYear = birthYear;
		this.eyeColor = eyeColor;
		this.gender = gender;
		this.hairColor = hairColor;
		this.height = height;
		this.mass = mass;
		this.skinColor = skinColor;
		this.created = created;
		this.edited = edited;
		this.starshipses = starshipses;
		this.specieses = specieses;
		this.vehicleses = vehicleses;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "homeworld")
	public Planets getPlanets() {
		return this.planets;
	}

	public void setPlanets(Planets planets) {
		this.planets = planets;
	}

	@Column(name = "name", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "birth_year", length = 100)
	public String getBirthYear() {
		return this.birthYear;
	}

	public void setBirthYear(String birthYear) {
		this.birthYear = birthYear;
	}

	@Column(name = "eye_color", length = 100)
	public String getEyeColor() {
		return this.eyeColor;
	}

	public void setEyeColor(String eyeColor) {
		this.eyeColor = eyeColor;
	}

	@Column(name = "gender", length = 100)
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name = "hair_color", length = 100)
	public String getHairColor() {
		return this.hairColor;
	}

	public void setHairColor(String hairColor) {
		this.hairColor = hairColor;
	}

	@Column(name = "height", length = 100)
	public String getHeight() {
		return this.height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	@Column(name = "mass", length = 100)
	public String getMass() {
		return this.mass;
	}

	public void setMass(String mass) {
		this.mass = mass;
	}

	@Column(name = "skin_color", length = 100)
	public String getSkinColor() {
		return this.skinColor;
	}

	public void setSkinColor(String skinColor) {
		this.skinColor = skinColor;
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
	@JoinTable(name = "starships_people", joinColumns = {
			@JoinColumn(name = "codigo_people", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "codigo_starship", nullable = false, updatable = false) })
	public Set<Starships> getStarshipses() {
		return this.starshipses;
	}

	public void setStarshipses(Set<Starships> starshipses) {
		this.starshipses = starshipses;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "species_people", joinColumns = {
			@JoinColumn(name = "codigo_people", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "codigo_specie", nullable = false, updatable = false) })
	public Set<Species> getSpecieses() {
		return this.specieses;
	}

	public void setSpecieses(Set<Species> specieses) {
		this.specieses = specieses;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "vehicles_people", joinColumns = {
			@JoinColumn(name = "codigo_people", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "codigo_vehicle", nullable = false, updatable = false) })
	public Set<Vehicles> getVehicleses() {
		return this.vehicleses;
	}

	public void setVehicleses(Set<Vehicles> vehicleses) {
		this.vehicleses = vehicleses;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "films_people", joinColumns = {
			@JoinColumn(name = "codigo_people", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "codigo_film", nullable = false, updatable = false) })
	public Set<Films> getFilmses() {
		return this.filmses;
	}

	public void setFilmses(Set<Films> filmses) {
		this.filmses = filmses;
	}

	public void imprimeRegistroCompleto() {
		StringBuilder sb = new StringBuilder();
		Integer codigo = getCodigo();
		sb.append(String.format("%-8s", codigo));
		String nombre = Utiles.formatedTextSize(getName(), 30);
		sb.append(String.format("%-30s", nombre));
		String altura = Utiles.checkUnknown(getHeight());
		sb.append(String.format("%-15s", altura));
		String peso = Utiles.checkUnknown(getMass());
		sb.append(String.format("%-15s", peso));
		String pelo = Utiles.checkUnknown(getHairColor());
		sb.append(String.format("%-30s", pelo));
		String piel = Utiles.checkUnknown(getSkinColor());
		sb.append(String.format("%-30s", piel));
		String ojos = Utiles.checkUnknown(getEyeColor());
		sb.append(String.format("%-30s", ojos));
		String nacimiento = Utiles.checkUnknown(getBirthYear());
		sb.append(String.format("%-20s", nacimiento));
		String mundo = "desconocido";
		if (getPlanets() != null && getPlanets().getName() != null) {
			mundo = getPlanets().getName();
		}
		sb.append(String.format("%-20s", mundo));

		String species = "";
		if (getSpecieses().isEmpty()) {
			species = "No tiene especie";
		} else {
			for (Species speci : getSpecieses()) {
				species += speci.getName() + "   ";
			}
		}
		sb.append(String.format("%-30s", species));

		String vehicles = "";
		if (getVehicleses().isEmpty()) {
			vehicles = "No conduce vehículo";
		} else {
			for (Vehicles vehicle : getVehicleses()) {
				if (!vehicle.getName().trim().isEmpty()) {
					vehicles += vehicle.getName() + "   ";
				}
			}
		}
		vehicles = Utiles.formatedTextSize(vehicles, 55);
		sb.append(String.format("%-60s", vehicles));

		String starships = "";
		if (getStarshipses().isEmpty()) {
			starships = "No conduce nave";
		} else {
			for (Starships st : getStarshipses()) {
				starships += st.getName() + "   ";
			}
		}
		starships = Utiles.formatedTextSize(starships, 55);
		sb.append(String.format("%-60s", starships));

		String films = "";
		if (getFilmses().isEmpty()) {
			films = "No ha participado en película";
		} else {
			for (Films film : getFilmses()) {
				films += film.getTitle() + "   ";
			}
		}
		sb.append(String.format("%-100s", Utiles.formatedTextSize(films, 95)));

		String creado = getCreated();
		sb.append(String.format("%-40s", creado));
		String editado = getEdited();
		sb.append(String.format("%-40s", editado));
		System.out.println(sb.toString());
	}

	public void imprimeRegistroDetallado() {
		StringBuilder sb = new StringBuilder();
		Integer codigo = getCodigo();
		sb.append(String.format("%-16s", "Código: " + codigo));
		String nombre = getName().length() > 30 ? getName().substring(0, 27) + "..." : getName();
		sb.append(String.format("%-30s", "Nombre: " + nombre + "\n\nCarácteristicas Físicas\n"));
		String altura = getHeight();
		sb.append(String.format("%-40s", "Altura: " + altura));
		String peso = getMass();
		sb.append(String.format("%-40s", "Peso: " + peso));
		String pelo = getHairColor();
		sb.append(String.format("%-40s", "Color del Pelo: " + pelo) + "\n");
		String piel = getSkinColor();
		sb.append(String.format("%-40s", "Color de Piel: " + piel));
		String ojos = getEyeColor();
		sb.append(String.format("%-40s", "Color de Ojos: " + ojos) + "\n");
		String nacimiento = getBirthYear();
		sb.append(String.format("%-40s", "Año Nacimiento: " + nacimiento));
		if (getPlanets() != null) {
			String mundo = getPlanets().getName();
			sb.append(String.format("%-40s", "Mundo: " + mundo));
		}

		String species = "";
		if (getSpecieses().isEmpty()) {
			species = "No pertenece a ninguna especie";
		} else {
			for (Species speci : getSpecieses()) {
				species += speci.getName() + " ";
			}
		}
		sb.append("Especie: " + species);

		sb.append("\n\nVehículos\n");
		String vehicles = "";
		if (getVehicleses().isEmpty()) {
			vehicles = "No conduce vehículo";
		} else {
			for (Vehicles vehicle : getVehicleses()) {
				vehicles += vehicle.getName() + " ";
			}
		}
		sb.append(vehicles);

		sb.append("\n\nNaves\n");
		String starships = "";
		if (getStarshipses().isEmpty()) {
			starships = "No conduce nave";
		} else {
			for (Starships st : getStarshipses()) {
				starships += st.getName() + " ";
			}
		}
		sb.append(starships);

		sb.append("\n\nPelícula en las que ha participado\n");
		String films = "";
		if (getFilmses().isEmpty()) {
			films = "No ha participado en películas";
		} else {
			for (Films film : getFilmses()) {
				films += "Episodio: " + film.getEpisodeId() + " - ";
				films += film.getTitle() + "\n";
			}
		}
		sb.append(films);

		sb.append("\n");
		String creado = getCreated();
		sb.append(String.format("%-50s", "Creado: " + creado) + "\n");
		String editado = getEdited();
		sb.append(String.format("%-50s", "Editado: " + editado) + "\n");

		System.out.println(sb.toString());
	}

	public void imprimeCodValor() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%-5s", getCodigo()) + "- ");
		String nombre = Utiles.formatedTextSize(getName(), 30);
		sb.append(String.format("%-30s", nombre));
		System.out.println(sb.toString());
	}

}
