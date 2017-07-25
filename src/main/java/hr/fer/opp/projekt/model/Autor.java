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
@Table(name="autori")
@Cacheable(true)
@NamedQueries({
	@NamedQuery(name="Autor.all",
				query="select a from Autor as a order by a.imeAutora, a.prezimeAutora"),
	@NamedQuery(name="Autor.byFullName",
				query="select a from Autor as a "
						+ "where a.imeAutora=:firstName and "
						+ "a.prezimeAutora=:lastName"),
	@NamedQuery(name="Autor.likeName",
				query="select a from Autor as a "
						+ "where lower(a.prezimeAutora) like :pattern"),
	@NamedQuery(name="Autor.byId",
				query="select a from Autor as a "
						+ "where a.id=:id")
})
public class Autor {

	@Id
	@GeneratedValue
	private Long id;

	@Column(length = 200, nullable = false)
	private String imeAutora;

	@Column(length = 200, nullable = false)
	private String prezimeAutora;

	@Column(length = 1500, nullable = false)
	private String biografija;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date datumObjave;

	@OneToMany(mappedBy="autor", fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
	@OrderBy("naslov ASC")
	private List<Djelo> djela;
	
	@ManyToOne
	@JoinColumn(nullable=true)
	private Korisnik zadnjiUredio;

	public String getImeAutora() {
		return this.imeAutora;
	}

	public void setImeAutora(String ime) {
		this.imeAutora = ime;
	}

	public String getPrezimeAutora() {
		return this.prezimeAutora;
	}

	public void setPrezimeAutora(String prezime) {
		this.prezimeAutora = prezime;
	}

	public String getBiografija() {
		return this.biografija;
	}

	public void setBiografija(String biografija) {
		this.biografija = biografija;
	}

	public List<Djelo> getDjela() {
		return this.djela;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Korisnik getZadnjiUredio() {
		return zadnjiUredio;
	}
	
	public void setZadnjiUredio(Korisnik zadnjiUredio) {
		this.zadnjiUredio = zadnjiUredio;
	}
	
	public Date getDatumObjave() {
		return datumObjave;
	}
	
	public void setDatumObjave(Date datumObjave) {
		this.datumObjave = datumObjave;
	}
	
}
