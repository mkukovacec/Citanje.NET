package hr.fer.opp.projekt.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.opp.projekt.dao.DAOProvider;
import hr.fer.opp.projekt.model.Korisnik;
import hr.fer.opp.projekt.model.KorisnikFormular;

@WebServlet("/registracija")
public class RegistracijaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// ako je korisnik već ulogiran ne smije se moći registrirati
		if (!(req.getSession() == null || req.getSession().getAttribute("current.user") == null)) {
			req.getRequestDispatcher("/WEB-INF/pages/forbidden.jsp").forward(req, resp);
			return;
		}
		
		KorisnikFormular kf = new KorisnikFormular();
		req.setAttribute("zapis", kf);
		req.getRequestDispatcher("/WEB-INF/pages/KorisnikFormular.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		obradi(req, resp);
	}
	
	protected void obradi(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		KorisnikFormular kf = new KorisnikFormular();
		kf.popuniIzZahtjeva(req);
		kf.validiraj();
		if (kf.imaPogresaka()) {
			req.setAttribute("zapis", kf);
			req.getRequestDispatcher("/WEB-INF/pages/KorisnikFormular.jsp").forward(req, resp);
			return;
		}
		
		Korisnik k = new Korisnik();
		kf.popuniUZapis(k);
		
		DAOProvider.getDAO().addKorisnik(k);
		
		resp.sendRedirect("/Citanje.NET");
	}

}
