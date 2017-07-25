package hr.fer.opp.projekt.model;

import javax.servlet.http.HttpServletRequest;

import hr.fer.opp.projekt.dao.DAOProvider;

public class OpomenaFormular extends Formular<Opomena> {
	
	private String id;
	
	private String korisnik;
	
	private String poruka;
	
	private String stupanj;
	
	private boolean pregledana = false;

	@Override
	public void popuniIzZahtjeva(HttpServletRequest req) {
		this.id = pripremi(req.getParameter("id"));
		this.korisnik = pripremi(req.getParameter("korisnik"));
		this.poruka = pripremi(req.getParameter("poruka"));
		this.stupanj = pripremi(req.getParameter("stupanj"));
	}

	@Override
	public void popuniIzZapisa(Opomena o) {
		if (o.getId() == null) {
			this.id = "";
		} else {
			this.id = o.getId().toString();
		}
		
		this.korisnik = o.getOpomenutiKorisnik().getKorisnickoIme();
		this.poruka = o.getPoruka();
		this.stupanj = o.getStupanj().toString();
	}

	@Override
	public void popuniUZapis(Opomena o) {
		if (this.id.isEmpty()) {
			o.setId(null);
		} else {
			o.setId(Long.valueOf(this.id));
		}
		
		// Tu bi moglo puknuti...
		o.setOpomenutiKorisnik(
				DAOProvider.getDAO().getKorisnik(
						this.korisnik)
				);
		o.setPoruka(this.poruka);
		o.setStupanj(Integer.valueOf(this.stupanj));
	}

	@Override
	public void validiraj() {
		greske.clear();
		
		if (!this.id.isEmpty()) {
			try {
				Long.parseLong(this.id);
			} catch(NumberFormatException ex) {
				greske.put("id", "Vrijednost identifikatora nije valjana.");
			}
		}
		
		if (this.korisnik.isEmpty()) {
			greske.put("korisnik", "Ime korisnika je obavezno!");
		} else {
			Korisnik k = DAOProvider.getDAO().getKorisnik(this.korisnik);
			
			if (k == null) {
				greske.put("korisnik", "Ne postoji korisnik sa nadimkom: \""
										+ this.korisnik + "\"");
			}
		}
		
		if (this.poruka.isEmpty()) {
			greske.put("poruka", "Tekst opomene je obavezan!");
		}
		
		if (this.stupanj.isEmpty()) {
			greske.put("stupanj", "Stupanj opomene je obavezan!");
		} else {
			try {
				int stupanj = Integer.parseInt(this.stupanj);
				
				if (stupanj < 1 || stupanj > 3) {
					greske.put("stupanj", "Stupanj opomene mora biti između 1 i 3.");
				}
			} catch (NumberFormatException ex) {
				greske.put("stupanj", "Vrijednost stupnja nije valjani broj.");
			}
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(String korisnik) {
		this.korisnik = korisnik;
	}

	public String getPoruka() {
		return poruka;
	}

	public void setPoruka(String poruka) {
		this.poruka = poruka;
	}

	public String getStupanj() {
		return stupanj;
	}

	public void setStupanj(String stupanj) {
		this.stupanj = stupanj;
	}

	public boolean isPregledana() {
		return pregledana;
	}

	public void setPregledana(boolean pregledana) {
		this.pregledana = pregledana;
	}

}
