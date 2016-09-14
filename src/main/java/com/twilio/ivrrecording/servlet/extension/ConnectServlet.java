package com.twilio.ivrrecording.servlet.extension;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.twilio.ivrrecording.models.Agent;
import com.twilio.ivrrecording.repositories.AgentRepository;
import com.twilio.ivrrecording.servlet.WebAppServlet;
import com.twilio.twiml.*;
import com.twilio.twiml.Number;

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
      redirectToMenu(response);
    } else {
      Say say = new Say.Builder("You'll be connected shortly to your planet.")
          .voice(Say.Voice.ALICE)
          .language(Say.Language.EN_GB)
          .build();

      Number number = new Number.Builder(agent.getPhoneNumber())
          .url("/agents/screen-call")
          .build();

      Dial dial = new Dial.Builder()
          .action(String.format("/agents/call?agentId=%s", agent.getId()))
          .number(number)
          .build();

      VoiceResponse voiceResponse = new VoiceResponse.Builder().say(say).dial(dial).build();

      respondTwiML(response, voiceResponse);
    }
  }

  private void redirectToMenu(HttpServletResponse response) throws IOException {
    Redirect redirect = new Redirect.Builder().url("/ivr/welcome").build();
    VoiceResponse voiceResponse = new VoiceResponse.Builder().redirect(redirect).build();

    respondTwiML(response, voiceResponse);
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
