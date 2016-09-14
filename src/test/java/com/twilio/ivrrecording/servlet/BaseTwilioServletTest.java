package com.twilio.ivrrecording.servlet;

import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.io.StringReader;

import javax.servlet.http.HttpServletResponse;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


public abstract class BaseTwilioServletTest {

  protected Document getDocument(String content) throws JDOMException, IOException {
    return new SAXBuilder().build(new StringReader(content));
  }

  protected Element getElement(Document document, String path) {
    String[] pathComponents = path.split("/");

    switch (pathComponents.length) {
      case 1:
        return document.getRootElement().getChild(pathComponents[0]);
      case 2:
        return document.getRootElement().getChild(pathComponents[0]).getChild(pathComponents[1]);
      case 3:
        return document.getRootElement().getChild(pathComponents[0]).getChild(pathComponents[1])
            .getChild(pathComponents[2]);
      default:
        return null;
    }
  }

  protected int countElement(Document document, String path) {
    return document.getRootElement().getChildren(path).size();
  }

  protected String getAttributeValue(Document document, String path, String attrName) {
    Element element = getElement(document, path);
    return element.getAttributeValue(attrName);
  }

  protected void assertThatContentTypeIsXML(HttpServletResponse response) {
    verify(response).setContentType("text/xml");
  }
}

