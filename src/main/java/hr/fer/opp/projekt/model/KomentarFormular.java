package hr.fer.opp.projekt.model;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class KomentarFormular extends Formular<Komentar> {

	private String id;

	private String komentar;

	private String ocjena;

	@Override
	public void popuniIzZahtjeva(HttpServletRequest req) {
		this.id = pripremi(req.getParameter("id"));
		this.komentar = pripremi(req.getParameter("komentar"));
		this.ocjena = pripremi(req.getParameter("ocjena"));
	}

	@Override
	public void popuniIzZapisa(Komentar k) {
		if (k.getId() == null) {
			this.id = "";
		} else {
			this.id = k.getId().toString();
		}

		this.komentar = k.getTekst();
		if (k.getOcjena() == null) {
			this.ocjena = "";
		} else {
			this.ocjena = k.getOcjena().toString();
		}
	}

	@Override
	public void popuniUZapis(Komentar k) {
		if (this.id.isEmpty()) {
			k.setId(null);
		} else {
			k.setId(Long.valueOf(this.id));
		}

		k.setTekst(this.komentar);
		k.setOcjena(Integer.parseInt(this.ocjena));
		k.setDatumObjave(new Date());
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

		if (this.komentar.isEmpty()) {
			greske.put("komentar", "Tekst komentara je obavezan!");
		} else if (this.komentar.length() > Komentar.duljinaTeksta) {
			greske.put("komentar", "Komentar ima više od "
					+ Komentar.duljinaTeksta + " znakova!");
		}

		if (this.ocjena.isEmpty()) {
			greske.put("ocjena", "Ocjena je obavezna!");
		}

		try {
			int a = Integer.parseInt(ocjena);
			if (a < 1 || a > 5) {
				greske.put("ocjena", "Ocjena mora biti od 1 do 5!");
			}
		} catch (NumberFormatException e) {
			greske.put("ocjena", "Ocjena mora biti od 1 do 5!");
		}

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKomentar() {
		return komentar;
	}

	public void setKomentar(String komentar) {
		this.komentar = komentar;
	}

	public String getOcjena() {
		return ocjena;
	}

	public void setOcjena(String ocjena) {
		this.ocjena = ocjena;
	}

}
