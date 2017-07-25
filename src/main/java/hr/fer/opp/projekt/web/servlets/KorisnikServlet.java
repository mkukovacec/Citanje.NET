package hr.fer.opp.projekt.web.servlets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import hr.fer.opp.projekt.dao.DAOProvider;
import hr.fer.opp.projekt.model.Biljeska;
import hr.fer.opp.projekt.model.Komentar;
import hr.fer.opp.projekt.model.Korisnik;
import hr.fer.opp.projekt.model.KorisnikFormular;
import hr.fer.opp.projekt.model.Opomena;

@WebServlet({ "/korisnik", "/korisnik/*" })
@MultipartConfig
public class KorisnikServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private void reportError(String message, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setAttribute("errorTitle", "Nevaljani zahtjev za korisnikom");
		req.setAttribute("errorMessage", message);
		req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		pronadiKorisnika(req, resp, true);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		pronadiKorisnika(req, resp, false);
	}

	private void pronadiKorisnika(HttpServletRequest req, HttpServletResponse resp, boolean isGET)
			throws ServletException, IOException {
		String path = req.getPathInfo();
		if (path != null) {
			req.setAttribute("uredi", false);
			path.substring(1);
			String[] dijelovi = path.split("/");

			String nick = dijelovi[1];
			Korisnik k = DAOProvider.getDAO().getKorisnik(nick);
			if (k != null) {
				req.setAttribute("korisnik", k);
				KorisnikFormular kf = new KorisnikFormular();
				kf.popuniIzZapisa(k);
				kf.setLozinka("");
				req.setAttribute("zapis", kf);
				if (dijelovi.length == 3 && dijelovi[2].equals("komentari")) {
					req.getRequestDispatcher("/WEB-INF/pages/komentariKorisnika.jsp").forward(req, resp);
				} else if (dijelovi.length == 5 && dijelovi[2].equals("komentari") && dijelovi[4].equals("brisi")) {
					try {
						Long id = Long.parseLong(dijelovi[3]);
						Komentar komentar = DAOProvider.getDAO().getKomentar(id);
						if (komentar == null) {
							reportError("Ne postoji komentar s id-om " + id.toString() + ".", req, resp);
						} else {
							DAOProvider.getDAO().removeKomentar(komentar.getId());
							resp.sendRedirect("/Citanje.NET/korisnik/" + k.getKorisnickoIme() + "/komentari");
						}
					} catch (NumberFormatException e) {
						reportError("Ne postoji staza do zadanog komentara", req, resp);
					}
				} else if (dijelovi.length == 5 && dijelovi[2].equals("komentari") && dijelovi[4].equals("uredi")) {
					try {
						Long id = Long.parseLong(dijelovi[3]);
						Komentar komentar = DAOProvider.getDAO().getKomentar(id);
						if (komentar == null) {
							reportError("Ne postoji komentar s id-om " + id.toString() + ".", req, resp);
						} else {
							req.setAttribute("zaUredivanje", komentar);
							if (isGET) {
								req.getRequestDispatcher("/WEB-INF/pages/komentariKorisnika.jsp").forward(req, resp);
							} else {
								urediKomentar(req, resp, komentar);
							}
						}
					} catch (NumberFormatException e) {
						reportError("Ne postoji staza do zadanog komentara", req, resp);
					}
				} else if (dijelovi.length == 3 && dijelovi[2].equals("biljeske")) {
					req.getRequestDispatcher("/WEB-INF/pages/biljeskeKorisnika.jsp").forward(req, resp);
				} else if (dijelovi.length == 5 && dijelovi[2].equals("biljeske") && dijelovi[4].equals("brisi")) {
					try {
						Long id = Long.parseLong(dijelovi[3]);
						Biljeska biljeska = DAOProvider.getDAO().getBiljeska(id);
						if (biljeska == null) {
							reportError("Ne postoji bilješka s id-om " + id.toString() + ".", req, resp);
						} else {
							DAOProvider.getDAO().removeBiljeska(biljeska.getId());
							resp.sendRedirect("/Citanje.NET/korisnik/" + k.getKorisnickoIme() + "/biljeske");
						}
					} catch (NumberFormatException e) {
						reportError("Ne postoji staza do zadane bilješke.", req, resp);
					}
				} else if (dijelovi.length == 5 && dijelovi[2].equals("biljeske") && dijelovi[4].equals("uredi")) {
					try {
						Long id = Long.parseLong(dijelovi[3]);
						Biljeska biljeska = DAOProvider.getDAO().getBiljeska(id);
						if (biljeska == null) {
							reportError("Ne postoji bilješka s id-om " + id.toString() + ".", req, resp);
						} else {
							req.setAttribute("zaUredivanje", biljeska);
							if (isGET) {
								req.getRequestDispatcher("/WEB-INF/pages/biljeskeKorisnika.jsp").forward(req, resp);
							} else {
								urediBiljesku(req, resp, biljeska);
							}
						}
					} catch (NumberFormatException e) {
						reportError("Ne postoji staza do zadane bilješke.", req, resp);
					}
				} else if (dijelovi.length == 5 && dijelovi[2].equals("komentari") && dijelovi[4].equals("uredi")) {
					try {
						Long id = Long.parseLong(dijelovi[3]);
						Komentar komentar = DAOProvider.getDAO().getKomentar(id);
						if (komentar == null) {
							reportError("Ne postoji komentar s id-om " + id.toString() + ".", req, resp);
						} else {
							req.setAttribute("uredi", true);
							if (isGET) {
								req.getRequestDispatcher("/WEB-INF/pages/komentariKorisnika.jsp").forward(req, resp);
							} else {
								urediKomentar(req, resp, komentar);
							}
						}
					} catch (NumberFormatException e) {
						reportError("Ne postoji staza do zadanog komentara", req, resp);
					}
				} else if (dijelovi.length == 3 && dijelovi[2].equals("uredi")) {
					req.setAttribute("uredi", true);
					if (isGET) {
						req.getRequestDispatcher("/WEB-INF/pages/korisnik.jsp").forward(req, resp);
					} else {
						uredivanjePodataka(req, resp, k);
					}
				} else if (dijelovi.length == 3 && dijelovi[2].equals("slika")) {
					obradiSliku(nick, req, resp);					
					req.getRequestDispatcher("/WEB-INF/pages/korisnik.jsp").forward(req, resp);
				} else if (dijelovi.length >= 3 && dijelovi[2].equals("opomene")) {
					HttpSession sesija = req.getSession();
					String prijavljeniNick = (String) sesija.getAttribute("current.user");

					if (prijavljeniNick.equals(nick)) {
						if (dijelovi.length == 4) {
							try {
								Long id = Long.parseLong(dijelovi[3]);
								Opomena op = DAOProvider.getDAO().getOpomena(id);
								if (op != null) {
									if (op.getOpomenutiKorisnik().getKorisnickoIme().equals(prijavljeniNick)) {
										op.setPregledana(true);
										DAOProvider.getDAO().addOpomena(op);
										req.setAttribute("opomena", op);
										req.getRequestDispatcher("/WEB-INF/pages/prikazOpomene.jsp").forward(req, resp);
									}
								}
								return;
							} catch (NumberFormatException nfe) {
							}

							reportError("Nevaljani zahtjev za opomenom.", req, resp);

						} else {
							req.setAttribute("opomene", k.getOpomene());
							req.getRequestDispatcher("/WEB-INF/pages/opomene.jsp").forward(req, resp);
						}
					}

					reportError("", req, resp);
				} else {
					req.getRequestDispatcher("/WEB-INF/pages/korisnik.jsp").forward(req, resp);
				}
			} else {
				reportError("Ne postoji registrirani korisnik " + nick + ".", req, resp);
			}

		} else {
			reportError("Nepostojeća staza od korisnika.", req, resp);
		}

	}

	private boolean obradiSliku(String user, HttpServletRequest req,
			HttpServletResponse resp) throws IOException, ServletException {
		
		String folder = req.getServletContext().getRealPath("/avatars");
		
		if (ServletFileUpload.isMultipartContent(req)) {
			try {
				List<FileItem> multiparts =
						new ServletFileUpload(
								new DiskFileItemFactory()
						).parseRequest(req);
				
				for (FileItem item : multiparts) {
					if (!item.isFormField()) {
						String name = new File(item.getName()).getName();
						if (!name.isEmpty()) {
							BufferedImage bim = ImageIO.read(item.getInputStream());
							if (bim == null) return false;
							
							FileOutputStream os = new FileOutputStream(
														folder + File.separator
														+ user + ".png"
													);
							
							boolean result = ImageIO.write(bim, "png", os);
							
							os.close();
							return result;
						}
					}
				}
			} catch (Exception e) {
				
			}
		}
		
		return false;
		
	}

	private void urediBiljesku(HttpServletRequest req, HttpServletResponse resp,
			Biljeska biljeska) throws ServletException, IOException {
		
		String tekst = req.getParameter("tekst");
		if (tekst.trim().isEmpty()) {
			req.setAttribute("message", "Tekst bilješke ne može biti prazan.");
			req.getRequestDispatcher("/WEB-INF/pages/biljeskeKorisnika.jsp").forward(req, resp);
			return;
		}
		biljeska.setTekst(tekst);
		Biljeska tmp = new Biljeska();
		tmp.setTekst("#");
		req.setAttribute("zaUredivanje", tmp);
		Korisnik k = (Korisnik) req.getAttribute("korisnik");
		resp.sendRedirect("/Citanje.NET/korisnik/" + k.getKorisnickoIme() + "/biljeske");

	}

	private void urediKomentar(HttpServletRequest req, HttpServletResponse resp, Komentar komentar)
			throws IOException, ServletException {
		String tekst = req.getParameter("tekst");
		if (tekst.trim().isEmpty()) {
			req.setAttribute("message", "Tekst komentara ne može biti prazan.");
			req.getRequestDispatcher("/WEB-INF/pages/komentariKorisnika.jsp").forward(req, resp);
			return;
		}
		komentar.setTekst(tekst);
		komentar.setDatumObjave(new Date());
		Komentar tmp = new Komentar();
		tmp.setTekst("#");
		req.setAttribute("zaUredivanje", tmp);
		Korisnik k = (Korisnik) req.getAttribute("korisnik");
		resp.sendRedirect("/Citanje.NET/korisnik/" + k.getKorisnickoIme() + "/komentari");

	}

	private void uredivanjePodataka(HttpServletRequest req, HttpServletResponse resp, Korisnik k)
			throws ServletException, IOException {
		String staraLozinka = k.getLozinka();
		boolean reset = false;
		KorisnikFormular zapis = new KorisnikFormular();
		zapis.popuniIzZahtjeva(req);
		
		zapis.validiraj();
		
		if (zapis.getKorisnickoIme().equals(k.getKorisnickoIme())) {
			zapis.getGreske().remove("korisnickoIme");
		}
		
		if (zapis.getEmail().equals(k.getEmail())) {
			zapis.getGreske().remove("email");
		}
		
		if (zapis.getLozinka().isEmpty()) {
			zapis.getGreske().remove("lozinka");
			reset = true;
		}
		
		if (zapis.imaPogresaka()) {
			req.setAttribute("zapis", zapis);
			req.getRequestDispatcher("/WEB-INF/pages/korisnik.jsp").forward(req, resp);
			return;
		}

		zapis.setId(k.getId().toString());
		zapis.popuniUZapis(k);
		if (reset) {
			k.setHashLozinka(staraLozinka);
		}
		
		k.changeAdminStatus();
		
		req.setAttribute("uredi", false);
		resp.sendRedirect("/Citanje.NET/korisnik/" + k.getKorisnickoIme());
	}

}
