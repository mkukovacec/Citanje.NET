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
import hr.fer.opp.projekt.model.Report;

@WebServlet({ "/report", "/report/*" })
public class ReportServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getPathInfo() == null) {
			req.getRequestDispatcher("/WEB-INF/pages/forbidden.jsp").forward(req, resp);
			return;
		}
		if (req.getPathInfo().substring(1).split("/").length > 1) {
			req.getRequestDispatcher("/WEB-INF/pages/forbidden.jsp").forward(req, resp);
			return;
		}
		HttpSession sesija = req.getSession();
		String current = (String) sesija.getAttribute("current.user");
		String s = req.getPathInfo().substring(1);
		Korisnik optuz = DAOProvider.getDAO().getKorisnik(s);

		if (current.equals(s) || optuz.getIsAdmin()) {
			req.getRequestDispatcher("/WEB-INF/pages/forbidden.jsp").forward(req, resp);
			return;
		}
		req.setAttribute("optuzeni", s);

		req.getRequestDispatcher("/WEB-INF/pages/reportFormular.jsp").forward(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String s = req.getParameter("optuzeni");
		Korisnik optuz = DAOProvider.getDAO().getKorisnik(s);
		String razlog = req.getParameter("razlog");
		Korisnik tuzitelj = DAOProvider.getDAO().getKorisnik((String) req.getSession().getAttribute("current.user"));

		Report rep = new Report();
		rep.setPrijavljeniKorisnik(optuz);
		rep.setTuzitelj(tuzitelj);
		rep.setRazlog(razlog);
		DAOProvider.getDAO().addReport(rep);
		resp.sendRedirect("/Citanje.NET/korisnik/" + optuz.getKorisnickoIme());
	}

}
