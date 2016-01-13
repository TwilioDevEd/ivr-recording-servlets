package com.twilio.ivrrecording.repositories;

import com.twilio.ivrrecording.models.Recording;

public class RecordingRepository extends Repository<Recording> {

    public RecordingRepository() {
        super(Recording.class);
    }
}
