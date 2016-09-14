package com.twilio.ivrrecording.servlet.menu;

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

public class ShowServletTest extends BaseTwilioServletTest {
  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Before
  public void setUp() throws IOException {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void postMethod_WhenTheSelectedOptionIs1_ThenTheResponseContainsSayTwiceAndAHangup()
      throws Exception {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    PrintWriter printWriter = new PrintWriter(output);

    when(request.getParameter("Digits")).thenReturn("1");
    when(response.getWriter()).thenReturn(printWriter);

    ShowServlet servlet = new ShowServlet();
    servlet.doPost(request, response);

    printWriter.flush();
    String content = new String(output.toByteArray(), "UTF-8");

    Document document = getDocument(content);

    assertThatContentTypeIsXML(response);
    assertThat(countElement(document, "Say"), is(equalTo(2)));
    assertThat(getElement(document, "Hangup"), is(CoreMatchers.<Element>notNullValue()));
  }

  @Test
  public void postMethod_WhenTheSelectedOptionIs1_WhenTheSelectedOptionIs2_ThenTheResponseContainsGatherAndSay()
      throws Exception {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    PrintWriter printWriter = new PrintWriter(output);

    when(request.getParameter("Digits")).thenReturn("2");
    when(response.getWriter()).thenReturn(printWriter);

    ShowServlet servlet = new ShowServlet();
    servlet.doPost(request, response);

    printWriter.flush();
    String content = new String(output.toByteArray(), "UTF-8");

    Document document = getDocument(content);

    assertThatContentTypeIsXML(response);
    assertThat(getElement(document, "Say"), is(CoreMatchers.<Element>nullValue()));
    assertThat(getAttributeValue(document, "Gather", "action"), is(equalTo("/extensions/connect")));
  }

  @Test
  public void postMethod_WhenTheSelectedOptionIsDifferentThan_1_Or_2_ThenTheResponseRedirectsToIVRWelcome()
      throws Exception {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    PrintWriter printWriter = new PrintWriter(output);

    when(request.getParameter("Digits")).thenReturn("*");
    when(response.getWriter()).thenReturn(printWriter);

    ShowServlet servlet = new ShowServlet();
    servlet.doPost(request, response);

    printWriter.flush();
    String content = new String(output.toByteArray(), "UTF-8");

    Document document = getDocument(content);

    assertThatContentTypeIsXML(response);
    assertThat(getElement(document, "Redirect").getValue(), is(equalTo("/ivr/welcome")));
  }
}
