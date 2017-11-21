package com.twilio.ivrrecording.servlet.ivr;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.twilio.ivrrecording.servlet.WebAppServlet;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Gather;
import com.twilio.twiml.voice.Play;

public class WelcomeServlet extends WebAppServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    Play play = new Play.Builder("http://howtodocs.s3.amazonaws.com/et-phone.mp3")
        .loop(3)
        .build();

    Gather gather = new Gather.Builder()
        .play(play)
        .action("/menu/show")
        .numDigits(1)
        .build();

    VoiceResponse voiceResponse = new VoiceResponse.Builder().gather(gather).build();

    respondTwiML(response, voiceResponse);
  }
}
