package com.app.web.util;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.springframework.stereotype.Service;

@Service
public class Sha2 {
	
	public static String getSHA512(String input) {
		
		String toReturn = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			digest.reset();
			digest.update(input.getBytes("utf8"));
			toReturn = String.format("%0128x", new BigInteger(1, digest.digest()));
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return toReturn;
	}
	

}
