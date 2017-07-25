package hr.fer.opp.projekt.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name="zanrovi")
@Cacheable(true)
@NamedQueries({
	@NamedQuery(name="Zanr.all",
				query="select zn from Zanr as zn order by zn.naziv"),
	@NamedQuery(name="Zanr.byName",
				query="select zn from Zanr as zn "
						+ "where zn.naziv=:name"),
	@NamedQuery(name="Zanr.byId",
				query="select zn from Zanr as zn "
						+ "where zn.id=:id")
})
public class Zanr {
	
	@Id @GeneratedValue
	private Long id;
	
	@Column(length=100, nullable=false)
	private String naziv;
	
	@OneToMany(mappedBy="zanr", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("naslov ASC")
	List<Djelo> djela = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public List<Djelo> getDjela() {
		return djela;
	}

}
