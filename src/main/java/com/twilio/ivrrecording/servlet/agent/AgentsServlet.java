package com.twilio.ivrrecording.servlet.agent;

import com.twilio.ivrrecording.models.Agent;
import com.twilio.ivrrecording.repositories.AgentRepository;
import com.twilio.ivrrecording.servlet.WebAppServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("unused")
public class AgentsServlet extends WebAppServlet {

    private AgentRepository agentRepository;

    public AgentsServlet() {
        this(new AgentRepository());
    }

    public AgentsServlet(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Iterable<Agent> agents = agentRepository.findAll();

        request.setAttribute("agents", agents);
        request.getRequestDispatcher("/agents.jsp").forward(request, response);

    }

}
