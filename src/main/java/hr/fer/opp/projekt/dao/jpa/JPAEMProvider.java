package hr.fer.opp.projekt.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import hr.fer.opp.projekt.dao.DAOException;

public class JPAEMProvider {

	private static ThreadLocal<LocalData> locals = new ThreadLocal<>();

	public static EntityManager getEntityManager() {
		LocalData ldata = locals.get();
		if (ldata == null) {
			ldata = new LocalData();
			ldata.em = JPAEMFProvider.getEmf().createEntityManager();
			ldata.em.getTransaction().begin();
			locals.set(ldata);
		}
		
		return ldata.em;
	}

	public static void close() throws DAOException {
		LocalData ldata = locals.get();
		if (ldata == null) {
			return;
		}
		DAOException dex = null;
		try {
			EntityTransaction transaction = ldata.em.getTransaction();
			if (transaction.isActive()) {
				if (!transaction.getRollbackOnly()) {
					transaction.commit();
				} else {
					transaction.rollback();
				}
			}
		} catch (Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		
		try {
			ldata.em.close();
		} catch (Exception ex) {
			if (dex != null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if (dex != null)
			throw dex;
	}

	private static class LocalData {
		EntityManager em;
	}

}
