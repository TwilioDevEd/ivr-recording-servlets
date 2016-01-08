package com.twilio.ivrrecording.servlet;

import com.twilio.sdk.verbs.TwiMLResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebAppServlet {

    protected WebAppServlet() {
    }

    protected void respondTwiML(HttpServletResponse response, TwiMLResponse twiMLResponse) throws IOException {
        response.setContentType("text/xml");
        response.getWriter().write(twiMLResponse.toXML());
    }

    protected void content(HttpServletResponse response, String content) throws IOException {
        response.getWriter().write(content);
    }
}
