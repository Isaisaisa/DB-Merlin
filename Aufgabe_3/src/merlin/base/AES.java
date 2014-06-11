package merlin.base;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

import javax.crypto.Cipher;

public class AES {
	static String IV = "AAAAAAAAAAAAAAAA";
	private static String encKey = "Th. Thiel-Clemen";

	public static byte[] encrypt(String plainText, String encryptionKey) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
		SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
		cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
		
		// Plaintext auffüllen, bis die Stringlänge ein Vielfaches von 16 darstellt.
		int len = 16 - plainText.length() % 16;
		for (int i = 0; i < len; i++) { plainText += "\0"; }
		
		return cipher.doFinal(plainText.getBytes("UTF-8"));
	}
	
	public static byte[] encrypt(String plainText) throws Exception {
		return encrypt(plainText, encKey);
	}

	public static String decrypt(byte[] cipherText, String encryptionKey) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
		SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
		cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
		
		// trim führt evtl. zu fehlern
		return (new String(cipher.doFinal(cipherText), "UTF-8")).trim();
//		return (new String(cipher.doFinal(cipherText), "UTF-8"));
	}
	
	public static String decrypt(byte[] cipherText) throws Exception {
		return decrypt(cipherText, encKey);
	}
}