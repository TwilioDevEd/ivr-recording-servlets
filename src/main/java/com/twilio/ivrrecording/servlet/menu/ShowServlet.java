package com.twilio.ivrrecording.servlet.menu;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.twilio.ivrrecording.servlet.WebAppServlet;
import com.twilio.twiml.*;

public class ShowServlet extends WebAppServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    String selectedOption = request.getParameter("Digits");

    VoiceResponse twiMLResponse = null;

    switch (selectedOption) {
      case "1":
        twiMLResponse = getReturnInstructions();
        break;
      case "2":
        twiMLResponse = getPlanets();
        break;
      default:
        twiMLResponse = redirectWelcome();
    }

    respondTwiML(response, twiMLResponse);
  }

  private VoiceResponse getReturnInstructions() {
    Say firstPhrase =
        new Say.Builder("To get to your extraction point, get on your bike and go down "
            + "the street. Then Left down an alley. Avoid the police cars. Turn left "
            + "into an unfinished housing development. Fly over the roadblock. Go "
            + "passed the moon. Soon after you will see your mother ship.").voice(Say.Voice.POLLY_AMY)
                .language(Say.Language.EN_GB).build();

    Say secondPhrase = new Say.Builder("Thank you for calling the ET Phone Home Service - the "
        + "adventurous alien's first choice in intergalactic travel").build();

    Hangup hangup = new Hangup();

    VoiceResponse voiceResponse =
        new VoiceResponse.Builder().say(firstPhrase).say(secondPhrase).hangup(hangup).build();

    return voiceResponse;
  }

  private VoiceResponse getPlanets() {
    Say phrase = new Say.Builder("To call the planet Broh doe As O G, press 2. To call the planet "
        + "DuhGo bah, press 3. To call an oober asteroid to your location, press 4. To "
        + "go back to the main menu, press the star key ")
            .voice(Say.Voice.POLLY_AMY)
            .language(Say.Language.EN_GB)
            .loop(3)
            .build();

    Gather gather = new Gather.Builder()
        .say(phrase)
        .action("/extensions/connect")
        .numDigits(1)
        .build();

    VoiceResponse voiceResponse = new VoiceResponse.Builder().gather(gather).build();

    return voiceResponse;
  }

  private VoiceResponse redirectWelcome() {
    Redirect redirect = new Redirect.Builder().url("/ivr/welcome").build();

    VoiceResponse voiceResponse= new VoiceResponse.Builder().redirect(redirect).build();

    return voiceResponse;
  }
}
