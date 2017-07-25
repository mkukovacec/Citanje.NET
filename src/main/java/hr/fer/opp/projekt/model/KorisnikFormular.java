package hr.fer.opp.projekt.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import hr.fer.opp.projekt.dao.DAOProvider;

public class KorisnikFormular extends Formular<Korisnik> {

	private String id;

	private String korisnickoIme;

	private String lozinka;

	private String ime;

	private String prezime;

	private String email;

	private String lokacija;

	private String datumRod;

	private Boolean isAdmin;

	private Boolean isBanned;

	private String avatar;

	private SimpleDateFormat df = new SimpleDateFormat("dd MM yyyy");

	@Override
	public void popuniIzZahtjeva(HttpServletRequest req) {
		this.id = pripremi(req.getParameter("id"));
		this.korisnickoIme = pripremi(req.getParameter("korisnickoIme"));
		this.lozinka = pripremi(req.getParameter("lozinka"));
		this.ime = pripremi(req.getParameter("ime"));
		this.prezime = pripremi(req.getParameter("prezime"));
		this.email = pripremi(req.getParameter("email"));
		this.lokacija = pripremi(req.getParameter("lokacija"));
		this.datumRod = pripremi(req.getParameter("datumRod"));
	}

	@Override
	public void popuniIzZapisa(Korisnik k) {
		if (k.getId() == null) {
			this.id = "";
		} else {
			this.id = k.getId().toString();
		}

		this.korisnickoIme = k.getKorisnickoIme();
		this.lozinka = k.getLozinka();
		this.ime = k.getIme();
		this.prezime = k.getPrezime();
		this.email = k.getEmail();
		this.lokacija = k.getLokacija();
		this.datumRod = df.format(k.getDatumRod());

	}

	@Override
	public void popuniUZapis(Korisnik k) {
		if (this.id.isEmpty()) {
			k.setId(null);
		} else {
			k.setId(Long.valueOf(this.id));
		}

		k.setKorisnickoIme(this.korisnickoIme);
		k.setLozinka(this.lozinka);
		k.setIme(this.ime);
		k.setPrezime(this.prezime);
		k.setEmail(this.email);
		k.setLokacija(this.lokacija);
		k.changeAdminStatus();
		try {
			k.setDatumRod(df.parse(this.datumRod));
		} catch (ParseException e) {
			k.setDatumRod(new Date());
		}
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

		validirajKorisnickoIme();

		validirajLozinku();

		validirajIme();

		validirajPrezime();

		validirajEmail();

		validirajLokaciju();

		validirajDatumRod();
	}

	private void validirajKorisnickoIme() {
		if (this.korisnickoIme.isEmpty()) {
			greske.put("korisnickoIme", "Korisničko ime je obavezno!");
			return;
		}
		
		if (this.korisnickoIme.length() > Korisnik.duljinaKorImena) {
			greske.put("korisnickoIme", "Korisničko ime je dulje od "
								+ Korisnik.duljinaKorImena + " znakova!");
			return;
		}

		Korisnik korisnik = DAOProvider.getDAO().getKorisnik(this.korisnickoIme);
		if (korisnik != null) {
			greske.put("korisnickoIme", "Odabrano korisničko ime već postoji!");
			return;
		}
	}

	private void validirajLozinku() {
		if (this.lozinka.isEmpty()) {
			greske.put("lozinka", "Lozinka je obavezna!");
			return;
		}
		
		if (this.lozinka.length() > Korisnik.duljinaLozinke) {
			greske.put("lozinka", "Lozinka je dulja od "
								+ Korisnik.duljinaLozinke + " znakova!");
			return;
		}
	}

	private void validirajIme() {
		if (this.ime.isEmpty()) {
			greske.put("ime", "Ime je obavezno!");
			return;
		}
		
		if (this.ime.length() > Korisnik.duljinaImena) {
			greske.put("ime", "Ime je dulje od "
					+ Korisnik.duljinaImena + " znakova!");
		}

		jeLjudskoIme("ime", this.ime);

	}

	private void validirajPrezime() {
		if (this.prezime.isEmpty()) {
			greske.put("prezime", "Prezime je obavezno!");
			return;
		}
		
		if (this.prezime.length() > Korisnik.duljinaPrezimena) {
			greske.put("prezime", "Prezime je dulje od "
					+ Korisnik.duljinaPrezimena + " znakova!");
		}

		jeLjudskoIme("prezime", this.prezime);
	}

	private void validirajEmail() {
		if (this.email.isEmpty()) {
			greske.put("email", "Email adresa je obavezna!");
			return;
		}
		
		if (this.email.length() > Korisnik.duljinaEmaila) {
			greske.put("email", "Email je dulji od "
					+ Korisnik.duljinaEmaila + " znakova!");
		}

		// validiraj format
		String email = this.email;
		Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
		Matcher mat = pattern.matcher(email);
		if (mat.matches()) {
			// valid email address format
		} else {
			// invalid email address format
			greske.put("email", "Email adresa je neispravnog formata!");
			return;
		}

		// TODO validiraj email

		// email ne smije biti već korišten
		List<Korisnik> korisnici = DAOProvider.getDAO().getSviKorisnici();
		boolean postoji = false;
		for (Korisnik k : korisnici)
			if (k.getEmail().equals(this.email)) {
				postoji = true;
				break;
			}
		if (postoji) {
			greske.put("email", "Email je već u upotrebi!");
			return;
		}

	}

	private void validirajLokaciju() {
		if (this.lokacija.isEmpty()) {
			greske.put("lokacija", "Lokacija je obavezna!");
			return;
		}
		
		if (this.lokacija.length() > Korisnik.duljinaLokacije) {
			greske.put("lokacija", "Lokacija je dulja od "
					+ Korisnik.duljinaLokacije + " znakova!");
		}
	}

	
	private void validirajDatumRod() {
		if (this.datumRod.isEmpty()) {
			greske.put("datumRod", "Datum rođenja je obavezan!");
			return;
		}

		
		if (!this.datumRod.matches("([0-9]{2}) ([0-9]{2}) ([0-9]{4})")) {
			greske.put("datumRod", "Datum rođenja mora biti formata dd MM yyyy!");
			return;
		}

		Date date = new Date();
		try {
			df.setLenient(false);
			date = df.parse(this.datumRod);
		} catch (ParseException ex) {
			greske.put("datumRod", "Neispravan datum!");
			return;
		}
		Calendar cal = Calendar.getInstance();
		
		if (!date.before(cal.getTime())){
			greske.put("datumRod", "Datum rođenja ne može biti u budućnosti!");
			return;
		}
		
	}

	private void jeLjudskoIme(String sender, String naziv) {
	    
		
		Locale.setDefault(new Locale("hr", "HR"));
		
	    if (!naziv.matches("[a-žA-Ž]+")) {
			greske.put(sender, sender.substring(0, 1).toUpperCase() + sender.substring(1)
					+ " nije ispravno!");
			return;
		}
		if (checkForMoreThenDuplicatesInAName(naziv) == true) {
			greske.put(sender, sender.substring(0, 1).toUpperCase() + sender.substring(1)
					+ " ne može imati 3 ili više istih slova zaredom!");
			return;
		}
	}

	private boolean checkForMoreThenDuplicatesInAName(String naziv) {
		int i = 0;
		char current;
		char last = 0;
		boolean almostBreaking = false;
		while (i < naziv.length()) {
			current = naziv.toLowerCase().charAt(i);
			if (current == last) {
				if (almostBreaking == true)
					return true;
				else
					almostBreaking = true;
			} else {
				almostBreaking = false;
			}
			last = current;
			i++;
		}
		return false;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
		this.lozinka = lozinka;
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

	public String getDatumRod() {
		return datumRod;
	}

	public void setDatumRod(String datumRod) {
		this.datumRod = datumRod;
	}

	public DateFormat getDf() {
		return df;
	}

	public void setDf(SimpleDateFormat df) {
		this.df = df;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Boolean getIsBanned() {
		return isBanned;
	}

	public void setIsBanned(Boolean isBanned) {
		this.isBanned = isBanned;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

}
