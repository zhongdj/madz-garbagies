package net.madz.swing.resources;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Hashtable;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import javax.swing.ImageIcon;

public class ResourceFactory {
	private static String PACKAGE_NAME = "net.madz.swing.resources.";

	private static ResourceFactory _instance = null;

	private Locale _locale = Locale.ENGLISH;
	private Hashtable _domainHashtable = new Hashtable();
	private Hashtable _icons = new Hashtable();

	public ResourceFactory() {
		_locale = Locale.CHINESE;//ENGLISH;
		
	}

	public Locale getLocale() {
		return _locale;
	}

	public static ResourceFactory getInstance() {
		if (_instance == null) {
			_instance = new ResourceFactory();
		}

		return _instance;
	}

	/**
	 * 
	 * 
	 * @param domain
	 *            the prop file name
	 * @param key
	 *            the key
	 * @return
	 */
	public String getString(String domain, String key) {
		String result = null;
		ResourceBundle rb = null;

		if (!_domainHashtable.containsKey(domain)) {
			String completeDomain = PACKAGE_NAME + domain;
			try {
				rb = ResourceBundle.getBundle(completeDomain, _locale);
				_domainHashtable.put(domain, rb);
			} catch (MissingResourceException e) {

			}
		} else {
			rb = (ResourceBundle) _domainHashtable.get(domain);
		}

		if (rb != null) {
			try {
				result = rb.getString(key);
			} catch (MissingResourceException e) {
				result = "Missing resource : (" + domain + ") " + key + " : "
						+ e.getMessage();

			}
		} else {
			result = "Unknown resource: (" + domain + ")" + key;
		}

		return result;
	}

	public String getStringEx(String domain, String key) {
		ResourceBundle rb = null;

		if (!_domainHashtable.containsKey(domain)) {
			String completeDomain = PACKAGE_NAME + domain;
			try {
				rb = ResourceBundle.getBundle(completeDomain, _locale);
				_domainHashtable.put(domain, rb);
			} catch (MissingResourceException e) {

			}
		} else {
			rb = (ResourceBundle) _domainHashtable.get(domain);
		}

		if (rb != null) {
			try {
				return rb.getString(key);
			} catch (MissingResourceException e) {

				return null;
			}
		} else {
			return null;
		}
	}

	public ImageIcon createImageIcon(String name) {
		ImageIcon result = null;

		if (name != null) {
			if (!_icons.containsKey(name)) {
				URL url = ResourceFactory.class.getResource(name);
				
				if (url == null) {
					result = null;
				} else {
					result = new ImageIcon(url);
				}

				if (result != null) {
					_icons.put(name, result);
				}
			}

			else {
				result = (ImageIcon) _icons.get(name);
			}
		} else {

		}

		return result;
	}

	public URL getUrlFromString(String name) {
		URL url = this.getClass().getResource(name);
		return url;
	}

	public FileInputStream getFileInputStream(String fileName)
			throws FileNotFoundException {
		URL resource = this.getClass().getResource(fileName);

		if (resource == null) {
			throw new FileNotFoundException("File " + fileName
					+ "not found by " + getClass());
		}

		String path = resource.getPath();

		path = _replaceAllSubstrings(path, "%20", " ");
		FileInputStream fis = new FileInputStream(path);

		return fis;
	}

	public InputStream getInputStream(String fileName) throws IOException {
		URL resource = this.getClass().getResource(fileName);
		if (resource == null) {
			throw new FileNotFoundException("File " + fileName
					+ "not found by " + getClass());
		}

		InputStream is = null;

		try {
			String s = resource.getPath();

			s = _replaceAllSubstrings(s, "%20", " ");

			s = s.substring(s.indexOf(':') + 1, s.indexOf('!'));

			JarFile jarfile = new JarFile(s);

			ZipEntry zipEntry = jarfile
					.getEntry("com/nsn/tnms/client/resources/" + fileName);

			if (zipEntry != null)
				is = jarfile.getInputStream(zipEntry);
		} catch (IOException e) {

		}
		return is;
	}

	public String fileToString(String filename) {
		StringBuffer contents = new StringBuffer();
		InputStream inputStream = null;
		FileInputStream fileStream = null;
		BufferedReader reader = null;
		boolean eof = false;

		try {
			fileStream = getFileInputStream(filename);
		} catch (FileNotFoundException e) {
			try {
				inputStream = getInputStream(filename);
			} catch (IOException ioe) {

			}
		}

		if (fileStream != null)
			reader = new BufferedReader(new InputStreamReader(fileStream));
		else if (inputStream != null)
			reader = new BufferedReader(new InputStreamReader(inputStream));

		if (reader != null) {
			try {
				while (!eof) {
					String line = reader.readLine();

					if (line == null)
						eof = true;
					else
						contents.append(line);
				}

				reader.close();
			} catch (IOException e) {

			}

			finally {

				if (reader != null) {
					try {
						reader.close();
					} catch (IOException ioe) {

						reader = null;
					}
				}

			}
		}

		else {

		}

		return contents.toString();
	}

	public InputStream getWSInputStream(String filename) throws IOException {
		InputStream inputStream = null;
		FileInputStream fileStream = null;

		try {
			fileStream = getFileInputStream(filename);
		} catch (FileNotFoundException e) {

			inputStream = getInputStream(filename);

		}

		if (fileStream != null)
			return fileStream;
		else
			return inputStream;
	}

	public String _replaceAllSubstrings(String toReplace,
			String tokenToReplace, String replaceToken) {
		String res = new String(toReplace);

		while ((res != null) && res.indexOf(tokenToReplace) != -1) {

			res = _replaceToken(res, tokenToReplace, replaceToken);
		}

		return res;
	}

	private String _replaceToken(String s, String token, String newValue) {
		int start = s.indexOf(token);
		int end = start + token.length();

		String cmde = "";

		if (start != -1) {
			cmde = new String(s.substring(0, start) + newValue
					+ s.substring(end, s.length()));
		} else {
			cmde = s;
		}
		return cmde;
	}

	
}