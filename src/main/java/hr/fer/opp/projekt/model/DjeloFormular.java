package hr.fer.opp.projekt.model;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import hr.fer.opp.projekt.dao.DAOProvider;

public class DjeloFormular extends Formular<Djelo> {

	private String id;

	private String naslov;

	private String imeAutora;

	private String prezimeAutora;

	private String zanr;

	private String godinaIzdanja;

	private String kratakSadrzaj;

	private String biografija;

	@Override
	public void popuniIzZahtjeva(HttpServletRequest req) {
		this.id = pripremi(req.getParameter("id"));
		this.imeAutora = pripremi(req.getParameter("imeAutora"));
		this.prezimeAutora = pripremi(req.getParameter("prezimeAutora"));
		this.naslov = pripremi(req.getParameter("naslov"));
		this.zanr = pripremi(req.getParameter("zanr"));
		this.godinaIzdanja = pripremi(req.getParameter("godinaIzdanja"));
		this.kratakSadrzaj = pripremi(req.getParameter("kratakSadrzaj"));
		this.biografija = pripremi(req.getParameter("biografija"));
	}

	@Override
	public void popuniIzZapisa(Djelo d) {
		if (d.getId() == null) {
			this.id = "";
		} else {
			this.id = d.getId().toString();
		}

		this.naslov = d.getNaslov();

		if (d.getAutor() == null) {
			this.imeAutora = "";
			this.prezimeAutora = "";
			this.biografija = "";
		} else {
			this.imeAutora = d.getAutor().getImeAutora();
			this.prezimeAutora = d.getAutor().getPrezimeAutora();
			this.biografija = d.getAutor().getBiografija();
		}

		if (d.getZanr() == null) {
			this.zanr = "";
		} else {
			this.zanr = d.getZanr().getNaziv();
		}
		if (d.getGodinaIzdanja() == null) {
			this.godinaIzdanja = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
		} else {
			this.godinaIzdanja = d.getGodinaIzdanja().toString();
		}
		this.kratakSadrzaj = d.getKratakSadrzaj();
	}

	@Override
	public void popuniUZapis(Djelo d) {
		if (this.id.isEmpty()) {
			d.setId(null);
		} else {
			d.setId(Long.valueOf(this.id));
		}

		Autor autor = DAOProvider.getDAO().getAutor(this.imeAutora, this.prezimeAutora);

		if (autor == null) {
			DAOProvider.getDAO().addAutor(this.imeAutora, this.prezimeAutora,
					this.biografija, d.getKorisnik());
		}
		
		Zanr zanr = DAOProvider.getDAO().getZanr(this.zanr);
		if (zanr == null) {
			DAOProvider.getDAO().addZanr(this.zanr);
		}

		d.setAutor(DAOProvider.getDAO().getAutor(this.imeAutora, this.prezimeAutora));
		d.setNaslov(this.naslov);
		d.setZanr(DAOProvider.getDAO().getZanr(this.zanr));
		d.setGodinaIzdanja(Integer.valueOf(this.godinaIzdanja));
		d.setKratakSadrzaj(this.kratakSadrzaj);
	}

	@Override
	public void validiraj() {
		greske.clear();

		if (!this.id.isEmpty()) {
			try {
				Long.parseLong(this.id);
			} catch (NumberFormatException ex) {
				greske.put("id", "Vrijednost identifikatora nije valjana.");
			}
		}

		if (this.imeAutora.isEmpty()) {
			greske.put("imeAutora", "Ime autora je obavezno!");
		}

		if (this.prezimeAutora.isEmpty()) {
			greske.put("prezimeAutora", "Prezime autora je obavezno!");
		}
		
		if (!this.imeAutora.isEmpty() && !this.prezimeAutora.isEmpty()) {
			Autor a = DAOProvider.getDAO().getAutor(
						this.imeAutora, this.prezimeAutora);
			if (a == null && this.biografija.isEmpty()) {
				greske.put("biografija", "Biografija autora je obavezna!");
			}
		} else if (this.biografija.isEmpty()) {
			greske.put("biografija", "Biografija autora je obavezna!");
		}

		if (this.naslov.isEmpty()) {
			greske.put("naslov", "Naslov je obavezan!");
		}

		if (this.zanr.isEmpty()) {
			greske.put("zanr", "Žanr je obavezan!");
		}

		if (this.godinaIzdanja.isEmpty()) {
			greske.put("godinaIzdanja", "Godina izdanja je obavezna!");
		} else {
			try {
				Integer godina = Integer.parseInt(this.godinaIzdanja);
				if (godina < 0) {
					greske.put("godinaIzdanja", "Godina mora biti pozitivan broj!");
				}

				if (godina > Calendar.getInstance().get(Calendar.YEAR)) {
					greske.put("godinaIzdanja", "Godina izdavanja ne može biti u budućnosti!");
				}
			} catch (NumberFormatException ex) {
				greske.put("godinaIzdanja", "Vrijednost identifikatora nije valjana.");
			}
		}

		if (this.kratakSadrzaj.isEmpty()) {
			greske.put("kratakSadrzaj", "Kratak sadržaj je obavezan!");
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNaslov() {
		return naslov;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public String getZanr() {
		return zanr;
	}

	public void setZanr(String zanr) {
		this.zanr = zanr;
	}

	public String getGodinaIzdanja() {
		return godinaIzdanja;
	}

	public void setGodinaIzdanja(String godinaIzdanja) {
		this.godinaIzdanja = godinaIzdanja;
	}

	public String getImeAutora() {
		return imeAutora;
	}

	public String getPrezimeAutora() {
		return prezimeAutora;
	}

	public void setImeAutora(String imeAutora) {
		this.imeAutora = imeAutora;
	}

	public void setPrezimeAutora(String prezimeAutora) {
		this.prezimeAutora = prezimeAutora;
	}

	public String getKratakSadrzaj() {
		return kratakSadrzaj;
	}

	public void setKratakSadrzaj(String kratakSadrzaj) {
		this.kratakSadrzaj = kratakSadrzaj;
	}

	public String getBiografija() {
		return biografija;
	}

	public void setBiografija(String biografija) {
		this.biografija = biografija;
	}

}
