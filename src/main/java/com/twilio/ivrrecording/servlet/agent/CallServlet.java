package com.twilio.ivrrecording.servlet.agent;

import com.twilio.ivrrecording.servlet.WebAppServlet;
import com.twilio.sdk.verbs.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CallServlet extends WebAppServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String dialCallStatus = request.getParameter("dialCallStatus");
        String agentId = request.getParameter("agentId");

        if (dialCallStatus == "completed") {
            content(response, "");
            return;
        }

        TwiMLResponse twiml = new TwiMLResponse();

        Say say1 = new Say("It appears that no agent is available. " +
                "Please leave a message after the beep");

        say1.setLanguage("en-GB");
        say1.setVoice("alice");

        Record record = new Record();
        record.setMaxLength(20);
        record.setAction("/call/hangup");
        record.setTranscribeCallback(String.format("/record/create?agentId=%s", agentId));

        Say say2 = new Say("No record received. Goodbye");
        say2.setLanguage("en-GB");
        say2.setVoice("alice");

        Hangup hangup = new Hangup();

        try {
            twiml.append(say1);
            twiml.append(record);
            twiml.append(say2);
            twiml.append(hangup);
        } catch (TwiMLException e) {
            e.printStackTrace();
        }

        respondTwiML(response, twiml);

    }

}
