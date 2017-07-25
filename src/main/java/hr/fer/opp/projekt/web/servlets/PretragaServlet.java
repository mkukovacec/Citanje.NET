package hr.fer.opp.projekt.web.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.opp.projekt.dao.DAO;
import hr.fer.opp.projekt.dao.DAOProvider;
import hr.fer.opp.projekt.model.Autor;
import hr.fer.opp.projekt.model.Djelo;
import hr.fer.opp.projekt.model.Zanr;

@WebServlet("/pretraga")
public class PretragaServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private String pripremi (HttpServletRequest req, String parametar) {
		String param = req.getParameter(parametar);
		return param == null ? "" : param.trim();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("rezPretrage", new ArrayList<>());
		req.setAttribute("prviUlaz", true);
		
		List<Zanr> zanrovi = DAOProvider.getDAO().getSveZanrove();
		Zanr zanr = new Zanr();
		zanr.setNaziv("");
		zanrovi.add(0, zanr);
		req.setAttribute("zanrovi", zanrovi);
		req.getRequestDispatcher("/WEB-INF/pages/traziDjelo.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nazivDjela = pripremi(req, "nazivDjela").toLowerCase();
		String prezimeAutora = pripremi(req, "prezimeAutora").toLowerCase();
		String zanrDjela = pripremi(req, "zanrDjela");

		List<Djelo> rezPretrage = new ArrayList<>();
		DAO dao = DAOProvider.getDAO();
		
		if (zanrDjela.isEmpty() && prezimeAutora.isEmpty()) {
			rezPretrage = dao.getDjeloLike(nazivDjela);
		} else if (!zanrDjela.isEmpty()) {
			List<Djelo> poZanru = dao.getZanr(zanrDjela).getDjela();
			
			for (Djelo djelo : poZanru) {
				String naslovDjela = djelo.getNaslov().toLowerCase();
				String prezAutDjela = djelo.getAutor().getPrezimeAutora()
										.toLowerCase();
				
				if (naslovDjela.startsWith(nazivDjela) &&
						prezAutDjela.startsWith(prezimeAutora)) {
					rezPretrage.add(djelo);
				}
			}
		} else if (!prezimeAutora.isEmpty()) {
			List<Autor> autori = dao.getAutorLike(prezimeAutora);
			
			for (Autor autor : autori) {
				for (Djelo djelo : autor.getDjela()) {
					String naslovDjela = djelo.getNaslov().toLowerCase();
					
					if (naslovDjela.startsWith(nazivDjela)) {
						rezPretrage.add(djelo);
					}
				}
			}
		}
		
		req.setAttribute("rezPretrage", rezPretrage);
		req.setAttribute("prviUlaz", false);
		
		List<Zanr> zanrovi = DAOProvider.getDAO().getSveZanrove();
		Zanr zanr = new Zanr();
		zanr.setNaziv("");
		zanrovi.add(0, zanr);
		req.setAttribute("zanrovi", zanrovi);
		req.getRequestDispatcher("/WEB-INF/pages/traziDjelo.jsp").forward(req, resp);

	}
}
