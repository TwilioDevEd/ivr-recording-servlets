package com.twilio.ivrrecording.models;

import javax.persistence.*;

@SuppressWarnings("unused")
@Entity
@Table(name = "recordings")
public class Recording {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "url")
    private String url;

    @Column(name = "transcription")
    private String transcription;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "agent_id")
    private long agentId;

    public Recording() {
    }

    public Recording(String url, String transcription, String phoneNumber, long agentId) {
        this.url = url;
        this.transcription = transcription;
        this.phoneNumber = phoneNumber;
        this.agentId = agentId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getAgentId() {
        return agentId;
    }

    public void setAgentId(long agentId) {
        this.agentId = agentId;
    }
}
