package hr.fer.opp.projekt.model;

import javax.servlet.http.HttpServletRequest;

public class AutorFormular extends Formular<Autor> {
	
	private String id;
	
	private String imeAutora;
	
	private String prezimeAutora;
	
	private String biografija; 

	@Override
	public void popuniIzZahtjeva(HttpServletRequest req) {
		this.id = pripremi(req.getParameter("id"));
		this.imeAutora = pripremi(req.getParameter("imeAutora"));
		this.prezimeAutora = pripremi(req.getParameter("prezimeAutora"));
		this.biografija = pripremi(req.getParameter("biografija"));
	}

	@Override
	public void popuniIzZapisa(Autor a) {
		if (a.getId() == null) {
			this.id = "";
		} else {
			this.id = a.getId().toString();
		}
		
		this.imeAutora = a.getImeAutora();
		this.prezimeAutora = a.getPrezimeAutora();
		this.biografija = a.getBiografija();
	}

	@Override
	public void popuniUZapis(Autor a) {
		if (this.id.isEmpty()) {
			a.setId(null);
		} else {
			a.setId(Long.valueOf(this.id));
		}
	
		a.setImeAutora(this.imeAutora);
		a.setPrezimeAutora(this.prezimeAutora);
		a.setBiografija(this.biografija);
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
		
		if (this.biografija.isEmpty()) {
			greske.put("biografija", "Biografija ne smije biti prazna!");
		}
		
		if (this.imeAutora.isEmpty()) {
			greske.put("imeAutora", "Ime autora je obavezno!");
		}
		
		if (this.prezimeAutora.isEmpty()) {
			greske.put("prezimeAutora", "Prezime autora je obavezno!");
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImeAutora() {
		return imeAutora;
	}

	public void setImeAutora(String imeAutora) {
		this.imeAutora = imeAutora;
	}

	public String getPrezimeAutora() {
		return prezimeAutora;
	}

	public void setPrezimeAutora(String prezimeAutora) {
		this.prezimeAutora = prezimeAutora;
	}

	public String getBiografija() {
		return biografija;
	}

	public void setBiografija(String biografija) {
		this.biografija = biografija;
	}

}
