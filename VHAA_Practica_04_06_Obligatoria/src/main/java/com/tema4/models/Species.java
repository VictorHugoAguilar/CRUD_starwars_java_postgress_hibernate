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
@Table(name = "species")
public class Species implements Serializable {

	private static final long serialVersionUID = 1L;
	private int codigo;
	private Planets planets;
	private String name;
	private String classification;
	private String designation;
	private String averageHeight;
	private String averageLifespan;
	private String eyeColors;
	private String hairColors;
	private String skinColors;
	private String language;
	private String created;
	private String edited;
	private Set<People> peoples = new HashSet<People>(0);

	public Species() {
	}

	public Species(int codigo) {
		this.codigo = codigo;
	}

	public Species(int codigo, Planets planets, String name, String classification, String designation,
			String averageHeight, String averageLifespan, String eyeColors, String hairColors, String skinColors,
			String language, String created, String edited, Set<People> peoples) {
		this.codigo = codigo;
		this.planets = planets;
		this.name = name;
		this.classification = classification;
		this.designation = designation;
		this.averageHeight = averageHeight;
		this.averageLifespan = averageLifespan;
		this.eyeColors = eyeColors;
		this.hairColors = hairColors;
		this.skinColors = skinColors;
		this.language = language;
		this.created = created;
		this.edited = edited;
		this.peoples = peoples;
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

	@Column(name = "classification", length = 100)
	public String getClassification() {
		return this.classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	@Column(name = "designation", length = 100)
	public String getDesignation() {
		return this.designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	@Column(name = "average_height", length = 100)
	public String getAverageHeight() {
		return this.averageHeight;
	}

	public void setAverageHeight(String averageHeight) {
		this.averageHeight = averageHeight;
	}

	@Column(name = "average_lifespan", length = 100)
	public String getAverageLifespan() {
		return this.averageLifespan;
	}

	public void setAverageLifespan(String averageLifespan) {
		this.averageLifespan = averageLifespan;
	}

	@Column(name = "eye_colors", length = 100)
	public String getEyeColors() {
		return this.eyeColors;
	}

	public void setEyeColors(String eyeColors) {
		this.eyeColors = eyeColors;
	}

	@Column(name = "hair_colors", length = 100)
	public String getHairColors() {
		return this.hairColors;
	}

	public void setHairColors(String hairColors) {
		this.hairColors = hairColors;
	}

	@Column(name = "skin_colors", length = 100)
	public String getSkinColors() {
		return this.skinColors;
	}

	public void setSkinColors(String skinColors) {
		this.skinColors = skinColors;
	}

	@Column(name = "language", length = 100)
	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
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
	@JoinTable(name = "species_people", joinColumns = {
			@JoinColumn(name = "codigo_specie", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "codigo_people", nullable = false, updatable = false) })
	public Set<People> getPeoples() {
		return this.peoples;
	}

	public void setPeoples(Set<People> peoples) {
		this.peoples = peoples;
	}

	public void imprimeRegistro() {
		StringBuilder sb = new StringBuilder();
		int codigo = getCodigo();
		sb.append(String.format("%-8s", codigo));
		String name = Utiles.formatedTextSize(getName(), 15);
		sb.append(String.format("%-20s", name));
		String designacion = getDesignation();
		sb.append(String.format("%-20s", designacion));
		String alturaPromedio = Utiles.checkUnknown(getAverageHeight());
		sb.append(String.format("%-20s", alturaPromedio));
		String esperanzaVida = Utiles.checkUnknown(getAverageLifespan());
		sb.append(String.format("%-20s", esperanzaVida));
		String lenguaje = Utiles.checkUnknown(getLanguage());
		sb.append(String.format("%-20s", lenguaje));
		String mundo = getPlanets() == null ? "desconocido" : getPlanets().getName();
		sb.append(String.format("%-20s", mundo));
		String ojos = Utiles.checkUnknown(getEyeColors());
		sb.append(String.format("%-50s", ojos));
		String pelo = Utiles.checkUnknown(getHairColors());
		sb.append(String.format("%-50s", pelo));
		String piel = Utiles.checkUnknown(getSkinColors());
		sb.append(String.format("%-50s", piel));
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
		sb.append(String.format("%-50s", "Nombre de la especie: " + name));
		String designacion = getDesignation();
		sb.append(String.format("%-50s", "Designación: " + designacion) + " \n");
		sb.append("\nCarácteristicas Físicas\n");
		String alturaPromedio = Utiles.checkUnknown(getAverageHeight());
		sb.append(String.format("%-30s", "Altura promedio: " + alturaPromedio));
		String esperanzaVida = Utiles.checkUnknown(getAverageLifespan());
		sb.append(String.format("%-30s", "Esperanza de vida: " + esperanzaVida));
		String lenguaje = Utiles.checkUnknown(getLanguage());
		sb.append(String.format("%-30s", "Lenguaje: " + lenguaje) + "\n");
		String ojos = Utiles.checkUnknown(getEyeColors());
		sb.append(String.format("%-50s", "Color de ojos: " + ojos) + "\n");
		String pelo = Utiles.checkUnknown(getHairColors());
		sb.append(String.format("%-50s", "Color de pelo: " + pelo));
		sb.append("\n");
		String piel = Utiles.checkUnknown(getSkinColors());
		sb.append(String.format("%-50s", "Color de piel: " + piel) + "\n");

		String mundo = getPlanets() == null ? "desconocido" : getPlanets().getName();
		sb.append(String.format("%-50s", "\nMundo: " + mundo) + "\n");

		String peoples = "";
		for (People p : getPeoples()) {
			peoples += p.getName() + "   ";
		}
		if (!peoples.isEmpty()) {
			sb.append("\nPeoples de esta especie: \n");
			sb.append(peoples + "\n");
		}
		String creado = getCreated();
		sb.append(String.format("%-50s", "\nCreado: " + creado) + "\n");
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
