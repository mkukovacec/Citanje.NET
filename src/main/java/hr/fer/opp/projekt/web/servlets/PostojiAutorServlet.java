package hr.fer.opp.projekt.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import hr.fer.opp.projekt.dao.DAOProvider;
import hr.fer.opp.projekt.model.Autor;

@WebServlet({"/postojiAutor"})
public class PostojiAutorServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private String pripremi(String s) {
		return s == null ? "" : s.trim();
	}
	
	private void obradi(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		boolean imaAutor = false;
		
		String ime = pripremi(req.getParameter("ime"));
		String prezime = pripremi(req.getParameter("prezime"));
		
		if (!ime.isEmpty() && !prezime.isEmpty()) {
			Autor a = DAOProvider.getDAO().getAutor(ime, prezime);
			
			if (a != null) {
				imaAutor = true;
			}
		}
		
		resp.setContentType("application/json");
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		
		JsonObject json = new JsonObject();
		json.addProperty("imaAutor", imaAutor);
		
		out.print(json.toString());
	}
	
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
	
	
}
