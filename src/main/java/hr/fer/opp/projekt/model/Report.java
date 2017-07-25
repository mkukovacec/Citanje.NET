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
@Table(name="reporti")
@Cacheable(true)
@NamedQueries({
	@NamedQuery(name="Report.all",
			query="select r from Report as r order by r.datumObjave desc"),
	@NamedQuery(name="Report.byId",
				query="select r from Report as r "
					+ "where r.id=:id"),
})
public class Report {
	
	@Id @GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Korisnik prijavljeniKorisnik;
	
	@Column(length=512, nullable=false)
	private String razlog;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Korisnik tuzitelj;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date datumObjave;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Korisnik getPrijavljeniKorisnik() {
		return prijavljeniKorisnik;
	}

	public void setPrijavljeniKorisnik(Korisnik prijavljeniKorisnik) {
		this.prijavljeniKorisnik = prijavljeniKorisnik;
	}

	public Korisnik getTuzitelj() {
		return tuzitelj;
	}

	public void setTuzitelj(Korisnik tuzitelj) {
		this.tuzitelj = tuzitelj;
	}
	
	public Date getDatumObjave() {
		return datumObjave;
	}
	
	public void setDatumObjave(Date datumObjave) {
		this.datumObjave = datumObjave;
	}

	public String getRazlog() {
		return razlog;
	}

	public void setRazlog(String razlog) {
		this.razlog = razlog;
	}
	
}
