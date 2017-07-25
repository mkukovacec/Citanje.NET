package hr.fer.opp.projekt.model;

import javax.servlet.http.HttpServletRequest;

public class BiljeskaFormular extends Formular<Biljeska> {

	private String id;

	private String tekst;

	@Override
	public void popuniIzZahtjeva(HttpServletRequest req) {
		this.id = pripremi(req.getParameter("id"));
		this.tekst = pripremi(req.getParameter("tekst"));
		
	}

	@Override
	public void popuniIzZapisa(Biljeska b) {
		if (b.getId() == null) {
			this.id = "";
		} else {
			this.id = b.getId().toString();
		}
		this.tekst = b.getTekst();
	}

	@Override
	public void popuniUZapis(Biljeska b) {
		if (this.id.isEmpty()) {
			b.setId(null);
		} else {
			b.setId(Long.valueOf(this.id));
		}
		
		b.setTekst(this.tekst);
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
		
		if (this.tekst.isEmpty()){
			greske.put("tekst", "Tekst je obavezan!");
		}

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getTekst() {
		return tekst;
	}

	public void setTekst(String tekst) {
		this.tekst = tekst;
	}
	

}
