package com.twilio.ivrrecording.servlet.ivr;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hamcrest.CoreMatchers;
import org.jdom2.Document;
import org.jdom2.Element;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.twilio.ivrrecording.servlet.BaseTwilioServletTest;

public class WelcomeServletTest extends BaseTwilioServletTest {
  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Before
  public void setUp() throws IOException {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void postMethod_ThenTheResponseContainsGatherPlay() throws Exception {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    PrintWriter printWriter = new PrintWriter(output);

    when(request.getParameter("Digits")).thenReturn("2");
    when(response.getWriter()).thenReturn(printWriter);

    WelcomeServlet servlet = new WelcomeServlet();
    servlet.doPost(request, response);

    printWriter.flush();
    String content = new String(output.toByteArray(), "UTF-8");

    Document document = getDocument(content);

    assertThatContentTypeIsXML(response);
    assertThat(getElement(document, "Play"), is(CoreMatchers.<Element>nullValue()));
    assertThat(getAttributeValue(document, "Gather", "action"), is(equalTo("/menu/show")));
  }
}
