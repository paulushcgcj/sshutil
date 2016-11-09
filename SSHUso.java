package org.paulushc.ssh;

import java.util.Arrays;

public class SSHUso {

	private static String usuario = "user";
	private static String senha = "password";
	private static String host = "127.0.0.1";
	private static int porta = 22;
	
	public static void main(String[] args){		
				
		System.out.println("Arquivo bolodefuba.doc existe? "+SSHUtils.existFile(usuario,senha,host,porta,"/root/bolodefuba.doc"));
		
		System.out.println("Enviei arquivo Lista.xls? " + SSHUtils.uploadTo(usuario, senha, host, porta,"/home/desenv/lista.xls", "/home/desenv/", "C:\\Pessoal\\GoogleDrive\\List_2_.xls") );
		
		System.out.println("Enviei multiplos arquivos? " + SSHUtils.uploadTo(usuario, senha, host, porta,"/home/desenv/", Arrays.asList("C:\\Pessoal\\GoogleDrive\\List_2_.xls","C:\\Pessoal\\GoogleDrive\\SemParar.pdf")) );
		
		//Criei diretorio
		SSHUtils.criaDir(usuario,senha,host,porta,"/home/desenv/200/");
		
		SSHUtils.renameFile(usuario, senha, host, porta, "/home/desenv/lista.xls", "/home/desenv/lista22.xls");
		
		SSHUtils.deleteFile(usuario, senha, host, porta, "/home/desenv/lista22.xls");
		
	}
	
	
}
