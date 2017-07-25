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
@Table(name="biljeske")
@Cacheable(true)
@NamedQueries({
	@NamedQuery(name="Biljeska.byId",
				query="select b from Biljeska as b "
					+ "where b.id=:id")
})
public class Biljeska {

	@Id @GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Korisnik korisnik;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Djelo djelo;
	
	@Column(length=7500, nullable=false)
	private String tekst;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date datumObjave;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Korisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public Djelo getDjelo() {
		return djelo;
	}

	public void setDjelo(Djelo djelo) {
		this.djelo = djelo;
	}

	public String getTekst() {
		return tekst;
	}

	public void setTekst(String tekst) {
		this.tekst = tekst;
	}

	public Date getDatumObjave() {
		return datumObjave;
	}
	
	public void setDatumObjave(Date datumObjave) {
		this.datumObjave = datumObjave;
	}
	
}
