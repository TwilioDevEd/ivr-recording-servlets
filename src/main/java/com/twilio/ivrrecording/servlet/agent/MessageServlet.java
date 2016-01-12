package com.twilio.ivrrecording.servlet.agent;

import com.twilio.ivrrecording.servlet.WebAppServlet;
import com.twilio.sdk.verbs.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MessageServlet extends WebAppServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TwiMLResponse twiml = new TwiMLResponse();

        Say say = new Say("Connecting you to the extraterrestrial in distress");

        try {
            twiml.append(say);
        } catch (TwiMLException e) {
            e.printStackTrace();
        }

        respondTwiML(response, twiml);
    }

}
