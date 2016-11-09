package org.paulushc.ssh;

import java.util.List;

public class SSHUtils {

	public static boolean existFile(String usuario,String senha, String host, int porta,String destino){

		ShellConnectionStream ssh = new ShellConnectionStream(usuario, senha, host, porta);
		try{
			ssh.connect();
			if(ssh.isReady()) {
				String retorno = ssh.write("[ -f " + destino +" ]  && echo 1 || echo 0 ");
				ssh.close();
				if(retorno != null){
					retorno = retorno.replace("\n", "");
					return  Long.parseLong(retorno) > 0;
				}
				return false;
			}
		}catch(Exception e){ e.printStackTrace(); }		
		return false;
	}

	public static boolean uploadTo(String usuario,String senha, String host, int porta,String destino,String dirDestino, String arquivo){
		criaDir(usuario, senha, host, porta, dirDestino);
		ShellConnectionStream ssh = new ShellConnectionStream(usuario, senha, host, porta);
		try{
			ssh.connect();
			if(ssh.isReady()) {
				if(ssh.upload(arquivo,destino, dirDestino)){
					ssh.close();
					return true;				
				}
			}
		}catch(Exception e){ e.printStackTrace(); }		
		return false;
	}

	public static boolean uploadTo(String usuario,String senha, String host, int porta,String dirDestino, List<String> arquivos){
		criaDir(usuario, senha, host, porta, dirDestino);
		ShellConnectionStream ssh = new ShellConnectionStream(usuario, senha, host, porta);
		try{
			ssh.connect();
			if(ssh.isReady()) {
				for(String arquivo : arquivos){
					if(!ssh.upload(arquivo, dirDestino)){
						ssh.close();
						return false;
					}
				}
				ssh.close();
				return true;
			}
		}catch(Exception e){ e.printStackTrace(); }		
		return false;
	}

	public static void criaDir(String usuario,String senha, String host, int porta, String dirDestino) {
		ShellConnectionStream ssh = new ShellConnectionStream(usuario, senha, host, porta);
		try{
			ssh.connect();
			if(ssh.isReady()) {
				ssh.write("mkdir -p " + dirDestino);
				ssh.close();
			}
		}catch(Exception e){ e.printStackTrace(); }		
		
	}
	
	public static void renameFile(String usuario,String senha, String host, int porta, String dirArquivoOriginal, String dirArquivoNovo) {
		ShellConnectionStream ssh = new ShellConnectionStream(usuario, senha, host, porta);
		try{
			ssh.connect();
			if(ssh.isReady()) {
				dirArquivoOriginal =  dirArquivoOriginal.replace(" ", "\\ ");
				ssh.write("mv " + dirArquivoOriginal + " " + dirArquivoNovo);
				ssh.close();
			}
		}catch(Exception e){ e.printStackTrace(); }		
		
	}
	
	public static void deleteFile(String usuario,String senha, String host, int porta, String dirArquivo) {
		ShellConnectionStream ssh = new ShellConnectionStream(usuario, senha, host, porta);
		try{
			ssh.connect();
			if(ssh.isReady()) {
				ssh.write("rm -f " + dirArquivo);
				ssh.close();
			}
		}catch(Exception e){ e.printStackTrace(); }		
		
	}
	
}
