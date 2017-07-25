package hr.fer.opp.projekt.dao;

import hr.fer.opp.projekt.dao.jpa.JPADAOImpl;

public class DAOProvider {

	private static DAO dao = new JPADAOImpl();
	
	public static DAO getDAO() {
		return dao;
	}
	
}
