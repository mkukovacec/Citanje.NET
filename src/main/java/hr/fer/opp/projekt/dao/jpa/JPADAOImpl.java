package hr.fer.opp.projekt.dao.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.opp.projekt.dao.DAO;
import hr.fer.opp.projekt.dao.DAOException;
import hr.fer.opp.projekt.model.Autor;
import hr.fer.opp.projekt.model.Biljeska;
import hr.fer.opp.projekt.model.Djelo;
import hr.fer.opp.projekt.model.Komentar;
import hr.fer.opp.projekt.model.Korisnik;
import hr.fer.opp.projekt.model.Opomena;
import hr.fer.opp.projekt.model.Report;
import hr.fer.opp.projekt.model.Zanr;

public class JPADAOImpl implements DAO {

	// Autor

	@Override
	public void addAutor(Autor autor) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		autor.setDatumObjave(new Date());
		if (autor.getId() == null) {
			em.persist(autor);
		} else {
			em.merge(autor);
		}
	}

	@Override
	public void addAutor(String ime, String prezime, String biografija, Korisnik unositelj) throws DAOException {
		Autor autor = new Autor();
		autor.setDatumObjave(new Date());
		autor.setImeAutora(ime);
		autor.setPrezimeAutora(prezime);
		autor.setBiografija(biografija);
		autor.setZadnjiUredio(unositelj);
		addAutor(autor);
	}

	@Override
	public Autor getAutor(Long id) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		@SuppressWarnings("unchecked")
		List<Autor> autori = (List<Autor>) em.createNamedQuery("Autor.byId").setParameter("id", id).getResultList();

		return autori.isEmpty() ? null : autori.get(0);
	}

	@Override
	public Autor getAutor(String ime, String prezime) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		@SuppressWarnings("unchecked")
		List<Autor> autori = (List<Autor>) em.createNamedQuery("Autor.byFullName").setParameter("firstName", ime)
				.setParameter("lastName", prezime).getResultList();

		return autori.isEmpty() ? null : autori.get(0);
	}

	@Override
	public List<Autor> getAutorLike(String prezime) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		@SuppressWarnings("unchecked")
		List<Autor> autori = (List<Autor>) em.createNamedQuery("Autor.likeName")
				.setParameter("pattern", prezime.toLowerCase() + "%").getResultList();

		return autori;
	}

	@Override
	public List<Autor> getSviAutori() throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		@SuppressWarnings("unchecked")
		List<Autor> autori = (List<Autor>) em.createNamedQuery("Autor.all").getResultList();

		return autori;
	}

	// Biljeska

	@Override
	public void addBiljeska(Biljeska biljeska) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		biljeska.setDatumObjave(new Date());
		if (biljeska.getId() == null) {
			em.persist(biljeska);
		} else {
			em.merge(biljeska);
		}
	}

	@Override
	public Biljeska getBiljeska(Long id) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		@SuppressWarnings("unchecked")
		List<Biljeska> biljeske = (List<Biljeska>) em.createNamedQuery("Biljeska.byId").setParameter("id", id)
				.getResultList();

		return biljeske.isEmpty() ? null : biljeske.get(0);
	}

	@Override
	public Biljeska getBiljeska(Korisnik user, Djelo djelo) throws DAOException {
		List<Biljeska> biljeske = user.getBiljeske();

		for (Biljeska biljeska : biljeske) {
			if (biljeska.getDjelo().getId().equals(djelo.getId())) {
				return biljeska;
			}
		}

		return null;
	}

	@Override
	public void removeBiljeska(Long biljeskaID) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.remove(getBiljeska(biljeskaID));
	}

	// Djelo

	@Override
	public void addDjelo(Djelo djelo) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		djelo.setDatumObjave(new Date());
		if (djelo.getId() == null) {
			em.persist(djelo);
		} else {
			em.merge(djelo);
		}
	}

	@Override
	public Djelo getDjelo(Long id) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		@SuppressWarnings("unchecked")
		List<Djelo> djela = (List<Djelo>) em.createNamedQuery("Djelo.byId").setParameter("id", id).getResultList();

		return djela.isEmpty() ? null : djela.get(0);
	}

	@Override
	public List<Djelo> getDjeloLike(String naziv) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		@SuppressWarnings("unchecked")
		List<Djelo> djela = (List<Djelo>) em.createNamedQuery("Djelo.likeName")
				.setParameter("pattern", naziv.toLowerCase() + "%").getResultList();

		return djela;
	}

	@Override
	public List<Djelo> getSvaDjela() throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		@SuppressWarnings("unchecked")
		List<Djelo> djela = (List<Djelo>) em.createNamedQuery("Djelo.all").getResultList();
		return djela;
	}

	@Override
	public void removeDjelo(Long id) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		Autor a = getDjelo(id).getAutor();
		Zanr z = getDjelo(id).getZanr();
		int countA = 0;
		int countZ = 0;
		for (Djelo d : getSvaDjela()){
			if (d.getAutor().getId()==a.getId()){
				countA++;
			}
			if (d.getZanr().getId()==z.getId()){
				countZ++;
			}
		}
		em.remove(getDjelo(id));
		if (countA<=1){
			em.remove(a);
		}
		if (countZ<=1){
			em.remove(z);
		}
	}

	// Komentar

	@Override
	public void addKomentar(Komentar komentar) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		komentar.setDatumObjave(new Date());
		if (komentar.getId() == null) {
			em.persist(komentar);
		} else {
			em.merge(komentar);
		}
	}

	@Override
	public Komentar getKomentar(Long id) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		@SuppressWarnings("unchecked")
		List<Komentar> komentari = (List<Komentar>) em.createNamedQuery("Komentar.byId").setParameter("id", id)
				.getResultList();

		return komentari.isEmpty() ? null : komentari.get(0);
	}

	@Override
	public void removeKomentar(Long komentarID) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.remove(getKomentar(komentarID));
	}

	// Korisnik

	@Override
	public void addKorisnik(Korisnik korisnik) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		if (korisnik.getId() == null) {
			em.persist(korisnik);
		} else {
			em.merge(korisnik);
		}
	}

	@Override
	public Korisnik getKorisnik(Long id) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		@SuppressWarnings("unchecked")
		List<Korisnik> korisnici = (List<Korisnik>) em.createNamedQuery("Korisnik.byId").setParameter("id", id)
				.getResultList();

		return korisnici.isEmpty() ? null : korisnici.get(0);
	}

	@Override
	public Korisnik getKorisnik(String nick) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		@SuppressWarnings("unchecked")
		List<Korisnik> korisnici = (List<Korisnik>) em.createNamedQuery("Korisnik.byUsername")
				.setParameter("username", nick).getResultList();

		return korisnici.isEmpty() ? null : korisnici.get(0);
	}

	@Override
	public List<Korisnik> getSviKorisnici() throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		@SuppressWarnings("unchecked")
		List<Korisnik> korisnici = (List<Korisnik>) em.createNamedQuery("Korisnik.all").getResultList();
		return korisnici;
	}

	@Override
	public void removeKorisnik(Long korisnikID) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		
		Korisnik k = getKorisnik(korisnikID);
		for (Djelo d : k.getDjela()){
			if (d.getKorisnik().getId()==k.getId()){
				d.setKorisnik(null);
			}
		}
		for (Djelo d : k.getUnio()){
			d.setUnio(null);
		}
		for (Autor a : k.getAutori()){
			if (a.getZadnjiUredio().getId()==k.getId()){
				a.setZadnjiUredio(null);
			}
		}
		k.setAutori(null);
		k.setDjela(null);
		k.setUnio(null);
		
		em.remove(getKorisnik(korisnikID));
	}

	// Opomena

	@Override
	public void addOpomena(Opomena opomena) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		opomena.setDatumObjave(new Date());
		if (opomena.getId() == null) {
			em.persist(opomena);
		} else {
			em.merge(opomena);
		}
	}

	@Override
	public Opomena getOpomena(Long id) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		@SuppressWarnings("unchecked")
		List<Opomena> opomene = (List<Opomena>) em.createNamedQuery("Opomena.byId").setParameter("id", id)
				.getResultList();

		return opomene.isEmpty() ? null : opomene.get(0);
	}

	@Override
	public List<Opomena> getOpomene(String nick) throws DAOException {
		List<Opomena> opomene = new ArrayList<>();
		Korisnik korisnik = getKorisnik(nick);

		if (korisnik != null) {
			return korisnik.getOpomene();
		}

		return opomene;
	}

	// Zanr

	@Override
	public void addZanr(String naziv) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		Zanr zanr = new Zanr();
		zanr.setNaziv(naziv);
		if (zanr.getId() == null) {
			em.persist(zanr);
		} else {
			em.merge(zanr);
		}
	}

	@Override
	public Zanr getZanr(Long id) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		@SuppressWarnings("unchecked")
		List<Zanr> zanrovi = (List<Zanr>) em.createNamedQuery("Zanr.byId").setParameter("id", id).getResultList();

		return zanrovi.isEmpty() ? null : zanrovi.get(0);
	}

	@Override
	public Zanr getZanr(String naziv) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		@SuppressWarnings("unchecked")
		List<Zanr> zanrovi = (List<Zanr>) em.createNamedQuery("Zanr.byName").setParameter("name", naziv)
				.getResultList();

		return zanrovi.isEmpty() ? null : zanrovi.get(0);
	}

	@Override
	public List<Zanr> getSveZanrove() throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		@SuppressWarnings("unchecked")
		List<Zanr> zanrovi = (List<Zanr>) em.createNamedQuery("Zanr.all").getResultList();

		return zanrovi;
	}

	// REPORT

	@Override
	public void addReport(Report report) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		report.setDatumObjave(new Date());
		if (report.getId() == null) {
			em.persist(report);
		} else {
			em.merge(report);
		}

	}

	@Override
	public Report getReport(Long id) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		@SuppressWarnings("unchecked")
		List<Report> reporti = (List<Report>) em.createNamedQuery("Report.byId").setParameter("id", id).getResultList();

		return reporti.isEmpty() ? null : reporti.get(0);
	}

	@Override
	public void removeReport(Long id) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.remove(getReport(id));
	}
	
	@Override
	public List<Report> getReports() throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		@SuppressWarnings("unchecked")
		List<Report> reporti = (List<Report>) em.createNamedQuery("Report.all").getResultList();
		return reporti;
	}

}
