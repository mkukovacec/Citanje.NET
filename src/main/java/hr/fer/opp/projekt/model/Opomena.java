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
@Table(name="opomene")
@Cacheable(true)
@NamedQueries({
	@NamedQuery(name="Opomena.byId",
				query="select o from Opomena as o "
					+ "where o.id=:id"),
})
public class Opomena {
	
	@Id @GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Korisnik opomenutiKorisnik;
	
	@Column(length=512, nullable=false)
	private String poruka;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date datumObjave;
	
	@Column(nullable=false)
	private int stupanj;
	
	@Column(nullable=false)
	private boolean pregledana;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Korisnik getOpomenutiKorisnik() {
		return opomenutiKorisnik;
	}

	public void setOpomenutiKorisnik(Korisnik opomenutiKorisnik) {
		this.opomenutiKorisnik = opomenutiKorisnik;
	}

	public String getPoruka() {
		return poruka;
	}

	public void setPoruka(String poruka) {
		this.poruka = poruka;
	}

	public Integer getStupanj() {
		return stupanj;
	}

	public void setStupanj(int stupanj) {
		this.stupanj = stupanj;
	}

	public boolean isPregledana() {
		return pregledana;
	}

	public void setPregledana(boolean pregledana) {
		this.pregledana = pregledana;
	}

	public Date getDatumObjave() {
		return datumObjave;
	}
	
	public void setDatumObjave(Date datumObjave) {
		this.datumObjave = datumObjave;
	}
	
}
