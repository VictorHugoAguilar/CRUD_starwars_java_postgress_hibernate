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
@Table(name = "films")
public class Films implements Serializable {

	private static final long serialVersionUID = 1L;
	private int codigo;
	private String title;
	private String episodeId;
	private String openingCrawl;
	private String director;
	private String producer;
	private String releaseDate;
	private String created;
	private String edited;
	private Set<Starships> starshipses = new HashSet<Starships>(0);
	private Set<Planets> planetses = new HashSet<Planets>(0);
	private Set<People> peoples = new HashSet<People>(0);
	private Set<Vehicles> vehicleses = new HashSet<Vehicles>(0);

	public Films() {
	}

	public Films(int codigo) {
		this.codigo = codigo;
	}

	public Films(int codigo, String title, String episodeId, String openingCrawl, String director, String producer,
			String releaseDate, String created, String edited, Set<Starships> starshipses, Set<Planets> planetses,
			Set<People> peoples, Set<Vehicles> vehicleses) {
		this.codigo = codigo;
		this.title = title;
		this.episodeId = episodeId;
		this.openingCrawl = openingCrawl;
		this.director = director;
		this.producer = producer;
		this.releaseDate = releaseDate;
		this.created = created;
		this.edited = edited;
		this.starshipses = starshipses;
		this.planetses = planetses;
		this.peoples = peoples;
		this.vehicleses = vehicleses;
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

	@Column(name = "title", length = 100)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "episode_id", length = 100)
	public String getEpisodeId() {
		return this.episodeId;
	}

	public void setEpisodeId(String episodeId) {
		this.episodeId = episodeId;
	}

	@Column(name = "opening_crawl", length = 10485760)
	public String getOpeningCrawl() {
		return this.openingCrawl;
	}

	public void setOpeningCrawl(String openingCrawl) {
		this.openingCrawl = openingCrawl;
	}

	@Column(name = "director", length = 100)
	public String getDirector() {
		return this.director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	@Column(name = "producer", length = 100)
	public String getProducer() {
		return this.producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	@Column(name = "release_date", length = 100)
	public String getReleaseDate() {
		return this.releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
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
	@JoinTable(name = "starships_films", joinColumns = {
			@JoinColumn(name = "codigo_film", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "codigo_starship", nullable = false, updatable = false) })
	public Set<Starships> getStarshipses() {
		return this.starshipses;
	}

	public void setStarshipses(Set<Starships> starshipses) {
		this.starshipses = starshipses;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "films_planets", joinColumns = {
			@JoinColumn(name = "codigo_film", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "codigo_planet", nullable = false, updatable = false) })
	public Set<Planets> getPlanetses() {
		return this.planetses;
	}

	public void setPlanetses(Set<Planets> planetses) {
		this.planetses = planetses;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "films_people", joinColumns = {
			@JoinColumn(name = "codigo_film", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "codigo_people", nullable = false, updatable = false) })
	public Set<People> getPeoples() {
		return this.peoples;
	}

	public void setPeoples(Set<People> peoples) {
		this.peoples = peoples;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "vehicles_films", joinColumns = {
			@JoinColumn(name = "codigo_film", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "codigo_vehicle", nullable = false, updatable = false) })
	public Set<Vehicles> getVehicleses() {
		return this.vehicleses;
	}

	public void setVehicleses(Set<Vehicles> vehicleses) {
		this.vehicleses = vehicleses;
	}

	@Override
	public String toString() {
		return "Films [codigo=" + codigo + ", title=" + title + ", episodeId=" + episodeId + ", openingCrawl="
				+ openingCrawl + ", director=" + director + ", producer=" + producer + ", releaseDate=" + releaseDate
				+ ", created=" + created + ", edited=" + edited + ", starshipses=" + starshipses + ", planetses="
				+ planetses + ", peoples=" + peoples + ", vehicleses=" + vehicleses + "]";
	}

	public void imprimeRegistroDetallado() {
		StringBuilder sb = new StringBuilder();
		Integer intCodigo = getCodigo();
		sb.append(String.format("%-20s", "Código: " + intCodigo));
		String titles = getTitle();
		sb.append(String.format("%-40s", "Título: " + titles));
		String episodio = getEpisodeId();
		sb.append(String.format("%-20s", "Episodio: " + episodio) + "\n");
		String directors = getDirector();
		sb.append(String.format("%-40s", "Director: " + directors));
		String productors = getProducer();
		sb.append(String.format("%-60s", "Productores: " + productors));
		sb.append("\nSinopsis: \n");
		String sinopsis = getOpeningCrawl();
		sb.append(String.format("%-65s", sinopsis) + "\n");
		String creado = getCreated();
		String personajes = "";
		int i = 1;
		for (People p : getPeoples()) {
			if (i % 5 == 0) {
				personajes += "\n";
			}
			personajes += p.getName() + "   ";
			i++;
		}
		if (!personajes.isEmpty()) {
			sb.append("\nPersonajes de la película: \n");
			sb.append(personajes + "\n");
		}
		String starships = "";
		for (Starships s : getStarshipses()) {
			starships += s.getName() + "   ";
		}
		if (!starships.isEmpty()) {
			sb.append("\nNaves en la película: \n");
			sb.append(starships + "\n");
		}
		String vehicles = "";
		for (Vehicles v : getVehicleses()) {
			vehicles += v.getName() + "   ";
		}
		if (!vehicles.isEmpty()) {
			sb.append("\nVehículos en la película: \n");
			sb.append(vehicles + "\n");
		}
		String planets = "";
		for (Planets p : getPlanetses()) {
			planets += p.getName() + "   ";
		}
		if (!planets.isEmpty()) {
			sb.append("\nPlanetas en la película: \n");
			sb.append(planets + "\n");
		}
		sb.append(String.format("%-50s", "\nCreado: " + creado) + "\n");
		String editado = getCreated();
		sb.append(String.format("%-50s", "Editado: " + editado) + "\n");
		System.out.println(sb.toString());
	}

	public void imprimeRegistro() {
		StringBuilder sb = new StringBuilder();
		Integer intCodigo = getCodigo();
		sb.append(String.format("%-10s", intCodigo));
		String titles = getTitle();
		sb.append(String.format("%-30s", titles));
		String episodio = getEpisodeId();
		sb.append(String.format("%-10s", episodio));
		String directors = getDirector();
		sb.append(String.format("%-30s", directors));
		String productors = getProducer();
		sb.append(String.format("%-50s", productors));
		String fechaSalida = getReleaseDate();
		sb.append(String.format("%-30s", fechaSalida));
		String sinopsis = Utiles.formatedTextSize(getOpeningCrawl(), 60);
		sb.append(String.format("%-65s", sinopsis));
		String creado = getCreated();
		sb.append(String.format("%-40s", creado));
		String editado = getCreated();
		sb.append(String.format("%-40s", editado));
		System.out.println(sb.toString());
	}

	public void imprimeCodValor() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%-5s", getCodigo()) + " - ");
		String title = Utiles.formatedTextSize(getTitle(), 30);
		sb.append(String.format("%-30s", title));
		System.out.println(sb.toString());
	}
}
