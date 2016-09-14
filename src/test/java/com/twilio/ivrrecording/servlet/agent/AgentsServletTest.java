package com.twilio.ivrrecording.servlet.agent;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.twilio.ivrrecording.models.Agent;
import com.twilio.ivrrecording.repositories.AgentRepository;

import junitparams.JUnitParamsRunner;

@RunWith(JUnitParamsRunner.class)
public class AgentsServletTest {
  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  RequestDispatcher requestDispatcher;

  @Mock
  AgentRepository agentRepository;

  @Before
  public void setUp() throws IOException {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getMethod_ReturnAllAgents() throws Exception {

    List<Agent> agents = new ArrayList<Agent>();
    Agent brodo = new Agent("Brodo", "+222222222");
    Agent dagobah = new Agent("Dagobah", "+3333333333");
    agents.add(brodo);
    agents.add(dagobah);

    when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    when(agentRepository.findAll()).thenReturn(agents);

    AgentsServlet servlet = new AgentsServlet(agentRepository);
    servlet.doGet(request, response);

    verify(request).getRequestDispatcher("/agents.jsp");
    verify(request).setAttribute("agents", agents);
  }
}
