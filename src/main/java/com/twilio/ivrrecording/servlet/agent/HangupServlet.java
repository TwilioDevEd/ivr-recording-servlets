package com.twilio.ivrrecording.servlet.agent;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.twilio.ivrrecording.servlet.WebAppServlet;
import com.twilio.twiml.Hangup;
import com.twilio.twiml.Say;
import com.twilio.twiml.VoiceResponse;

public class HangupServlet extends WebAppServlet {

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Say say = new Say.Builder("Thanks for your message. Goodbye")
        .language(Say.Language.EN_GB)
        .voice(Say.Voice.ALICE)
        .build();

    Hangup hangup = new Hangup();

    VoiceResponse voiceResponse = new VoiceResponse.Builder().say(say).hangup(hangup).build();

    respondTwiML(response, voiceResponse);
  }
}
