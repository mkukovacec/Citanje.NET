package hr.fer.opp.projekt.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hr.fer.opp.projekt.dao.DAOProvider;
import hr.fer.opp.projekt.model.Korisnik;
import hr.fer.opp.projekt.model.Opomena;
import hr.fer.opp.projekt.model.OpomenaFormular;

@WebServlet({ "/opomena", "/opomena/*" })
public class OpomenaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sesija = req.getSession();
		String current = (String) sesija.getAttribute("current.user");
		Boolean isAdmin = (boolean) sesija.getAttribute("current.admin");
		if (current == null || isAdmin == null || !isAdmin) {
			req.getRequestDispatcher("/WEB-INF/pages/forbidden.jsp").forward(req, resp);
			return;
		}
		OpomenaFormular of = new OpomenaFormular();
		if (req.getPathInfo() != null) {
			if (req.getPathInfo().length() > 0) {
				String num = req.getPathInfo().substring(1);
				long l = Long.parseLong(num);
				Korisnik k = DAOProvider.getDAO().getKorisnik(l);
				of.setKorisnik(k.getKorisnickoIme());
			}
		}
		req.setAttribute("zapis", of);
		req.getRequestDispatcher("/WEB-INF/pages/opomena.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sesija = req.getSession();
		String current = (String) sesija.getAttribute("current.user");
		Boolean isAdmin = (boolean) sesija.getAttribute("current.admin");
		if (current == null || isAdmin == null || !isAdmin) {
			req.getRequestDispatcher("/WEB-INF/pages/forbidden.jsp").forward(req, resp);
			return;
		}
		req.setCharacterEncoding("UTF-8");

		OpomenaFormular of = new OpomenaFormular();
		of.popuniIzZahtjeva(req);
		of.validiraj();

		if (of.imaPogresaka()) {
			req.setAttribute("zapis", of);
			req.getRequestDispatcher("/WEB-INF/pages/opomena.jsp").forward(req, resp);
			return;
		}

		Opomena o = new Opomena();
		of.popuniUZapis(o);

		DAOProvider.getDAO().addOpomena(o);
		
		resp.sendRedirect("/Citanje.NET/kontrolpanel/korisnici/pregled");
	}

}
