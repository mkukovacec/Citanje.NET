package hr.fer.opp.projekt.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.opp.projekt.dao.DAOProvider;
import hr.fer.opp.projekt.model.Zanr;

@WebServlet({ "/zanr", "/zanr/*" })
public class ZanrServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private void reportError(String message, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setAttribute("errorTitle", "Nevaljani zahtjev za žanrom");
		req.setAttribute("errorMessage", message);
		req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = req.getPathInfo();
		if (path == null) {
			// case:
			// http://localhost:8080/Citanje.NET/zanr
			req.setAttribute("sviZanrovi", DAOProvider.getDAO().getSveZanrove());
			req.getRequestDispatcher("/WEB-INF/pages/zanrovi.jsp").forward(req, resp);
		} else {
			String[] dijelovi = path.split("/");
			// case:
			// http://localhost:8080/Citanje.NET/zanr/*
			if (dijelovi.length == 2) {
				// case:
				// http://localhost:8080/Citanje.NET/zanr/{byId}
				try {
					Long id = Long.parseLong(dijelovi[1]);
					Zanr zanr = DAOProvider.getDAO().getZanr(id);
					if (zanr == null) {
						reportError("Ne postoji žanr s identifikacijskom oznakom " + id, req, resp);
					} else {
						req.setAttribute("zanr", zanr);
						req.setAttribute("djelaZanra", zanr.getDjela());
						req.getRequestDispatcher("/WEB-INF/pages/zanr.jsp").forward(req, resp);
					}
				} catch (NumberFormatException e) {
					// case:
					// http://localhost:8080/Citanje.NET/zanr/tragedija
					String imeZanra = dijelovi[1];
					List<Zanr> sviZanrovi = DAOProvider.getDAO().getSveZanrove();
					for(Zanr z : sviZanrovi) {
						if (z.getNaziv().toLowerCase().equals(imeZanra.toLowerCase())) {
							req.setAttribute("zanr", z);
							req.setAttribute("djelaZanra", z.getDjela());
							req.getRequestDispatcher("/WEB-INF/pages/zanr.jsp").forward(req, resp);
						}
					}
					
					// case:
					// ne postoji
					reportError("Ne postoji žanr s takvom identifikacijskom oznakom", req, resp);
				}
			} else {
				// case:
				// http://localhost:8080/Citanje.NET/zanr/trag/ed//ij/a
				reportError("Ne postoji žanr s takvom identifikacijskom oznakom", req, resp);
			}
		}
	}
}