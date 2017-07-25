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
import hr.fer.opp.projekt.model.Djelo;
import hr.fer.opp.projekt.model.DjeloFormular;
import hr.fer.opp.projekt.model.Korisnik;

@WebServlet("/spremiDjelo")
public class SpremiDjelo extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (req.getSession()!=null && req.getSession().getAttribute("current.user")!=null){
			obradi(req, resp);
		}else{
			req.getRequestDispatcher("/WEB-INF/pages/forbidden.jsp").forward(req, resp);
			return;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		obradi(req, resp);
	}
	
	protected void obradi(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		DjeloFormular df = new DjeloFormular();
		df.popuniIzZahtjeva(req);
		df.validiraj();
		
		if (df.imaPogresaka()) {
			req.setAttribute("zapis", df);
			req.getRequestDispatcher("/WEB-INF/pages/DjeloFormular.jsp").forward(req, resp);
			return;
		}
		
		HttpSession sesija = req.getSession();
		String prijavljeniNick = (String) sesija.getAttribute("current.user");
		
		if (prijavljeniNick != null) {
			DAO dao = DAOProvider.getDAO();
			
			Korisnik unositeljDjela = dao.getKorisnik(prijavljeniNick);
			
			if (unositeljDjela != null) {
				Djelo d = new Djelo();
				d.setKorisnik(unositeljDjela);
				d.setUnio(unositeljDjela);
				df.popuniUZapis(d);
				dao.addDjelo(d);
			}
		}
		
		resp.sendRedirect("/Citanje.NET");
	}

}
