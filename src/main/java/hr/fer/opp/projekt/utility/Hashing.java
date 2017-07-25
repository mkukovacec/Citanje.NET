package hr.fer.opp.projekt.utility;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Hashing {
	
	public static final String HASH_ALGORITAM = "SHA-1";
	
	public static String izracunajHash(String data) {
		return hash(data.getBytes(StandardCharsets.UTF_8));
	}
	
	public static String hash(byte[] data) {
		try {
			MessageDigest md = MessageDigest.getInstance(HASH_ALGORITAM);
			byte[] hashed = md.digest(data);
			return byteToHex(hashed);
		} catch (Exception e) {
			return new String(data, StandardCharsets.UTF_8);
		}
	}
	
	public static String byteToHex(byte[] data) {
		if (data==null) {
			throw new IllegalArgumentException(
					"Null polje ne može biti pretvoreno u niz znakova!"
			);
		}
		
		StringBuffer hex = new StringBuffer();
		
		for (int i = 0, n = data.length; i < n; i++) {
			String byteHex = Integer.toHexString(0xFF & data[i]);
			
			if (byteHex.length() == 1) {
				hex.append('0');
			}
			
			hex.append(byteHex);
		}
		
		return hex.toString();
	}

}
