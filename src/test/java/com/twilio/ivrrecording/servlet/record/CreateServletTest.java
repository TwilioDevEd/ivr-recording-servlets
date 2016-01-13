package com.twilio.ivrrecording.servlet.record;

import com.twilio.ivrrecording.models.Agent;
import com.twilio.ivrrecording.models.Recording;
import com.twilio.ivrrecording.repositories.RecordingRepository;
import com.twilio.ivrrecording.servlet.BaseTwilioServletTest;
import com.twilio.ivrrecording.servlet.extension.ConnectServlet;
import org.jdom2.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CreateServletTest extends BaseTwilioServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RecordingRepository recordingRepository;

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
        when(request.getParameter("caller")).thenReturn("caller");
        when(request.getParameter("transcriptionText")).thenReturn("transcription");
        when(request.getParameter("recordingUrl")).thenReturn("http://web.com");
        when(response.getWriter()).thenReturn(printWriter);

        CreateServlet servlet = new CreateServlet(recordingRepository);
        servlet.doPost(request, response);

        printWriter.flush();
        String content = new String(output.toByteArray(), "UTF-8");

        verify(recordingRepository).create(any(Recording.class));
        assertThat(content, is(equalTo("Recording saved")));
    }
}