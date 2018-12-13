
package me.colley.mpp2xls.config.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sun.xml.internal.ws.api.model.Parameter;

import me.colley.mpp2xls.config.Column;
import me.colley.mpp2xls.config.Config;

public class XMLConfigReader {

	String filePath = "config.xml";

	public XMLConfigReader(String filePath) {
		System.out.println("filepath=" + filePath);
		this.filePath = filePath;
	}

	public Config getConfig() {
		File xmlFile = new File(this.filePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Config config = new Config();
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			config.infile = doc.getElementsByTagName("infile").item(0).getTextContent();
			System.out.println("infile :" + config.infile);
			
			config.outfile = doc.getElementsByTagName("outfile").item(0).getTextContent();
			System.out.println("outfile :" + config.outfile);

			config.dateFormat = doc.getElementsByTagName("dateFormat").item(0).getTextContent();
			System.out.println("dateFormat :" + config.dateFormat);
//			NodeList defaults = doc.getElementsByTagName("dateFormat");
//			for( int i = 0; i < defaults.getLength(); i++ ) {
//				//config.defaults.dateFormat = doc.getElementsByTagName("dateFormat").item(0).getTextContent();
//				Node n = defaults.item(i);
//				System.out.println(n.toString());
//			}

			NodeList nodeList = doc.getElementsByTagName("column");
			// now XML is loaded as Document in memory, lets convert it to
			// Object List
			// List<Column> colList = new ArrayList<Column>();

			for (int i = 0; i < nodeList.getLength(); i++) {
				// colList.add(getColumn(nodeList.item(i)));
				// System.out.println("i="+i);
				Node n = nodeList.item(i);
				Column c = getColumn(n);
				config.columns.add(c);
			}
			// // lets print Column list information
			// for (Column col : config.columns) {
			// System.out.println(col.toString());
			// }

		} catch (SAXException | ParserConfigurationException | IOException e1) {
			e1.printStackTrace();
		}

		return config;
	}

	// public static void main(String[] args) {
	// }

	private static Column getColumn(Node colNode) {
		Column col = new Column();
		if (colNode.getNodeType() == Node.ELEMENT_NODE) {
			Element colElement = (Element) colNode;
			// check for 'deep' definition
			if (colElement.getElementsByTagName("name").item(0) != null) {
				col.type = colElement.getAttribute("type");
				col.of = colElement.getAttribute("of");
				col.name = colElement.getElementsByTagName("name").item(0).getTextContent(); // .item(0).toString();
				col.method = colElement.getElementsByTagName("method").item(0).getTextContent();
				col.params = new ArrayList<MyParameter>();

				// <params>
				NodeList paramList = colElement.getElementsByTagName("parameter");
				for (int i = 0; i < paramList.getLength(); i++) {
					Element pEl = (Element) paramList.item(i);
					Class<?> cls;
					try {
						MyParameter param = new MyParameter();
						param.type = pEl.getAttribute("type");
						switch (param.type) {
						case "int":
							param.value = new Integer(pEl.getTextContent());
						case "java.lang.Integer":
							param.value = new Integer(pEl.getTextContent());
							break;
						case "java.lang.Number":
							param.value = new Double(pEl.getTextContent());
							break;
						case "java.lang.String":
							param.value = pEl.getTextContent();
							break;
						default:
							cls = Class.forName(pEl.getAttribute("type"));
							param.value = cls.newInstance();
						}
						col.params.add(param);
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// </params>
				// shallow definition
			} else {
				col.name = colElement.getTextContent();
				col.type = colElement.getAttribute("type");
				col.of = colElement.getAttribute("of");
			}
		}
		return col;
	}

	// private static String getTagValue(String tag, Element element) {
	//// System.out.println("getTagValue: tag:"+tag);
	//// NodeList nodeList =
	// element.getElementsByTagName(tag).item(0).getChildNodes();
	//// Node node = (Node) nodeList.item(0);
	//// return node.getNodeValue();
	// return element.getNodeValue();
	// }

	// private static String getTagAttribute(String tag, Element element) {
	// // NodeList nodeList =
	// // element.getElementsByTagName(tag).item(0).getChildNodes();
	// // Node node = (Node) nodeList.item(0);
	// // NamedNodeMap map = node.getAttributes();
	// // return map.getNamedItem(tag).toString();
	// return element.getAttribute(tag);
	// }

}
