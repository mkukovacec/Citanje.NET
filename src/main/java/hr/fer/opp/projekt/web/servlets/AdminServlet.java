package hr.fer.opp.projekt.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hr.fer.opp.projekt.dao.DAOProvider;
import hr.fer.opp.projekt.model.Djelo;
import hr.fer.opp.projekt.model.Korisnik;
import hr.fer.opp.projekt.model.Report;

@WebServlet({ "/kontrolpanel", "/kontrolpanel/*" })
public class AdminServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sesija = req.getSession();

		String current = (String) sesija.getAttribute("current.user");
		Boolean isAdmin = (Boolean) sesija.getAttribute("current.admin");
		if (current == null || isAdmin == null || !isAdmin) {
			req.getRequestDispatcher("/WEB-INF/pages/forbidden.jsp").forward(req, resp);
			return;
		}
		if (req.getPathInfo() != null) {
			if (req.getPathInfo().length() > 0) {
				String[] pathInfo = req.getPathInfo().substring(1).split("/");
				if (pathInfo[0].equals("djela")) {
					manageDjela(req, resp, pathInfo);
				} else if (pathInfo[0].equals("korisnici")) {
					manageKorisnika(req, resp, pathInfo);
					return;
				} else if (pathInfo[0].equals("reporti")) {
					manageReporta(req, resp, pathInfo);
					return;
				} else if (pathInfo[0].equals("komentari")) {
					manageKomentara(req, resp, pathInfo);
					return;
				} else if (pathInfo[0].equals("biljeske")) {
					manageBiljeske(req, resp, pathInfo);
					return;
				} else if (pathInfo[0].length() > 0) {
					req.getRequestDispatcher("/WEB-INF/pages/forbidden.jsp").forward(req, resp);
					return;
				}
			}
		}
		req.getRequestDispatcher("/WEB-INF/pages/admin.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Korisnik> users = DAOProvider.getDAO().getSviKorisnici();
		HttpSession sesija = req.getSession();
		sesija.setAttribute("users", users);
		resp.sendRedirect("/Citanje.NET/kontrolpanel/korisnici/pregled");
	}

	private void manageDjela(HttpServletRequest req, HttpServletResponse resp, String[] pathInfo)
			throws IOException, ServletException {
		if (pathInfo.length > 3) {
			req.getRequestDispatcher("/WEB-INF/pages/forbidden.jsp").forward(req, resp);
			return;
		}
		if (!pathInfo[1].equals("brisanje")) {
			req.getRequestDispatcher("/WEB-INF/pages/forbidden.jsp").forward(req, resp);
		}
		HttpSession sesija = req.getSession();
		try {
			Long l = Long.parseLong(pathInfo[2]);
			DAOProvider.getDAO().removeDjelo(l);
		} catch (Exception e) {
			req.getRequestDispatcher("/WEB-INF/pages/forbidden.jsp").forward(req, resp);
			return;
		}
		List<Djelo> djela = DAOProvider.getDAO().getSvaDjela();
		sesija.setAttribute("djela", djela);
		req.getRequestDispatcher("/WEB-INF/pages/djela.jsp").forward(req, resp);
		return;
	}

	private void manageKorisnika(HttpServletRequest req, HttpServletResponse resp, String[] pathInfo)
			throws IOException, ServletException {

		if (pathInfo.length > 3) {
			req.getRequestDispatcher("/WEB-INF/pages/forbidden.jsp").forward(req, resp);
			return;
		}

		HttpSession sesija = req.getSession();
		if (pathInfo[1].equals("brisanje")) {
			try {
				Long l = Long.parseLong(pathInfo[2]);
				DAOProvider.getDAO().removeKorisnik(l);
			} catch (Exception e) {
				req.getRequestDispatcher("/WEB-INF/pages/forbidden.jsp").forward(req, resp);
				return;
			}
		} else if (pathInfo[1].equals("ban")) {
			try {
				Long l = Long.parseLong(pathInfo[2]);
				Korisnik k = DAOProvider.getDAO().getKorisnik(l);
				k.changeBannedStatus();
			} catch (Exception e) {
				req.getRequestDispatcher("/WEB-INF/pages/forbidden.jsp").forward(req, resp);
				return;
			}
		} else if (pathInfo[1].equals("admin")) {
			try {
				Long l = Long.parseLong(pathInfo[2]);
				Korisnik k = DAOProvider.getDAO().getKorisnik(l);
				k.changeAdminStatus();
			} catch (Exception e) {
				req.getRequestDispatcher("/WEB-INF/pages/forbidden.jsp").forward(req, resp);
				return;
			}
		} else if (pathInfo[1].equals("pregled")) {
			List<Korisnik> users = DAOProvider.getDAO().getSviKorisnici();
			sesija.setAttribute("users", users);
			req.getRequestDispatcher("/WEB-INF/pages/users.jsp").forward(req, resp);
			return;
		} else if (!pathInfo[1].equals("pregled")) {
			req.getRequestDispatcher("/WEB-INF/pages/forbidden.jsp").forward(req, resp);
			return;
		}
		resp.sendRedirect("/Citanje.NET/kontrolpanel/korisnici/pregled");
		return;
	}

	private void manageKomentara(HttpServletRequest req, HttpServletResponse resp, String[] pathInfo)
			throws IOException, ServletException {
		if (pathInfo.length > 2) {
			req.getRequestDispatcher("/WEB-INF/pages/forbidden.jsp").forward(req, resp);
			return;
		}

		try {
			Long l = Long.parseLong(pathInfo[1]);
			DAOProvider.getDAO().removeKomentar(l);
		} catch (Exception e) {
			req.getRequestDispatcher("/WEB-INF/pages/forbidden.jsp").forward(req, resp);
			return;
		}
		req.getRequestDispatcher("/WEB-INF/pages/admin.jsp").forward(req, resp);
	}

	private void manageBiljeske(HttpServletRequest req, HttpServletResponse resp, String[] pathInfo)
			throws IOException, ServletException {
		if (pathInfo.length > 2) {
			req.getRequestDispatcher("/WEB-INF/pages/forbidden.jsp").forward(req, resp);
			return;
		}

		try {
			Long l = Long.parseLong(pathInfo[1]);
			DAOProvider.getDAO().removeBiljeska(l);
		} catch (Exception e) {
			req.getRequestDispatcher("/WEB-INF/pages/forbidden.jsp").forward(req, resp);
			return;
		}
		req.getRequestDispatcher("/WEB-INF/pages/admin.jsp").forward(req, resp);
	}

	private void manageReporta(HttpServletRequest req, HttpServletResponse resp, String[] pathInfo)
			throws IOException, ServletException {
		if (pathInfo.length > 3) {
			req.getRequestDispatcher("/WEB-INF/pages/forbidden.jsp").forward(req, resp);
			return;
		}

		HttpSession sesija = req.getSession();
		if (pathInfo[1].equals("brisanje")) {
			try {
				Long l = Long.parseLong(pathInfo[2]);
				DAOProvider.getDAO().removeReport(l);
			} catch (Exception e) {
				req.getRequestDispatcher("/WEB-INF/pages/forbidden.jsp").forward(req, resp);
				return;
			}
		} else if (pathInfo[1].equals("ban")) {
			try {
				Long l = Long.parseLong(pathInfo[2]);
				Korisnik k = DAOProvider.getDAO().getKorisnik(l);
				k.changeBannedStatus();
			} catch (Exception e) {
				req.getRequestDispatcher("/WEB-INF/pages/forbidden.jsp").forward(req, resp);
				return;
			}
		} else if (pathInfo[1].equals("pregled")) {
			List<Report> reporti = DAOProvider.getDAO().getReports();
			sesija.setAttribute("reports", reporti);
			req.getRequestDispatcher("/WEB-INF/pages/reports.jsp").forward(req, resp);
			return;
		} else if (!pathInfo[1].equals("pregled")) {
			req.getRequestDispatcher("/WEB-INF/pages/forbidden.jsp").forward(req, resp);
			return;
		}
		resp.sendRedirect("/Citanje.NET/kontrolpanel/reporti/pregled");
		return;

	}
}
