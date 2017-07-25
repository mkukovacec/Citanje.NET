package hr.fer.opp.projekt.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.opp.projekt.model.Djelo;
import hr.fer.opp.projekt.model.DjeloFormular;

@WebServlet("/dodajDjelo")
public class DodajDjelo extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (req.getSession() == null || req.getSession().getAttribute("current.user") == null) {
			req.getRequestDispatcher("/WEB-INF/pages/forbidden.jsp").forward(req, resp);
			return;
		}

		Djelo d = new Djelo();
		DjeloFormular df = new DjeloFormular();
		df.popuniIzZapisa(d);

		req.setAttribute("zapis", df);

		req.getRequestDispatcher("/WEB-INF/pages/DjeloFormular.jsp").forward(req, resp);
	}

}
