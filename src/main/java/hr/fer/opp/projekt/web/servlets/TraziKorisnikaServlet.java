package hr.fer.opp.projekt.web.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.opp.projekt.dao.DAOProvider;
import hr.fer.opp.projekt.model.Korisnik;

@WebServlet("/pretragaKorisnika")
public class TraziKorisnikaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("rezPretrage", new ArrayList<>());
		req.setAttribute("prviUlaz", true);
		req.getRequestDispatcher("/WEB-INF/pages/traziKorisnika.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("prviUlaz", false);
		String unos = req.getParameter("unos");
		List<Korisnik> rezPretrage = new ArrayList<>();
		List<Korisnik> sviRegistrirani = DAOProvider.getDAO().getSviKorisnici();
		for (Korisnik korisnik : sviRegistrirani) {
			if (korisnik.getKorisnickoIme().toLowerCase().contains(unos.toLowerCase())) {
				rezPretrage.add(korisnik);
			}
		}
		req.setAttribute("rezPretrage", rezPretrage);
		req.getRequestDispatcher("/WEB-INF/pages/traziKorisnika.jsp").forward(req, resp);
	}
}
