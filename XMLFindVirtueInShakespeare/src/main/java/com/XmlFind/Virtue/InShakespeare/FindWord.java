package com.XmlFind.Virtue.InShakespeare;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

import org.apache.log4j.Logger;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class FindWord {

	private static final Logger log = Logger.getLogger(FindWord.class);
	private File xml;
	private String name;
	private boolean isFound = false;
	private int counter = 0;

	public FindWord(File xml) {
		this.xml = xml;
		readXML(xml);
	}

	private void readXML(File xml) {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(xml);
			Element rootNode = document.getRootElement();
			List<Element> play = rootNode.getChildren("ACT");
			log.debug("Root Element : " + rootNode.getName());
			log.debug("TITLE OF PLAY : " + rootNode.getChildText("TITLE"));
			log.debug("----------------------------------------------");
			for (int i = 0; i < play.size(); i++) {
				Element shakespeare = play.get(i);
				List<Element> scenary = shakespeare.getChildren("SCENE");
				for (int j = 0; j < scenary.size(); j++) {
					Element scene = scenary.get(j);
					scene.getChildren("SPEECH");
					for (Element s : scene.getChildren("SPEECH")) {
						for (Element part : s.getChildren()) {
							findVirtue(part);

						}
					}
				}
			}
			if (isFound) {
				createDir();
				XMLOutputter newDoc = new XMLOutputter();
				newDoc.setFormat(Format.getPrettyFormat());
				// newDoc.output(play, new FileWriter("Change Directory to the
				// pointed directory in createDir()" + xml.getName()));
				log.debug("COUNT FOR " + xml.getName() + ": " + counter);
				counter = 0;
			}
		} catch (IOException io) {
			log.error(io);
		} catch (JDOMException jdomex) {
			log.error(jdomex);
		} catch (TransformerFactoryConfigurationError e) {
			log.error(e);
		} catch (TransformerException a) {
			log.error(a);
		}
	}

	private void findVirtue(Element a)
			throws JDOMException, IOException, TransformerFactoryConfigurationError, TransformerException {
		SAXBuilder builder = new SAXBuilder();
		builder.build(xml);
		Pattern p = Pattern.compile("(?i)\\bvirtue('?s)?\\b", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(a.getText());
		List<String> virtue = new ArrayList<String>();
		while (m.find()) {
			isFound = true;
			virtue.add(m.group());
			log.debug("FOUND : " + m.group());
			log.debug("REPLACEMENT : " + m.replaceAll("RyanKahil"));
			a.setText(m.replaceAll("RyanKahil"));
			counter++;
		}
	}

	private void createDir() {
		// File dir = new File("Change Directory and point to new Folder");
		// dir.mkdirs();
	}
}
