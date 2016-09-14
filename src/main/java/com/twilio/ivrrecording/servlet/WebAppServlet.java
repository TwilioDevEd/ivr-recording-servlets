package com.twilio.ivrrecording.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import com.twilio.twiml.TwiMLException;
import com.twilio.twiml.VoiceResponse;

public class WebAppServlet extends HttpServlet {

  protected WebAppServlet() {}

  protected void respondTwiML(HttpServletResponse response, VoiceResponse twiMLResponse)
      throws IOException {
    response.setContentType("text/xml");
    try {
      response.getWriter().write(twiMLResponse.toXml());
    } catch (TwiMLException e) {
      System.out.println("Unable to create TwiML");
    }
  }

  protected void respondContent(HttpServletResponse response, String content) throws IOException {
    response.getWriter().write(content);
  }
}
