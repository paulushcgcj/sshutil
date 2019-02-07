package org.paulushc.sshutils;

import java.util.List;

public class SSHUtils {

	/**
	 * Check if a specific file exist in remote server
	 * @param username Username to connect
	 * @param password password of user
	 * @param host host to connect to
	 * @param port host port
	 * @param destination file path to be checked
	 * @return true if exists, false if not
	 */
	public static boolean fileExist(String username, String password, String host, int port, String destination){

		ShellConnectionStream ssh = ShellConnectionStream.builder()
				.username(username)
				.password(password)
				.host(host)
				.port(port)
			.build();
		try{
			ssh.connect();
			if(ssh.isReady()) {
				String retorno = ssh.write("[ -f " + destination +" ]  && echo 1 || echo 0 ");
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

    /**
     * Upload a local file to a remote location
     * @param username Username to connect
     * @param password password of user
     * @param host host to connect to
     * @param port host port
     * @param destination remote file path
     * @param destinationDirectory remote folder
     * @param originFilePath local file path
     * @return true if manage to upload, false if don't
     */
	public static boolean uploadTo(String username,String password, String host, int port,String destination,String destinationDirectory, String originFilePath){

        makeDir(username, password, host, port, destinationDirectory);

		ShellConnectionStream ssh = ShellConnectionStream.builder()
				.username(username)
				.password(password)
				.host(host)
				.port(port)
				.build();

		try{
			ssh.connect();
			if(ssh.isReady() && ssh.upload(originFilePath,destination, destinationDirectory)){
                ssh.close();
                return true;
			}
		}catch(Exception e){ e.printStackTrace(); }		
		return false;
	}

    /**
     * Upload a list of files to a specific location
     * @param username Username to connect
     * @param password password of user
     * @param host host to connect to
     * @param port host port
     * @param destinationDirectory a remote directory to save all files
     * @param files a list of file paths
     * @return true if manage to upload, false if don't
     */
	public static boolean uploadTo(String username,String password, String host, int port,String destinationDirectory, List<String> files){

        makeDir(username, password, host, port, destinationDirectory);

        ShellConnectionStream ssh = ShellConnectionStream.builder()
                .username(username)
                .password(password)
                .host(host)
                .port(port)
            .build();

		try{
			ssh.connect();
			if(ssh.isReady()) {
				for(String filePath : files){
					if(!ssh.upload(filePath, destinationDirectory)){
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

    /**
     * Create a directory on remote server
     * @param username Username to connect
     * @param password password of user
     * @param host host to connect to
     * @param port host port
     * @param destinationDirectory full path to be created into remote server
     */
	public static void makeDir(String username,String password, String host, int port, String destinationDirectory) {
        ShellConnectionStream ssh = ShellConnectionStream.builder()
                .username(username)
                .password(password)
                .host(host)
                .port(port)
            .build();

		try{
			ssh.connect();
			if(ssh.isReady()) {
				ssh.write("mkdir -p " + destinationDirectory);
				ssh.close();
			}
		}catch(Exception e){ e.printStackTrace(); }		
		
	}

    /**
     * Rename a remote file
     * @param username Username to connect
     * @param password password of user
     * @param host host to connect to
     * @param port host port
     * @param originalFilePath original path to file
     * @param destinationFilePath new location or name of remote file
     */
	public static void renameFile(String username,String password, String host, int port, String originalFilePath, String destinationFilePath) {
        ShellConnectionStream ssh = ShellConnectionStream.builder()
                .username(username)
                .password(password)
                .host(host)
                .port(port)
            .build();
		try{
			ssh.connect();
			if(ssh.isReady()) {
				originalFilePath =  originalFilePath.replace(" ", "\\ ");
				ssh.write("mv " + originalFilePath + " " + destinationFilePath);
				ssh.close();
			}
		}catch(Exception e){ e.printStackTrace(); }		
		
	}

    /**
     * Delete a file from the remote server
     * @param username Username to connect
     * @param password password of user
     * @param host host to connect to
     * @param port host port
     * @param remoteFilePath Remote location to file that needs to be removed
     */
	public static void deleteFile(String username,String password, String host, int port, String remoteFilePath) {
        ShellConnectionStream ssh = ShellConnectionStream.builder()
                .username(username)
                .password(password)
                .host(host)
                .port(port)
            .build();
		try{
			ssh.connect();
			if(ssh.isReady()) {
				ssh.write("rm -f " + remoteFilePath);
				ssh.close();
			}
		}catch(Exception e){ e.printStackTrace(); }		
		
	}
	
}
