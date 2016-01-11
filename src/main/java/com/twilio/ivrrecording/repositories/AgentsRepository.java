package com.twilio.ivrrecording.repositories;


import com.twilio.ivrrecording.models.Agent;

public class AgentsRepository extends Repository<Agent> {
    public AgentsRepository(Class<Agent> entity) {
        super(entity);
    }
}
