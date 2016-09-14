package com.twilio.ivrrecording.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import com.twilio.sdk.verbs.TwiMLResponse;

public class WebAppServlet extends HttpServlet {

  protected WebAppServlet() {}

  protected void respondTwiML(HttpServletResponse response, TwiMLResponse twiMLResponse)
      throws IOException {
    response.setContentType("text/xml");
    response.getWriter().write(twiMLResponse.toXML());
  }

  protected void respondContent(HttpServletResponse response, String content) throws IOException {
    response.getWriter().write(content);
  }
}
