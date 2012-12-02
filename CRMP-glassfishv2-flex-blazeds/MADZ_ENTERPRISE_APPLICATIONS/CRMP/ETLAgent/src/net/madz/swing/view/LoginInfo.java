package net.madz.swing.view;

public class LoginInfo {
	private static String _host ;
	private static String _port;
	private static String _user;
	
	private static String _dbAddr;
	private static String _dbPort;
	private static String _dbName;
	private static String _dbUserName;
	private static String _dbPwd;
	
	private static String _dbURL;
	
	
	public static synchronized  void setHostName(String hostName) {
		_host = hostName;
	}
	
	public static synchronized void setPort(String port) {
		_port = port;
	}
	
	public static synchronized void setUser(String user) {
		_user = user;
	}
	
	public static synchronized String getHostName () {
		return _host;
	}
	
	public static synchronized String getPort () {
		return _port;
	}
	
	public static synchronized String getUser () {
		return _user;
	}
	

	public static String get_dbAddr() {
		return _dbAddr;
	}

	public static synchronized void set_dbAddr(String addr) {
		_dbAddr = addr;
	}

	public static String get_dbName() {
		return _dbName;
	}

	public static synchronized void set_dbName(String name) {
		_dbName = name;
	}

	public static String get_dbPort() {
		return _dbPort;
	}

	public static synchronized void set_dbPort(String port) {
		_dbPort = port;
	}

	public static String get_dbPwd() {
		return _dbPwd;
	}

	public static synchronized void set_dbPwd(String pwd) {
		_dbPwd = pwd;
	}

	public static String get_dbUserName() {
		return _dbUserName;
	}

	public static synchronized void set_dbUserName(String userName) {
		_dbUserName = userName;
	}
	
	public static String get_dbURL() {
		return _dbURL;
	}
	
	public static synchronized void set_dbURL(String url) {
		_dbURL = url;
	}
	
}
