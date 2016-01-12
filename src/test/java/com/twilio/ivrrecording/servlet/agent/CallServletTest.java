package com.twilio.ivrrecording.servlet.agent;

import com.twilio.ivrrecording.servlet.BaseTwilioServletTest;
import junitparams.JUnitParamsRunner;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.core.Is;
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
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
public class CallServletTest extends BaseTwilioServletTest {
    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void postMethod_WhenStatusIsDifferentThanCompleted_ThenRecordTheCallAndHangup() throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(output);

        when(request.getParameter("agentId")).thenReturn("1");
        when(request.getParameter("dialCallStatus")).thenReturn("busy");
        when(response.getWriter()).thenReturn(printWriter);

        CallServlet servlet = new CallServlet();
        servlet.doPost(request, response);

        printWriter.flush();
        String content = new String(output.toByteArray(), "UTF-8");

        Document document = getDocument(content);

        assertThatContentTypeIsXML(response);
        assertThat(getAttributeValue(document, "Record", "action"), is(equalTo("/agents/hangup")));
        assertThat(getAttributeValue(document, "Record", "transcribeCallback"), is(equalTo("/record/create?agentId=1")));
        assertThat(getElement(document, "Hangup"), is(CoreMatchers.<Element>notNullValue()));
    }

    @Test
    public void postMethod_WhenStatusIsCompleted_ThenResponseWillBeEmpty() throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(output);

        when(request.getParameter("agentId")).thenReturn("1");
        when(request.getParameter("dialCallStatus")).thenReturn("completed");
        when(response.getWriter()).thenReturn(printWriter);

        CallServlet servlet = new CallServlet();
        servlet.doPost(request, response);

        printWriter.flush();
        String content = new String(output.toByteArray(), "UTF-8");

        assertThat(content, is(equalTo("")));
    }
}