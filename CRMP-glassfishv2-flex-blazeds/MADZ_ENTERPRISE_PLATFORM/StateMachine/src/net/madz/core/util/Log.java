package net.madz.core.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {

	private static Logger log = Logger.getAnonymousLogger();

	public static void verbose(String tag, String message) {
		log.log(Level.FINEST, message);
	}

	public static void info(String tag, String message) {
		System.out.println("[Method]: " + tag + "\t\t[Thread]" + Thread.currentThread().getName() + "\n\t[Message]" + message);
		// log.log(Level.INFO, message);
	}

	public static void warn(String tag, String message) {
		log.log(Level.WARNING, message);
	}

	public static void error(String tag, String message) {
		log.log(Level.SEVERE, message);
	}

	public static void error(String tag, String message, Throwable throwable) {
		log.log(Level.SEVERE, message, throwable);
	}
}
