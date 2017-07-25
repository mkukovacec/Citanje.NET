package hr.fer.opp.projekt.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.opp.projekt.dao.DAOProvider;
import hr.fer.opp.projekt.model.Djelo;

@WebServlet({ "/djela" })
public class DjelaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Djelo> djela = DAOProvider.getDAO().getSvaDjela();
		req.setAttribute("djela", djela);
		req.getRequestDispatcher("/WEB-INF/pages/djela.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Djelo> djela = DAOProvider.getDAO().getSvaDjela();
		req.setAttribute("djela", djela);
		req.getRequestDispatcher("/WEB-INF/pages/djela.jsp").forward(req, resp);
	}

}
