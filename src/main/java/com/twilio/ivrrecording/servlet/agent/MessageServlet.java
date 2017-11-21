package com.twilio.ivrrecording.servlet.agent;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.twilio.ivrrecording.servlet.WebAppServlet;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Say;

public class MessageServlet extends WebAppServlet {

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Say say = new Say.Builder("Connecting you to the extraterrestrial in distress").build();

    VoiceResponse voiceResponse = new VoiceResponse.Builder().say(say).build();

    respondTwiML(response, voiceResponse);
  }
}
