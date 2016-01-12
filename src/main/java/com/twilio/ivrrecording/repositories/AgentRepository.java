package com.twilio.ivrrecording.repositories;


import com.twilio.ivrrecording.models.Agent;

public class AgentRepository extends Repository<Agent> {
    public AgentRepository() {
        super(Agent.class);
    }
}
