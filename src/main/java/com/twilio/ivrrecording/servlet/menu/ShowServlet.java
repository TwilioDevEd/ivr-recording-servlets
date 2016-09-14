package com.twilio.ivrrecording.servlet.menu;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.twilio.ivrrecording.servlet.WebAppServlet;
import com.twilio.sdk.verbs.*;

public class ShowServlet extends WebAppServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    String selectedOption = request.getParameter("Digits");

    TwiMLResponse twiMLResponse = null;
    try {
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
    } catch (TwiMLException e) {
      e.printStackTrace();
    }

    respondTwiML(response, twiMLResponse);
  }

  private TwiMLResponse getReturnInstructions() throws TwiMLException {

    TwiMLResponse response = new TwiMLResponse();
    Say firstPhrase = new Say("To get to your extraction point, get on your bike and go down "
        + "the street. Then Left down an alley. Avoid the police cars. Turn left "
        + "into an unfinished housing development. Fly over the roadblock. Go "
        + "passed the moon. Soon after you will see your mother ship.");
    firstPhrase.setVoice("Alice");
    firstPhrase.setLanguage("en-GB");

    Say secondPhrase = new Say("Thank you for calling the ET Phone Home Service - the "
        + "adventurous alien's first choice in intergalactic travel");

    response.append(firstPhrase);
    response.append(secondPhrase);
    response.append(new Hangup());

    return response;
  }

  private TwiMLResponse getPlanets() throws TwiMLException {

    Gather gather = new Gather();
    gather.setAction("/extensions/connect");
    gather.setNumDigits(1);

    Say phrase = new Say("To call the planet Broh doe As O G, press 2. To call the planet "
        + "DuhGo bah, press 3. To call an oober asteroid to your location, press 4. To "
        + "go back to the main menu, press the star key ");
    phrase.setVoice("alice");
    phrase.setLanguage("en-GB");
    phrase.setLoop(3);

    gather.append(phrase);

    TwiMLResponse response = new TwiMLResponse();
    response.append(gather);

    return response;
  }

  private TwiMLResponse redirectWelcome() throws TwiMLException {
    TwiMLResponse twiMLResponse = new TwiMLResponse();

    twiMLResponse.append(new Redirect("/ivr/welcome"));

    return twiMLResponse;
  }
}
