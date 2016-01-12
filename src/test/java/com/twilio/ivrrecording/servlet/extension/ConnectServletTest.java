package com.twilio.ivrrecording.servlet.extension;

import com.twilio.ivrrecording.models.Agent;
import com.twilio.ivrrecording.repositories.AgentRepository;
import com.twilio.ivrrecording.servlet.BaseTwilioServletTest;
import com.twilio.ivrrecording.servlet.menu.ShowServlet;
import org.hamcrest.CoreMatchers;
import org.jdom2.Document;
import org.jdom2.Element;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class ConnectServletTest extends BaseTwilioServletTest {
    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    AgentRepository agentRepository;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void postMethod_WhenAnAgentIsFound_ThenRespondsByDialingTheAgentsPhoneNumber()
            throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(output);

        String number = "+12025550142";

        when(agentRepository.findByExtension(anyString())).thenReturn(new Agent(1, "Brodo", number));
        when(request.getParameter("Digits")).thenReturn("2");
        when(response.getWriter()).thenReturn(printWriter);

        ConnectServlet servlet = new ConnectServlet(agentRepository);
        servlet.doPost(request, response);

        printWriter.flush();
        String content = new String(output.toByteArray(), "UTF-8");

        Document document = getDocument(content);

        assertThatContentTypeIsXML(response);
        assertThat(getAttributeValue(document, "Dial/Number", "url"), is(equalTo("/agents/screen-call")));
        assertThat(getElement(document, "Dial/Number").getValue(), is(equalTo(number)));
    }

    @Test
    public void postMethod_WhenAnAgentIsNotFound_ThenRespondsRedirectingToMainMenu()
            throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(output);

        when(request.getParameter("Digits")).thenReturn("*");
        when(response.getWriter()).thenReturn(printWriter);

        ConnectServlet servlet = new ConnectServlet(agentRepository);
        servlet.doPost(request, response);

        printWriter.flush();
        String content = new String(output.toByteArray(), "UTF-8");

        Document document = getDocument(content);

        assertThatContentTypeIsXML(response);
        assertThat(getElement(document, "Redirect").getValue(), is(equalTo("/ivr/welcome")));
    }
}