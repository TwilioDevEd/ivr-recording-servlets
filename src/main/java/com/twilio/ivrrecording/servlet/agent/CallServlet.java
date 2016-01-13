package com.twilio.ivrrecording.servlet.agent;

import com.twilio.ivrrecording.servlet.WebAppServlet;
import com.twilio.sdk.verbs.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class CallServlet extends WebAppServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String dialCallStatus = request.getParameter("dialCallStatus");
        String agentId = request.getParameter("agentId");

        if (Objects.equals(dialCallStatus, "completed")) {
            respondContent(response, "");
            return;
        }

        TwiMLResponse twiMLResponse = new TwiMLResponse();

        Say say1 = new Say("It appears that no agent is available. " +
                "Please leave a message after the beep");

        say1.setLanguage("en-GB");
        say1.setVoice("alice");

        Record record = new Record();
        record.setMaxLength(20);
        record.setAction("/agents/hangup");
        record.setTranscribeCallback(String.format("/records/create?agentId=%s", agentId));

        Say say2 = new Say("No record received. Goodbye");
        say2.setLanguage("en-GB");
        say2.setVoice("alice");

        Hangup hangup = new Hangup();

        try {
            twiMLResponse.append(say1);
            twiMLResponse.append(record);
            twiMLResponse.append(say2);
            twiMLResponse.append(hangup);
        } catch (TwiMLException e) {
            e.printStackTrace();
        }

        respondTwiML(response, twiMLResponse);

    }

}
