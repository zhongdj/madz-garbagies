package net.madz.swing.view;

import java.io.FileInputStream;
import java.util.Iterator;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Text;
import org.jdom.input.SAXBuilder;

public class XMLParser {

	private String server = null;
	private String user = null;

	public XMLParser() throws Exception {
		parse();
	}

	private void parse() throws Exception {

		SAXBuilder sb = new SAXBuilder();
		Document doc = sb.build(new FileInputStream("loginfo.xml"));
		Element root = doc.getRootElement();

		java.util.List ls = root.getChildren();
		Iterator i = ls.iterator();
		while (i.hasNext()) {
			Object o = i.next();
			if (o instanceof Text) {

			} else if (o instanceof Attribute) {

			} else if (o instanceof Element) {
				Element el = (Element) o;
				server = el.getAttributeValue("ipaddress");
				user = el.getAttributeValue("username");
			}
		}
	}

	public String getUser() {
		return this.user;
	}

	public String getServer() {
		return this.server;
	}

}