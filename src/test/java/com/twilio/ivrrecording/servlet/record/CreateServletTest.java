package com.twilio.ivrrecording.servlet.record;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.twilio.ivrrecording.models.Agent;
import com.twilio.ivrrecording.models.Recording;
import com.twilio.ivrrecording.repositories.AgentRepository;
import com.twilio.ivrrecording.repositories.RecordingRepository;
import com.twilio.ivrrecording.servlet.BaseTwilioServletTest;

public class CreateServletTest extends BaseTwilioServletTest {

  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  RecordingRepository recordingRepository;

  @Mock
  AgentRepository agentRepository;

  @Before
  public void setUp() throws IOException {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void postMethod_WhenAnAgentIsFound_ThenRespondsByDialingTheAgentsPhoneNumber()
      throws Exception {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    PrintWriter printWriter = new PrintWriter(output);

    when(request.getParameter("agentId")).thenReturn("1");
    when(request.getParameter("Caller")).thenReturn("+23334444");
    when(request.getParameter("TranscriptionText")).thenReturn("transcription");
    when(request.getParameter("RecordingUrl")).thenReturn("http://web.com");
    when(response.getWriter()).thenReturn(printWriter);
    when(agentRepository.find(any(Long.class))).thenReturn(new Agent(1, "Brodo", "+1223334"));

    CreateServlet servlet = new CreateServlet(recordingRepository, agentRepository);
    servlet.doPost(request, response);

    printWriter.flush();
    String content = new String(output.toByteArray(), "UTF-8");

    verify(recordingRepository).create(any(Recording.class));
    assertThat(content, is(equalTo("Recording saved")));
  }
}
