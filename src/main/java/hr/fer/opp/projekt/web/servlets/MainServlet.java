package hr.fer.opp.projekt.web.servlets;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hr.fer.opp.projekt.dao.DAO;
import hr.fer.opp.projekt.dao.DAOProvider;
import hr.fer.opp.projekt.model.Djelo;
import hr.fer.opp.projekt.model.Korisnik;
import hr.fer.opp.projekt.model.Opomena;

@WebServlet("/")
public class MainServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	private static boolean imaAdmin = false;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		obradi(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		obradi(req, resp);
	}
	
	private void provjeriAdministratora() {
		if (!imaAdmin) {
			DAO dao = DAOProvider.getDAO();
			Korisnik kor = dao.getKorisnik("admin");
			if (kor == null) {
				Korisnik admin = new Korisnik();
				admin.setKorisnickoIme("admin");
				admin.setLozinka("0000");
				admin.setIme("Ime");
				admin.setPrezime("Prezime");
				admin.setDatumRod(new Date());
				admin.setAdminStatus(true);
				admin.setEmail("default@mail.com");
				admin.setLokacija("Adminbar");
				dao.addKorisnik(admin);
			}
			
			imaAdmin = true;
		}
	}
	
	private void obradi(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		provjeriAdministratora();
		
		HttpSession sesija = req.getSession();
		String current = (String) sesija.getAttribute("current.user");
		
		if (current != null) {
			int neprocitane = 0;
			Korisnik korisnik = DAOProvider.getDAO().getKorisnik(current);
			
			if (korisnik != null) {
				for (Opomena opomena : korisnik.getOpomene()) {
					if (!opomena.isPregledana()) {
						neprocitane++;
					}
				}
				
				req.setAttribute("neprocitaneOpomene", neprocitane);
			}
		}
		
		List<Djelo> djela = DAOProvider.getDAO().getSvaDjela();
		req.setAttribute("djela", djela);
		req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
	}

}
