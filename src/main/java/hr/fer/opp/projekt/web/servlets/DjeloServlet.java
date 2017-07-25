package hr.fer.opp.projekt.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hr.fer.opp.projekt.dao.DAO;
import hr.fer.opp.projekt.dao.DAOProvider;
import hr.fer.opp.projekt.model.Autor;
import hr.fer.opp.projekt.model.AutorFormular;
import hr.fer.opp.projekt.model.Biljeska;
import hr.fer.opp.projekt.model.BiljeskaFormular;
import hr.fer.opp.projekt.model.Djelo;
import hr.fer.opp.projekt.model.DjeloFormular;
import hr.fer.opp.projekt.model.Komentar;
import hr.fer.opp.projekt.model.KomentarFormular;
import hr.fer.opp.projekt.model.Korisnik;

@WebServlet({ "/djelo", "/djelo/*" })
public class DjeloServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private void reportError(String message, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setAttribute("errorTitle", "Nevaljani zahtjev za djelom");
		req.setAttribute("errorMessage", message);
		req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Komentar komentar = new Komentar();
		KomentarFormular kf = new KomentarFormular();
		kf.popuniIzZapisa(komentar);
		req.setAttribute("noviKomentar", kf);
		Biljeska biljeska = new Biljeska();
		BiljeskaFormular bf = new BiljeskaFormular();
		bf.popuniIzZapisa(biljeska);
		req.setAttribute("novaBiljeska", bf);
		pronadiDjelo(req, resp, true);

	}

	private void pronadiDjelo(HttpServletRequest req, HttpServletResponse resp, boolean isGET)
			throws ServletException, IOException {
		String path = req.getPathInfo();
		if (path != null) {

			req.setAttribute("uredi", false);
			path.substring(1);
			String[] dijelovi = path.split("/");
			try {
				Long id = Long.parseLong(dijelovi[1]);
				Djelo d = DAOProvider.getDAO().getDjelo(id);

				if (d != null) {
					req.setAttribute("djelo", d);
					DjeloFormular df = new DjeloFormular();
					df.popuniIzZapisa(d);
					req.setAttribute("zapis", df);
					if (dijelovi.length == 3 && dijelovi[2].equals("komentari")) {
						req.setAttribute("zaUredivanje", Long.parseLong("-1"));
						req.getRequestDispatcher("/WEB-INF/pages/komentariDjela.jsp").forward(req, resp);
					} else if (dijelovi.length == 3 && dijelovi[2].equals("autor")) {
						
						
						AutorFormular af = new AutorFormular();
						af.popuniIzZahtjeva(req);
						af.validiraj();
						
						if (af.imaPogresaka()) {
							req.setAttribute("zapis", af);
							req.getRequestDispatcher("/WEB-INF/pages/noviAutor.jsp").forward(req, resp);
							return;
						} else {
							HttpSession sesija = req.getSession();
							String prijavljeniNick = (String) sesija.getAttribute("current.user");
							Autor a = DAOProvider.getDAO().getAutor(Long.parseLong(af.getId()));
							if (prijavljeniNick != null) {
								DAO dao = DAOProvider.getDAO();
								
								Korisnik unositelj = dao.getKorisnik(prijavljeniNick);
								af.popuniUZapis(a);
								a.setZadnjiUredio(unositelj);
								
								dao.addAutor(a);
							}
							
							resp.sendRedirect("/Citanje.NET");
							return;
						}
						
					}else if (dijelovi.length == 5 && dijelovi[2].equals("komentari") && dijelovi[4].equals("brisi")) {
						try {
							Long idk = Long.parseLong(dijelovi[3]);
							Komentar komentar = DAOProvider.getDAO().getKomentar(idk);
							if (komentar == null) {
								reportError("Ne postoji komentar s id-om " + idk.toString() + ".", req, resp);
							} else {
								DAOProvider.getDAO().removeKomentar(komentar.getId());
								resp.sendRedirect("/Citanje.NET/djelo/" + d.getId().toString() + "/komentari");
							}
						} catch (NumberFormatException e) {
							reportError("Ne postoji staza do zadanog komentara", req, resp);
						}
					} else if (dijelovi.length == 5 && dijelovi[2].equals("komentari") && dijelovi[4].equals("uredi")) {
						try {
							Long idk = Long.parseLong(dijelovi[3]);
							Komentar komentar = DAOProvider.getDAO().getKomentar(idk);
							if (komentar == null) {
								reportError("Ne postoji komentar s id-om " + idk.toString() + ".", req, resp);
							} else {
								req.setAttribute("zaUredivanje", idk);
								if (isGET) {
									req.getRequestDispatcher("/WEB-INF/pages/komentariDjela.jsp").forward(req, resp);
								} else {
									urediKomentar(req, resp, komentar);
								}
							}
						} catch (NumberFormatException e) {
							reportError("Ne postoji staza do zadanog komentara", req, resp);
						}
					} else if (dijelovi.length == 3 && dijelovi[2].equals("biljeske")) {
						req.setAttribute("zaUredivanje", Long.parseLong("-1"));
						req.getRequestDispatcher("/WEB-INF/pages/biljeskeDjela.jsp").forward(req, resp);
					} else if (dijelovi.length == 5 && dijelovi[2].equals("biljeske") && dijelovi[4].equals("brisi")) {
						try {
							Long idb = Long.parseLong(dijelovi[3]);
							Biljeska biljeska = DAOProvider.getDAO().getBiljeska(idb);
							if (biljeska == null) {
								reportError("Ne postoji bilješka s id-om " + idb.toString() + ".", req, resp);
							} else {
								d.getBiljeske().remove(biljeska);
								resp.sendRedirect("/Citanje.NET/djelo/" + d.getId().toString() + "/biljeske");
							}
						} catch (NumberFormatException e) {
							reportError("Ne postoji staza do zadane bilješke.", req, resp);
						}
					} else if (dijelovi.length == 5 && dijelovi[2].equals("biljeske") && dijelovi[4].equals("uredi")) {
						try {
							Long idb = Long.parseLong(dijelovi[3]);
							Biljeska biljeska = DAOProvider.getDAO().getBiljeska(idb);
							if (biljeska == null) {
								reportError("Ne postoji bilješka s id-om " + idb.toString() + ".", req, resp);
							} else {
								req.setAttribute("zaUredivanje", idb);
								if (isGET) {
									req.getRequestDispatcher("/WEB-INF/pages/biljeskeDjela.jsp").forward(req, resp);
								} else {
									urediBiljesku(req, resp, biljeska);
								}
							}
						} catch (NumberFormatException e) {
							reportError("Ne postoji staza do zadane bilješke.", req, resp);
						}
					} else if (dijelovi.length == 3 && dijelovi[2].equals("dodajKomentar")) {
						if (isGET) {
							req.getRequestDispatcher("/WEB-INF/pages/komentarFormular.jsp").forward(req, resp);
							return;
						}
						dodavanjeKomentara(req, resp);
					} else if (dijelovi.length == 3 && dijelovi[2].equals("dodajBiljesku")) {
						if (isGET) {
							req.getRequestDispatcher("/WEB-INF/pages/biljeskaFormular.jsp").forward(req, resp);
							return;
						}
						dodavanjeBiljeske(req, resp);
					} else if (dijelovi.length == 3 && dijelovi[2].equals("uredi")) {
						req.setAttribute("uredi", true);
						if (isGET) {
							req.getRequestDispatcher("/WEB-INF/pages/djelo.jsp").forward(req, resp);
						} else {
							uredivanjePodataka(req, resp, d);
						}
					} else {
						req.getRequestDispatcher("/WEB-INF/pages/djelo.jsp").forward(req, resp);
					}
				} else {
					reportError("Ne postoji djelo s identifikacijskom oznakom " + id, req, resp);
				}
			} catch (NumberFormatException e) {
				reportError("Ne postoji djelo s takvom identifikacijskom oznakom", req, resp);
			}

		} else {
			reportError("Nepostojeća staza od djela", req, resp);
		}

	}

	private void urediBiljesku(HttpServletRequest req, HttpServletResponse resp, Biljeska biljeska)
			throws ServletException, IOException {
		String tekst = req.getParameter("tekst");
		if (tekst.trim().isEmpty()) {
			req.setAttribute("message", "Tekst bilješke ne može biti prazan.");
			req.getRequestDispatcher("/WEB-INF/pages/biljeskeDjela.jsp").forward(req, resp);
			return;
		}
		
		biljeska.setTekst(tekst);
		DAOProvider.getDAO().addBiljeska(biljeska);
		req.setAttribute("zaUredivanje", biljeska.getId());
		resp.sendRedirect("/Citanje.NET/djelo/" + biljeska.getDjelo().getId().toString() + "/biljeske");

	}

	private void urediKomentar(HttpServletRequest req, HttpServletResponse resp, Komentar komentar)
			throws ServletException, IOException {
		String tekst = req.getParameter("tekst");
		if (tekst.trim().isEmpty()) {
			req.setAttribute("message", "Tekst komentara ne može biti prazan.");
			req.getRequestDispatcher("/WEB-INF/pages/komentariDjela.jsp").forward(req, resp);
			return;
		}
		
		komentar.setTekst(tekst);
		DAOProvider.getDAO().addKomentar(komentar);
		req.setAttribute("zaUredivanje", komentar.getId());
		resp.sendRedirect("/Citanje.NET/djelo/" + komentar.getDjelo().getId() + "/komentari");

	}

	private void uredivanjePodataka(HttpServletRequest req, HttpServletResponse resp, Djelo djelo)
			throws IOException, ServletException {
		DjeloFormular zapis = new DjeloFormular();
		zapis.popuniIzZahtjeva(req);
		zapis.validiraj();

		if (zapis.imaPogresaka()) {
			req.setAttribute("zapis", zapis);
			req.getRequestDispatcher("/WEB-INF/pages/djelo.jsp").forward(req, resp);
			return;
		}
		
		zapis.setId(djelo.getId().toString());
		// uređivanje autora?
		zapis.popuniUZapis(djelo);
		
		HttpSession sesija = req.getSession();
		String prijavljeniNick = (String) sesija.getAttribute("current.user");
		
		Korisnik kor = DAOProvider.getDAO().getKorisnik(prijavljeniNick);
		
		if (kor != null) {
			djelo.setKorisnik(kor);
			DAOProvider.getDAO().addDjelo(djelo);
		}

		req.setAttribute("uredi", false);
		resp.sendRedirect("/Citanje.NET/djelo/" + djelo.getId().toString());

	}

	private void dodavanjeBiljeske(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		BiljeskaFormular bf = new BiljeskaFormular();
		bf.popuniIzZahtjeva(req);
		bf.validiraj();
		if (bf.imaPogresaka()) {
			req.setAttribute("novaBiljeska", bf);
			req.getRequestDispatcher("/WEB-INF/pages/biljeskaFormular.jsp").forward(req, resp);
			return;
		}

		HttpSession sesija = req.getSession();
		String prijavljeniNick = (String) sesija.getAttribute("current.user");
		Djelo djelo = (Djelo) req.getAttribute("djelo");

		if (prijavljeniNick != null) {
			DAO dao = DAOProvider.getDAO();

			Korisnik autor = dao.getKorisnik(prijavljeniNick);
			Biljeska biljeska = new Biljeska();
			bf.popuniUZapis(biljeska);
			biljeska.setKorisnik(autor);

			biljeska.setDjelo(djelo);
			dao.addBiljeska(biljeska);
		}

		resp.sendRedirect("/Citanje.NET/djelo/" + djelo.getId().toString() + "/biljeske");

	}

	private void dodavanjeKomentara(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		KomentarFormular kf = new KomentarFormular();
		kf.popuniIzZahtjeva(req);
		kf.validiraj();
		if (kf.imaPogresaka()) {
			req.setAttribute("noviKomentar", kf);
			req.getRequestDispatcher("/WEB-INF/pages/komentarFormular.jsp").forward(req, resp);
			return;
		}

		HttpSession sesija = req.getSession();
		String prijavljeniNick = (String) sesija.getAttribute("current.user");
		Djelo djelo = (Djelo) req.getAttribute("djelo");

		if (prijavljeniNick != null) {
			DAO dao = DAOProvider.getDAO();

			Korisnik komentator = dao.getKorisnik(prijavljeniNick);
			Komentar komentar = new Komentar();
			kf.popuniUZapis(komentar);
			komentar.setKorisnik(komentator);
			komentar.setDatumObjave(new Date());

			komentar.setDjelo(djelo);
			dao.addKomentar(komentar);
		}

		resp.sendRedirect("/Citanje.NET/djelo/" + djelo.getId().toString() + "/komentari");

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		pronadiDjelo(req, resp, false);

	}
}
