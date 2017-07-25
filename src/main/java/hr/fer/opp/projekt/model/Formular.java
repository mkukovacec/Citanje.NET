package hr.fer.opp.projekt.model;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public abstract class Formular<T> {
	
	protected Map<String, String> greske = new HashMap<>();
	
	public abstract void popuniIzZahtjeva(HttpServletRequest req);
	
	public abstract void popuniIzZapisa(T t);
	
	public abstract void popuniUZapis(T t);
	
	public abstract void validiraj();
	
	public String dohvatiPogresku(String ime) {
		return greske.get(ime);
	}
	
	public boolean imaPogresaka() {
		return !greske.isEmpty();
	}
	
	public boolean imaPogresku(String ime) {
		return greske.containsKey(ime);
	}
	
	protected String pripremi(String s) {
		if (s==null) return "";
		return s.trim();
	}
	
	public Map<String, String> getGreske() {
		return greske;
	}

	public void setGreske(Map<String, String> greske) {
		this.greske = greske;
	}

}
