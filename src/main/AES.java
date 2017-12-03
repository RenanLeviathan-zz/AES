package main;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class AES {

	private byte[] genkey;
	private byte[] iv;
	private SecretKey key;
	private Cipher cipher;
	public AES(String pw) throws Exception{
		SecureRandom srnd = new SecureRandom();
		byte [] sal = new byte[128];
		srnd.nextBytes(sal);
		// Instancia o cifrador
		cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		//PBKDF2
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		PBEKeySpec spec = new PBEKeySpec(pw.toCharArray(), sal, 1024, 128);
		SecretKey tmp = skf.generateSecret( spec );
		key = new SecretKeySpec(tmp.getEncoded(), "AES");
		genkey = key.getEncoded();
		iv = gerarVetorIV();
	}
	public byte [] gerarVetorIV() {
		SecureRandom rnd = new SecureRandom();
		byte [] vetiv = new byte [16];
		rnd.nextBytes( vetiv );
		return (vetiv);
	}
	public String asHex (byte buf[]) {
		StringBuffer strbuf = new StringBuffer(buf.length * 2);
		int i;

		for (i = 0; i < buf.length; i++) {
			if (((int) buf[i] & 0xff) < 0x10)
				strbuf.append("0");

			strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
		}

		return strbuf.toString();
	}
	
	public byte[] encriptar(String message) throws Exception{
		cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
		byte[] encrypted = cipher.doFinal(message.getBytes());
		return encrypted;
	}
	
	public byte[] decriptar(byte[] enc) throws Exception{
		cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
		byte[] original = cipher.doFinal(enc);
		return original;
	}
}