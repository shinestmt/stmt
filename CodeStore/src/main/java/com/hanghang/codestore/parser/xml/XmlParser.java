package com.hanghang.codestore.parser.xml;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlParser {

	private static XmlParser parser = new XmlParser();

	private void parser(InputStream is) throws Exception {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(is);	//解析获得XML文档对象
		Element element = document.getDocumentElement();	//获取文档内容

		NodeList bookNodes = element.getElementsByTagName("book");	//book节点
		//遍历book内的节点
		for (int i = 0; i < bookNodes.getLength(); i++) {
			Element bookElement = (Element) bookNodes.item(i);
			String id = bookElement.getAttribute("id");	//获取属性
			System.out.println("ID : " + id);
			
			NodeList childNodes = bookElement.getChildNodes();
			for (int j = 0; j < childNodes.getLength(); j++) {
				if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
					if ("name".equals(childNodes.item(j).getNodeName())) {
						String bookName = childNodes.item(j).getFirstChild().getNodeValue();
						System.out.println("书名 : " + bookName);
					} else if ("price".equals(childNodes.item(j).getNodeName())) {
						String price = childNodes.item(j).getFirstChild().getNodeValue();
						System.out.println("价格 : " + price);
					}
				}
			}
			System.out.println("---------------------------------");
		}
	}

	public static void main(String[] args) {
		try {
			InputStream is = new FileInputStream("test.xml");
			
			parser.parser(is);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
