package hr.fer.opp.projekt.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import hr.fer.opp.projekt.utility.Hashing;

@Entity
@Table(name="korisnici")
@Cacheable(true)
@NamedQueries({
	@NamedQuery(name="Korisnik.all",
				query="select kor from Korisnik as kor order by kor.korisnickoIme"),
	@NamedQuery(name="Korisnik.byUsername",
				query="select kor from Korisnik as kor "
						+ "where kor.korisnickoIme=:username"),
	@NamedQuery(name="Korisnik.byId",
				query="select kor from Korisnik as kor "
						+ "where kor.id=:id")
})
public class Korisnik {
	
	public static final int duljinaKorImena = 100;
	
	public static final int duljinaLozinke = 1024;
	
	public static final int duljinaImena = 512;
	
	public static final int duljinaPrezimena = 768;
	
	public static final int duljinaEmaila = 140;
	
	public static final int duljinaLokacije = 200;

	@Id @GeneratedValue
	private Long id;
	
	@Column(length=duljinaKorImena, nullable=false)
	private String korisnickoIme;
	
	@Column(length=duljinaLozinke, nullable=false)
	private String lozinka;
	
	@Column(length=duljinaImena, nullable=false)
	private String ime;
	
	@Column(length=duljinaPrezimena, nullable=false)
	private String prezime;
	
	@Column(length=duljinaEmaila, nullable=false)
	private String email;
	
	@Column(length=duljinaLokacije, nullable=false)
	private String lokacija;
	
	@Temporal(TemporalType.DATE)
	private Date datumRod;
	
	@Column(nullable=false)
	private boolean isAdmin = true;
	
	@Column(nullable=false)
	private boolean isBanned = false;
	
	@OneToMany(mappedBy="opomenutiKorisnik", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("datumObjave DESC")
	private List<Opomena> opomene = new ArrayList<>();
	
	@OneToMany(mappedBy="korisnik", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("datumObjave DESC")
	private List<Biljeska> biljeske = new ArrayList<>();
	
	@OneToMany(mappedBy="korisnik", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("datumObjave DESC")
	private List<Komentar> komentari = new ArrayList<>();
	
	@OneToMany(mappedBy="prijavljeniKorisnik", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("datumObjave DESC")
	private List<Report> reports = new ArrayList<>();
	
	@OneToMany(mappedBy="tuzitelj", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("datumObjave DESC")
	private List<Report> reportsTuzio = new ArrayList<>();
		
	@OneToMany(mappedBy="unio", fetch=FetchType.LAZY, cascade={CascadeType.MERGE, CascadeType.PERSIST})
	@OrderBy("naslov ASC")
	private List<Djelo> unio = new ArrayList<>();
	
	@OneToMany(mappedBy="korisnik", fetch=FetchType.LAZY, cascade={CascadeType.MERGE, CascadeType.PERSIST})
	@OrderBy("naslov ASC")
	private List<Djelo> djela = new ArrayList<>();
	
	@OneToMany(mappedBy="zadnjiUredio", fetch=FetchType.LAZY, cascade={CascadeType.MERGE, CascadeType.PERSIST})
	@OrderBy("imeAutora, prezimeAutora")
	private List<Autor> autori = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = Hashing.izracunajHash(lozinka);
	}
	
	public void setHashLozinka(String hash) {
		this.lozinka = hash;
	}
	
	public boolean provjeriLozinku(String unos) {
		return this.lozinka.equals(
				Hashing.izracunajHash(unos)
		);
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLokacija() {
		return lokacija;
	}

	public void setLokacija(String lokacija) {
		this.lokacija = lokacija;
	}

	public Date getDatumRod() {
		return datumRod;
	}

	public void setDatumRod(Date datumRod) {
		this.datumRod = datumRod;
	}
	
	public void changeAdminStatus(){
		this.isAdmin^=true;
	}
	
	public void setAdminStatus(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public void changeBannedStatus(){
		this.isBanned^=true;
	}

	public List<Opomena> getOpomene() {
		return opomene;
	}
	
	public List<Biljeska> getBiljeske() {
		return biljeske;
	}
	
	public Set<Djelo> getBiljeskeNa() {
		Set<Djelo> doprinos = new HashSet<>();
		
		if (biljeske != null) {
			for (Biljeska b : biljeske) {
				doprinos.add(b.getDjelo());
			}
		}
		
		return doprinos;
	}
	
	public List<Komentar> getKomentari() {
		return komentari;
	}
	
	public Set<Djelo> getKomentariNa() {
		Set<Djelo> doprinos = new HashSet<>();
		
		if (komentari != null) {
			for (Komentar k : komentari) {
				doprinos.add(k.getDjelo());
			}
		}
		
		return doprinos;
	}
	
	public List<Djelo> getDjela() {
		return djela;
	}
	
	public List<Autor> getAutori() {
		return autori;
	}
	
	public List<Djelo> getUnio() {
		return unio;
	}
	
	public List<Report> getReports() {
		return reports;
	}

	public void setUnio(List<Djelo> unio) {
		this.unio = unio;
	}

	public void setDjela(List<Djelo> djela) {
		this.djela = djela;
	}

	public void setAutori(List<Autor> autori) {
		this.autori = autori;
	}

	public boolean getIsAdmin(){
		return this.isAdmin;
	}
	
	public boolean getIsBanned(){
		return this.isBanned;
	}
	
	public void setIsBanned(boolean isBanned){
		this.isBanned=isBanned;
	}
}
