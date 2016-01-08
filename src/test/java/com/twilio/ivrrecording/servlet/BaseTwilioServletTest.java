package com.twilio.ivrrecording.servlet;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.StringReader;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


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
                return document.getRootElement()
                        .getChild(pathComponents[0])
                        .getChild(pathComponents[1]);
            case 3:
                return document.getRootElement()
                        .getChild(pathComponents[0])
                        .getChild(pathComponents[1])
                        .getChild(pathComponents[2]);
            default:
                return null;
        }
    }

    protected String getAttributeValue(Document document, String path, String attrName) {
        Element element = getElement(document, path);
        return element.getAttributeValue(attrName);
    }

    protected void assertThatContentTypeIsXML(HttpServletResponse response) {
        verify(response).setContentType("text/xml");
    }
}

