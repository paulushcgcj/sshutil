package io.github.paulushcgcj.sshutils;

import java.util.ArrayList;
import java.util.Arrays;
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
		return fileExist(ssh,destination);
	}

	/**
	 * Check if a specific file exist in remote server
	 * @param ssh The {@link ShellConnectionStream} object with the connection data set
	 * @param destination file path to be checked
	 * @return true if exists, false if not
	 */
	public static boolean fileExist(ShellConnectionStream ssh, String destination){
		try{
			ssh.connect();
			if(ssh.isReady()) {
				String returnValue = ssh.write("[ -f " + destination +" ]  && echo 1 || echo 0 ");
				ssh.close();
				if(returnValue != null){
					returnValue = returnValue.replace("\n", "");
					return  Long.parseLong(returnValue) > 0;
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

		ShellConnectionStream ssh = ShellConnectionStream.builder()
				.username(username)
				.password(password)
				.host(host)
				.port(port)
				.build();

        makeDir(ssh, destinationDirectory);
		return uploadTo(destination, destinationDirectory, originFilePath, ssh);
	}

	/**
	 * Upload a local file to a remote location
	 * @param ssh The {@link ShellConnectionStream} object with the connection data set
	 * @param destination remote file path
	 * @param destinationDirectory remote folder
	 * @param originFilePath local file path
	 * @return true if manage to upload, false if don't
	 */
	public static boolean uploadTo(String destination, String destinationDirectory, String originFilePath, ShellConnectionStream ssh) {
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

        ShellConnectionStream ssh = ShellConnectionStream.builder()
                .username(username)
                .password(password)
                .host(host)
                .port(port)
            .build();

		return uploadTo(destinationDirectory, files, ssh);
	}

	/**
	 * Upload a list of files to a specific location
	 * @param ssh The {@link ShellConnectionStream} object with the connection data set
	 * @param destinationDirectory a remote directory to save all files
	 * @param files a list of file paths
	 * @return true if manage to upload, false if don't
	 */
	public static boolean uploadTo(String destinationDirectory, List<String> files, ShellConnectionStream ssh) {
		makeDir(ssh, destinationDirectory);

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

		makeDir(ssh, destinationDirectory);

	}

	/**
	 * Create a directory on remote server
	 * @param ssh The {@link ShellConnectionStream} object with the connection data set
	 * @param destinationDirectory full path to be created into remote server
	 */
	public static void makeDir(ShellConnectionStream ssh, String destinationDirectory) {
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
		renameFile(originalFilePath, destinationFilePath, ssh);

	}

	/**
	 * Rename a remote file
	 * @param ssh The {@link ShellConnectionStream} object with the connection data set
	 * @param originalFilePath original path to file
	 * @param destinationFilePath new location or name of remote file
	 */
	public static void renameFile(String originalFilePath, String destinationFilePath, ShellConnectionStream ssh) {
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
		deleteFile(remoteFilePath, ssh);

	}

	/**
	 * Delete a file from the remote server
	 * @param ssh The {@link ShellConnectionStream} object with the connection data set
	 * @param remoteFilePath Remote location to file that needs to be removed
	 */
	public static void deleteFile(String remoteFilePath, ShellConnectionStream ssh) {
		try{
			ssh.connect();
			if(ssh.isReady()) {
				ssh.write("rm -f " + remoteFilePath);
				ssh.close();
			}
		}catch(Exception e){ e.printStackTrace(); }
	}

	/**
	 * List a content of a remote folder
	 * @param username Username to connect
	 * @param password password of user
	 * @param host host to connect to
	 * @param port host port
	 * @param remoteFilePath Remote location to folder
	 * @return A list of files from that specific folder
	 */
	public static List<String> listContent(String username,String password, String host, int port, String remoteFilePath){
		ShellConnectionStream ssh = ShellConnectionStream.builder()
				.username(username)
				.password(password)
				.host(host)
				.port(port)
				.build();

		return listContent(remoteFilePath, ssh);
	}

	/**
	 * List a content of a remote folder
	 * @param ssh The {@link ShellConnectionStream} object with the connection data set
	 * @param remoteFilePath Remote location to folder
	 * @return A list of files from that specific folder
	 */
	public static List<String> listContent(String remoteFilePath, ShellConnectionStream ssh) {
		List<String> contentList = new ArrayList<>();
		try{
			ssh.connect();
			if(ssh.isReady()) {
				String contentListAsString = ssh.write("find " + remoteFilePath + " -maxdepth 1");
				ssh.close();
				contentList.addAll(Arrays.asList(contentListAsString.split("\n")));
			}
		}catch(Exception e){ e.printStackTrace(); }

		return contentList;
	}

	/**
	 * Download a remote file
	 * @param username Username to connect
	 * @param password password of user
	 * @param host host to connect to
	 * @param port host port
	 * @param remoteFilePath Remote location to file that will be downloaded
	 * @param localFilePath Path to save the local file
	 * @return true in case of success download, false if didn't
	 */
	public static boolean downloadFile(String username,String password, String host, int port, String remoteFilePath,String localFilePath){

		ShellConnectionStream ssh = ShellConnectionStream.builder()
				.username(username)
				.password(password)
				.host(host)
				.port(port)
				.build();

		return downloadFile(remoteFilePath, localFilePath, ssh);
	}

	/**
	 * Download a remote file
	 * @param ssh The {@link ShellConnectionStream} object with the connection data set
	 * @param remoteFilePath Remote location to file that will be downloaded
	 * @param localFilePath Path to save the local file
	 * @return true in case of success download, false if didn't
	 */
	public static boolean downloadFile(String remoteFilePath, String localFilePath, ShellConnectionStream ssh) {
		boolean downloadedFile = false;

		try{
			ssh.connect();
			if(ssh.isReady()) {
				downloadedFile = ssh.download(remoteFilePath,localFilePath);
				ssh.close();
			}
		}catch(Exception e){ e.printStackTrace(); }

		return downloadedFile;
	}
}
