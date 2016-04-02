package com.sdi.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CifradoMD5 {

	private static String toHexadecimal(byte[] digest) {
		
		String hash = "";
		for (byte aux : digest) {
			int b = aux & 0xff;
			if (Integer.toHexString(b).length() == 1)
				hash += "0";
			hash += Integer.toHexString(b);
		}
		return hash;
	}

	public static String getStringMessageDigest(String message) {
		
		byte[] digest = null;
		byte[] buffer = message.getBytes();
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(buffer);
			digest = messageDigest.digest();
		} catch (NoSuchAlgorithmException ex) {
			System.out.println("Error creando digest");
		}
		return toHexadecimal(digest);
	}
}
