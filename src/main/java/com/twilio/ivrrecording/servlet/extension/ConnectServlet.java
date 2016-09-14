package com.twilio.ivrrecording.servlet.extension;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.twilio.ivrrecording.models.Agent;
import com.twilio.ivrrecording.repositories.AgentRepository;
import com.twilio.ivrrecording.servlet.WebAppServlet;
import com.twilio.sdk.verbs.*;
import com.twilio.sdk.verbs.Number;

public class ConnectServlet extends WebAppServlet {

  private AgentRepository agentRepository;

  public ConnectServlet() {
    this(new AgentRepository());
  }

  public ConnectServlet(AgentRepository agentRepository) {
    this.agentRepository = agentRepository;
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    String selectedOption = request.getParameter("Digits");

    Agent agent = findAgentByExtension(selectedOption);
    if (agent == null) {
      try {
        redirectToMenu(response);
      } catch (TwiMLException e) {
        e.printStackTrace();
      }
    } else {
      TwiMLResponse twiMLResponse = new TwiMLResponse();
      Say say = new Say("You'll be connected shortly to your planet.");
      say.setVoice("alice");
      say.setLanguage("en-GB");

      Dial dial = new Dial();
      dial.setAction(String.format("/agents/call?agentId=%s", agent.getId()));

      Number number = new Number(agent.getPhoneNumber());
      number.setUrl("/agents/screen-call");

      try {
        dial.append(number);
        twiMLResponse.append(dial);
      } catch (TwiMLException e) {
        e.printStackTrace();
      }
      respondTwiML(response, twiMLResponse);
    }
  }

  private void redirectToMenu(HttpServletResponse response) throws TwiMLException, IOException {
    TwiMLResponse twiMLResponse = new TwiMLResponse();
    twiMLResponse.append(new com.twilio.sdk.verbs.Redirect("/ivr/welcome"));

    respondTwiML(response, twiMLResponse);
  }

  private Agent findAgentByExtension(String extension) {
    Map<String, String> planetsExtensiosn = new HashMap<>();
    planetsExtensiosn.put("2", "Brodo");
    planetsExtensiosn.put("3", "Dagobah");
    planetsExtensiosn.put("4", "Oober");


    return planetsExtensiosn.containsKey(extension)
        ? agentRepository.findByExtension(planetsExtensiosn.get(extension)) : null;
  }
}
