package hr.fer.opp.projekt.web.servlets;

import java.io.IOException;

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
import hr.fer.opp.projekt.model.Korisnik;

@WebServlet({ "/autor", "/autor/*" })
public class AutorServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private void reportError(String message, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setAttribute("errorTitle", "Nevaljani zahtjev za djelom");
		req.setAttribute("errorMessage", message);
		req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		pronadiAutora(req, resp, true);
		req.getRequestDispatcher("/WEB-INF/pages/autor.jsp").forward(req, resp);
	}

	private void pronadiAutora(HttpServletRequest req, HttpServletResponse resp,
			boolean isGET) throws ServletException, IOException {
		String path = req.getPathInfo();
		
		if (path == null) {
			// Citanje.NET/autor
			req.setAttribute("sviAutori", DAOProvider.getDAO().getSviAutori());
			req.getRequestDispatcher("/WEB-INF/pages/autori.jsp").forward(req, resp);
		} else {
			String[] dijelovi = path.split("/");

			try {
				Long id = Long.parseLong(dijelovi[1]);
				Autor a = DAOProvider.getDAO().getAutor(id);
				if (a != null) {
					req.setAttribute("autor", a);
					req.setAttribute("djelaAutora", a.getDjela());
					
					if (dijelovi.length == 3 && dijelovi[2].equals("uredi")) {
						if (isGET) {
							AutorFormular af = new AutorFormular();
							af.popuniIzZapisa(a);
							req.setAttribute("zapis", af);
						} else {
							req.setCharacterEncoding("UTF-8");
							
							AutorFormular af = new AutorFormular();
							af.popuniIzZahtjeva(req);
							af.validiraj();
							
							if (af.imaPogresaka()) {
								req.setAttribute("zapis", af);
							} else {
								HttpSession sesija = req.getSession();
								String prijavljeniNick = (String) sesija.getAttribute("current.user");
								
								if (prijavljeniNick != null) {
									DAO dao = DAOProvider.getDAO();
									
									Korisnik unositelj = dao.getKorisnik(prijavljeniNick);
									af.popuniUZapis(a);
									a.setZadnjiUredio(unositelj);
								
									dao.addAutor(a);
								}
								
								resp.sendRedirect("/Citanje.NET/autor/" + a.getId());
								return;
							}
						}
						req.getRequestDispatcher("/WEB-INF/pages/AutorFormular.jsp").forward(req, resp);
						return;
					} else {
						req.getRequestDispatcher("/WEB-INF/pages/autor.jsp").forward(req, resp);
					}
				} else {
					reportError("Ne postoji autor s identifikacijskom oznakom " + id, req, resp);
				}
			} catch (NumberFormatException e) {
				reportError("Ne postoji autor s takvom identifikacijskom oznakom", req, resp);
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		pronadiAutora(req, resp, false);
	}
}
