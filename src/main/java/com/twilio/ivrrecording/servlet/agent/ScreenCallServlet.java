package com.twilio.ivrrecording.servlet.agent;

import com.twilio.ivrrecording.servlet.WebAppServlet;
import com.twilio.sdk.verbs.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ScreenCallServlet extends WebAppServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String from = request.getParameter("From");

        String incomingCallMessage = "You have an incoming call from: " + getSpelledPhoneNumber(from);

        TwiMLResponse twiml = new TwiMLResponse();

        Gather gather = new Gather();
        gather.setNumDigits(1);
        gather.setAction("/agents/message");

        Say sayInGather1 = new Say(incomingCallMessage);
        Say sayInGather2 = new Say("Press any key to accept");

        Say say = new Say("Sorry. Did not get your response");
        Hangup hangup = new Hangup();

        try {
            gather.append(sayInGather1);
            gather.append(sayInGather2);

            twiml.append(gather);
            twiml.append(say);
            twiml.append(hangup);
        } catch (TwiMLException e) {
            e.printStackTrace();
        }

        respondTwiML(response, twiml);
    }

    private String getSpelledPhoneNumber(String phoneNumber) {
        return String.join(", ", phoneNumber.split(""));
    }

}
