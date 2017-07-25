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

@WebServlet("/prijava")
public class PrijavaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private void redo(String message, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("errorMessage", message);
		req.getRequestDispatcher("/WEB-INF/pages/prijava.jsp").forward(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		redo(null, req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nick = req.getParameter("nick");
		String passwd = req.getParameter("passwd");
		HttpSession sesija = req.getSession();

		if (nick.isEmpty() || passwd.isEmpty()) {
			redo("Korisničko ime i lozinka su obavezni.", req, resp);
		} else {
			Korisnik korisnik = DAOProvider.getDAO().getKorisnik(nick);
			
			if (korisnik == null) {
				redo("Korisničko ime ne postoji.", req, resp);
			} else {
				if (!korisnik.provjeriLozinku(passwd)) {
					redo("Lozinka nije valjana.", req, resp);
				} else {
					if (korisnik.getIsBanned()){
						req.getRequestDispatcher("/WEB-INF/pages/banned.jsp").forward(req, resp);
						return;
					}
					sesija.setAttribute("current.user", nick);
					sesija.setAttribute("current.passwd", passwd);
					sesija.setAttribute("current.admin", korisnik.getIsAdmin());
					resp.sendRedirect("/Citanje.NET");
				}
			}
		}
		
	}
}
