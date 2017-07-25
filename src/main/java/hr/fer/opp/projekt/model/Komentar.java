package hr.fer.opp.projekt.model;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "komentari_na_djelo")
@Cacheable(true)
@NamedQueries({ @NamedQuery(name = "Komentar.byId", query = "select k from Komentar as k " + "where k.id=:id") })
public class Komentar {

	public static final int duljinaTeksta = 1024;
	
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Djelo djelo;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Korisnik korisnik;

	@Temporal(TemporalType.TIMESTAMP)
	private Date datumObjave;

	@Column(length=duljinaTeksta, nullable = false)
	private String tekst;

	@Column(nullable = false)
	private Integer ocjena;

	public String getTekst() {
		return this.tekst;
	}

	public Date getDatumObjave() {
		return this.datumObjave;
	}

	public void setDatumObjave(Date date) {
		this.datumObjave = date;
	}

	public Integer getOcjena() {
		return this.ocjena;
	}

	public void setOcjena(int ocjena) {
		this.ocjena = ocjena;
	}

	public Korisnik getKorisnik() {
		return this.korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public void setDjelo(Djelo djelo) {
		this.djelo = djelo;
	}

	public void setTekst(String tekst) {
		this.tekst = tekst;
	}

	public Djelo getDjelo() {
		return this.djelo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
