package com.twilio.ivrrecording.servlet.agent;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.twilio.ivrrecording.servlet.WebAppServlet;
import com.twilio.twiml.Hangup;
import com.twilio.twiml.Record;
import com.twilio.twiml.Say;
import com.twilio.twiml.VoiceResponse;

public class CallServlet extends WebAppServlet {

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String dialCallStatus = request.getParameter("DialCallStatus");
    String agentId = request.getParameter("agentId");

    if (Objects.equals(dialCallStatus, "completed")) {
      respondContent(response, "");
      return;
    }

    Say say1 = new Say.Builder(
        "It appears that no agent is available. " + "Please leave a message after the beep")
            .language(Say.Language.EN_GB)
            .voice(Say.Voice.ALICE)
            .build();

    Record record = new Record.Builder()
        .maxLength(20)
        .action("/agents/hangup")
        .transcribeCallback(String.format("/records/create?agentId=%s", agentId))
        .build();

    Say say2 = new Say.Builder("No record received. Goodbye")
        .language(Say.Language.EN_GB)
        .voice(Say.Voice.ALICE)
        .build();

    Hangup hangup = new Hangup();

    VoiceResponse voiceResponse = new VoiceResponse.Builder()
        .say(say1)
        .record(record)
        .say(say2)
        .hangup(hangup)
        .build();

    respondTwiML(response, voiceResponse);
  }
}
