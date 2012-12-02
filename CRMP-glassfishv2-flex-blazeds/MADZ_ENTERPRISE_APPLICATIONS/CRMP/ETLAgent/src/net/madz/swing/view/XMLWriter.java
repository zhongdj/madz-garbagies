package net.madz.swing.view;

import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.XMLOutputter;

public class XMLWriter {
	public void buildXMLDoc(String server, String user) throws IOException,
			JDOMException {

		Element root = new Element("loginfo");

		Document Doc = new Document(root);

		Element elements = new Element("log");

		elements.setAttribute("ipaddress", "" + server);
		elements.setAttribute("username", "" + user);

		root.addContent(elements);
		root.addContent("\n");

		XMLOutputter XMLOut = new XMLOutputter();

		XMLOut.output(Doc, new FileOutputStream("loginfo.xml"));
	}
}
