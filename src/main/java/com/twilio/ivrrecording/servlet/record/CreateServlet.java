package com.twilio.ivrrecording.servlet.record;

import com.twilio.ivrrecording.models.Agent;
import com.twilio.ivrrecording.models.Recording;
import com.twilio.ivrrecording.repositories.AgentRepository;
import com.twilio.ivrrecording.repositories.RecordingRepository;
import com.twilio.ivrrecording.servlet.WebAppServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateServlet extends WebAppServlet {

    private RecordingRepository recordingRepository;
    private AgentRepository agentRepository;

    public CreateServlet() {
        this(new RecordingRepository(), new AgentRepository());
    }

    public CreateServlet(RecordingRepository recordingRepository, AgentRepository agentRepository) {
        this.recordingRepository = recordingRepository;
        this.agentRepository = agentRepository;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String agentId = request.getParameter("agentId");
        String caller = request.getParameter("Caller");
        String transcriptionText = request.getParameter("TranscriptionText");
        String recordingUrl = request.getParameter("RecordingUrl");

        Agent agent = agentRepository.find(Long.valueOf(agentId));

        recordingRepository.create(
                new Recording(recordingUrl, transcriptionText, caller, agent));

        respondContent(response, "Recording saved");

    }
}
