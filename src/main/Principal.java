package main;

import javax.swing.JOptionPane;

public class Principal {
	public static void main(String[] args) throws Exception {
		String pass=JOptionPane.showInputDialog("Insira sua senha:");
		String message = JOptionPane.showInputDialog("Mensagem a encriptar:");
		AES alg = new AES(pass);
		byte[] enc = alg.encriptar(message);
		System.out.println(new String(alg.decriptar(enc)));
	}

}
