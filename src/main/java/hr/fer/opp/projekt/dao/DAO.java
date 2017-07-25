package hr.fer.opp.projekt.dao;

import java.util.List;

import hr.fer.opp.projekt.model.Autor;
import hr.fer.opp.projekt.model.Biljeska;
import hr.fer.opp.projekt.model.Djelo;
import hr.fer.opp.projekt.model.Komentar;
import hr.fer.opp.projekt.model.Korisnik;
import hr.fer.opp.projekt.model.Opomena;
import hr.fer.opp.projekt.model.Report;
import hr.fer.opp.projekt.model.Zanr;

public interface DAO {
	
	// Autor
	public void addAutor(Autor autor) throws DAOException;
	
	public void addAutor(String ime, String prezime, String biografija,
							Korisnik unositelj) throws DAOException;
	
	public Autor getAutor(Long id) throws DAOException;
	
	public Autor getAutor(String ime, String prezime) throws DAOException;
	
	public List<Autor> getAutorLike(String prezime) throws DAOException;
	
	public List<Autor> getSviAutori() throws DAOException;
	
	
	// Biljeska
	public void addBiljeska(Biljeska biljeska) throws DAOException;
	
	public Biljeska getBiljeska(Long id) throws DAOException;
	
	public Biljeska getBiljeska(Korisnik user, Djelo djelo) throws DAOException;
	
	public void removeBiljeska(Long biljeskaID) throws DAOException;
	
	
	// Djelo
	public void addDjelo(Djelo djelo) throws DAOException;
	
	public Djelo getDjelo(Long id) throws DAOException;
	
	public List<Djelo> getDjeloLike(String naziv) throws DAOException;

	public List<Djelo> getSvaDjela() throws DAOException;
	
	public void removeDjelo(Long id) throws DAOException;
	
	
	// Komentar
	public void addKomentar(Komentar komentar) throws DAOException;
	
	public Komentar getKomentar(Long id) throws DAOException;
		
	public void removeKomentar(Long komentarID) throws DAOException;
	
	
	// Korisnik
	public void addKorisnik(Korisnik form) throws DAOException;
	
	public Korisnik getKorisnik(Long id) throws DAOException;
	
	public Korisnik getKorisnik(String nick) throws DAOException;
	
	public List<Korisnik> getSviKorisnici() throws DAOException;
	
	public void removeKorisnik(Long korisnikID) throws DAOException;
	
	
	// Opomena
	public void addOpomena(Opomena opomena) throws DAOException;
	
	public Opomena getOpomena(Long id) throws DAOException;
	
	public List<Opomena> getOpomene(String nick) throws DAOException;
	
	// Report
	public void addReport(Report report) throws DAOException;
	
	public Report getReport(Long id) throws DAOException;
	
	public void removeReport(Long id) throws DAOException;
	
	public List<Report> getReports() throws DAOException;
	
	// Zanr
	public void addZanr(String naziv) throws DAOException;
	
	public Zanr getZanr(Long id) throws DAOException;
	
	public Zanr getZanr(String naziv) throws DAOException;

	public List<Zanr> getSveZanrove() throws DAOException;

}
