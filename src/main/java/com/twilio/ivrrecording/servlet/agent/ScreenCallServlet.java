package com.twilio.ivrrecording.servlet.agent;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.twilio.ivrrecording.servlet.WebAppServlet;
import com.twilio.twiml.voice.Gather;
import com.twilio.twiml.voice.Hangup;
import com.twilio.twiml.voice.Say;
import com.twilio.twiml.VoiceResponse;

public class ScreenCallServlet extends WebAppServlet {

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String from = request.getParameter("From");

    String incomingCallMessage = "You have an incoming call from: " + getSpelledPhoneNumber(from);
    String gatherMessage = incomingCallMessage + ".Press any key to accept";

    Say sayInGather = new Say.Builder(gatherMessage).build();
    Gather gather = new Gather.Builder()
        .numDigits(1)
        .action("/agents/message")
        .say(sayInGather)
        .build();

    Say say = new Say.Builder("Sorry. Did not get your response").build();
    Hangup hangup = new Hangup.Builder();

    VoiceResponse voiceResponse = new VoiceResponse.Builder()
        .gather(gather)
        .say(say)
        .hangup(hangup)
        .build();

    respondTwiML(response, voiceResponse);
  }

  private String getSpelledPhoneNumber(String phoneNumber) {
    return String.join(", ", phoneNumber.split(""));
  }
}
