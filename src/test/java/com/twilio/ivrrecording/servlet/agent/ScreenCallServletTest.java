package com.twilio.ivrrecording.servlet.agent;

import com.twilio.ivrrecording.servlet.BaseTwilioServletTest;
import junitparams.JUnitParamsRunner;
import org.hamcrest.CoreMatchers;
import org.jdom2.Document;
import org.jdom2.Element;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
public class ScreenCallServletTest extends BaseTwilioServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void postMethod_ThenResponseContainsGatherAndHangup() throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(output);

        when(request.getParameter("from")).thenReturn("1234567890");
        when(response.getWriter()).thenReturn(printWriter);

        ScreenCallServlet servlet = new ScreenCallServlet();
        servlet.doPost(request, response);

        printWriter.flush();
        String content = new String(output.toByteArray(), "UTF-8");

        Document document = getDocument(content);

        assertThatContentTypeIsXML(response);
        assertThat(getAttributeValue(document, "Gather", "action"), is(equalTo("/agent/connect")));
        assertThat(getElement(document, "Hangup"), is(CoreMatchers.<Element>notNullValue()));
    }

    @Test
    public void postMethod_ThenResponseContainsSpelledPhoneNumber() throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(output);

        when(request.getParameter("from")).thenReturn("1234567890");
        when(response.getWriter()).thenReturn(printWriter);

        ScreenCallServlet servlet = new ScreenCallServlet();
        servlet.doPost(request, response);

        printWriter.flush();
        String content = new String(output.toByteArray(), "UTF-8");

        assertThat(content, containsString("1, 2, 3, 4, 5, 6, 7, 8, 9, 0"));
    }
}