package hr.fer.opp.projekt.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="knjizevna_djela")
@Cacheable(true)
@NamedQueries({
	@NamedQuery(name="Djelo.all",
				query="select dj from Djelo as dj order by dj.naslov, dj.godinaIzdanja"),
	@NamedQuery(name="Djelo.likeName",
				query="select dj from Djelo as dj "
						+ "where lower(dj.naslov) like :pattern"),
	@NamedQuery(name="Djelo.byId",
				query="select dj from Djelo as dj "
						+ "where dj.id=:id")
})
public class Djelo {
	
	@Id @GeneratedValue
	private Long id;
	
	@Column(length=200, nullable=false)
	private String naslov;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Autor autor;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Zanr zanr;
	
	@Column(nullable=false)
	private Integer godinaIzdanja;

	@Column(length=1500, nullable=false)
	private String kratakSadrzaj;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date datumObjave;
	
	@ManyToOne
	@JoinColumn(nullable=true)
	private Korisnik unio;
	
	@ManyToOne
	@JoinColumn(nullable=true)
	private Korisnik korisnik;
	
	@OneToMany(mappedBy="djelo", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("datumObjave DESC")
	private List<Komentar> komentari;
	
	@OneToMany(mappedBy="djelo", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("datumObjave DESC")
	private List<Biljeska> biljeske;
	
	public Korisnik getKorisnik() {
		return korisnik;
	}
	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}
	
	public Korisnik getUnio() {
		return unio;
	}
	
	public void setUnio(Korisnik unio) {
		this.unio = unio;
	}

	public String getNaslov() {
		return naslov;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Zanr getZanr() {
		return zanr;
	}

	public void setZanr(Zanr zanr) {
		this.zanr = zanr;
	}

	public Integer getGodinaIzdanja() {
		return godinaIzdanja;
	}

	public void setGodinaIzdanja(int godinaIzdanja) {
		this.godinaIzdanja = godinaIzdanja;
	}

	public String getKratakSadrzaj(){
		return this.kratakSadrzaj;
	}

	public void setKratakSadrzaj(String sadrzaj){
		this.kratakSadrzaj=sadrzaj;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public List<Komentar> getKomentari() {
		return komentari;
	}
	
	public List<Biljeska> getBiljeske() {
		return biljeske;
	}
	
	public Date getDatumObjave() {
		return datumObjave;
	}
	
	public void setDatumObjave(Date datumObjave) {
		this.datumObjave = datumObjave;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Djelo other = (Djelo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
