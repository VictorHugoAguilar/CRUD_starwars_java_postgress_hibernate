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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.tema4.utils.Utiles;

@Entity
@Table(name = "planets")
public class Planets implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int codigo;
	private String name;
	private String diameter;
	private String rotationPeriod;
	private String orbitalPeriod;
	private String gravity;
	private String population;
	private String climate;
	private String terrain;
	private String surfaceWater;
	private String created;
	private String edited;
	private Set<Species> specieses = new HashSet<Species>(0);
	private Set<People> peoples = new HashSet<People>(0);
	private Set<Films> filmses = new HashSet<Films>(0);

	public Planets() {
	}

	public Planets(int codigo) {
		this.codigo = codigo;
	}

	public Planets(int codigo, String name, String diameter, String rotationPeriod, String orbitalPeriod,
			String gravity, String population, String climate, String terrain, String surfaceWater, String created,
			String edited, Set<Species> specieses, Set<People> peoples, Set<Films> filmses) {
		this.codigo = codigo;
		this.name = name;
		this.diameter = diameter;
		this.rotationPeriod = rotationPeriod;
		this.orbitalPeriod = orbitalPeriod;
		this.gravity = gravity;
		this.population = population;
		this.climate = climate;
		this.terrain = terrain;
		this.surfaceWater = surfaceWater;
		this.created = created;
		this.edited = edited;
		this.specieses = specieses;
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

	@Column(name = "diameter", length = 100)
	public String getDiameter() {
		return this.diameter;
	}

	public void setDiameter(String diameter) {
		this.diameter = diameter;
	}

	@Column(name = "rotation_period", length = 100)
	public String getRotationPeriod() {
		return this.rotationPeriod;
	}

	public void setRotationPeriod(String rotationPeriod) {
		this.rotationPeriod = rotationPeriod;
	}

	@Column(name = "orbital_period", length = 100)
	public String getOrbitalPeriod() {
		return this.orbitalPeriod;
	}

	public void setOrbitalPeriod(String orbitalPeriod) {
		this.orbitalPeriod = orbitalPeriod;
	}

	@Column(name = "gravity", length = 100)
	public String getGravity() {
		return this.gravity;
	}

	public void setGravity(String gravity) {
		this.gravity = gravity;
	}

	@Column(name = "population", length = 100)
	public String getPopulation() {
		return this.population;
	}

	public void setPopulation(String population) {
		this.population = population;
	}

	@Column(name = "climate", length = 100)
	public String getClimate() {
		return this.climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	@Column(name = "terrain", length = 100)
	public String getTerrain() {
		return this.terrain;
	}

	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}

	@Column(name = "surface_water", length = 100)
	public String getSurfaceWater() {
		return this.surfaceWater;
	}

	public void setSurfaceWater(String surfaceWater) {
		this.surfaceWater = surfaceWater;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "planets")
	public Set<Species> getSpecieses() {
		return this.specieses;
	}

	public void setSpecieses(Set<Species> specieses) {
		this.specieses = specieses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "planets")
	public Set<People> getPeoples() {
		return this.peoples;
	}

	public void setPeoples(Set<People> peoples) {
		this.peoples = peoples;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "films_planets", joinColumns = {
			@JoinColumn(name = "codigo_planet", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "codigo_film", nullable = false, updatable = false) })
	public Set<Films> getFilmses() {
		return this.filmses;
	}

	public void setFilmses(Set<Films> filmses) {
		this.filmses = filmses;
	}

	public void imprimeRegistro() {
		StringBuilder sb = new StringBuilder();
		int codigo = getCodigo();
		sb.append(String.format("%-10s", codigo));
		String name = getName();
		sb.append(String.format("%-20s", name));
		String diametro = Utiles.checkUnknown(getDiameter());
		sb.append(String.format("%-20s", diametro));
		String rotacion = Utiles.checkUnknown(getRotationPeriod());
		sb.append(String.format("%-20s", rotacion));
		String orbital = Utiles.checkUnknown(getOrbitalPeriod());
		sb.append(String.format("%-20s", orbital));
		String gravedad = Utiles.formatedTextSize(Utiles.checkUnknown(getGravity()), 27);
		sb.append(String.format("%-30s", gravedad));
		String poblacion = Utiles.checkUnknown(getPopulation());
		sb.append(String.format("%-30s", poblacion));
		String clima = Utiles.checkUnknown(getClimate());
		sb.append(String.format("%-30s", clima));
		String terreno = Utiles.checkUnknown(getTerrain());
		sb.append(String.format("%-50s", terreno));
		String superficieAgua = Utiles.checkUnknown(getSurfaceWater());
		sb.append(String.format("%-30s", superficieAgua));
		String creado = getCreated();
		sb.append(String.format("%-40s", creado));
		String editado = getCreated();
		sb.append(String.format("%-40s", editado));

		System.out.println(sb.toString());
	}

	public void imprimeRegistroDetallado() {
		StringBuilder sb = new StringBuilder();
		int codigo = getCodigo();
		sb.append(String.format("%-30s", "Código: " + codigo));
		String name = getName();
		sb.append(String.format("%-50s", "Nombre del Planet: " + name) + "\n");
		sb.append("\nCarácteristicas del Planets\n");
		String diametro = Utiles.checkUnknown(getDiameter());
		sb.append(String.format("%-30s", "Diámetro: " + diametro));
		String rotacion = Utiles.checkUnknown(getRotationPeriod());
		sb.append(String.format("%-30s", "Rotación: " + rotacion));
		String orbital = Utiles.checkUnknown(getOrbitalPeriod());
		sb.append(String.format("%-30s", "Periodo Orbital: " + orbital) + "\n");
		String gravedad = Utiles.checkUnknown(getGravity());
		sb.append(String.format("%-30s", "Gravedad: " + gravedad));
		String poblacion = Utiles.checkUnknown(getPopulation());
		sb.append(String.format("%-30s", "Población: " + poblacion));
		String clima = Utiles.checkUnknown(getClimate());
		sb.append(String.format("%-30s", "Clima: " + clima) + "\n");
		String superficieAgua = Utiles.checkUnknown(getSurfaceWater());
		sb.append(String.format("%-30s", "Superficie Agua: " + superficieAgua));
		String terreno = Utiles.checkUnknown(getTerrain());
		sb.append(String.format("%-60s", "Terreno: " + terreno) + "\n");

		String habitado = "";
		for (People p : getPeoples()) {
			habitado += p.getName() + "   ";
		}
		if (!habitado.isEmpty()) {
			sb.append("\nPlaneta de: \n");
			sb.append(habitado + "\n");
		}

		String especies = "";
		for (Species s : getSpecieses()) {
			especies += s.getName() + "   ";
		}
		if (!especies.isEmpty()) {
			sb.append("\nEspecies que habita: \n");
			sb.append(especies + "\n");
		}

		String films = "";
		for (Films f : getFilmses()) {
			films += f.getTitle() + "   ";
		}
		if (!films.isEmpty()) {
			sb.append("\nFilms donde aparece: \n");
			sb.append(films + "\n");
		}

		String creado = getCreated();
		sb.append(String.format("%-50s", "\nCreado: " + creado));
		sb.append("\n");
		String editado = getCreated();
		sb.append(String.format("%-50s", "Editado: " + editado) + "\n");

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
