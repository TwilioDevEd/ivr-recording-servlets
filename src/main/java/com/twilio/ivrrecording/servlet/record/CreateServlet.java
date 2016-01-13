package com.twilio.ivrrecording.servlet.record;

import com.twilio.ivrrecording.models.Recording;
import com.twilio.ivrrecording.repositories.RecordingRepository;
import com.twilio.ivrrecording.servlet.WebAppServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateServlet extends WebAppServlet {

    private RecordingRepository recordingRepository;

    public CreateServlet() {
        this(new RecordingRepository());
    }

    public CreateServlet(RecordingRepository recordingRepository) {
        this.recordingRepository = recordingRepository;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String agentId = request.getParameter("agentId");
        String caller = request.getParameter("caller");
        String transcriptionText = request.getParameter("transcriptionText");
        String recordingUrl = request.getParameter("recordingUrl");

        recordingRepository.create(
                new Recording(recordingUrl, transcriptionText, caller, Long.valueOf(agentId)));

        respondContent(response, "Recording saved");

    }
}
