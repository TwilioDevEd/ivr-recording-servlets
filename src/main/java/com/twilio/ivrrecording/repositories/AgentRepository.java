package com.twilio.ivrrecording.repositories;


import com.twilio.ivrrecording.models.Agent;

import javax.persistence.NoResultException;

public class AgentRepository extends Repository<Agent> {
    public AgentRepository() {
        super(Agent.class);
    }

    public Agent findByExtension(String extension) {

        Agent agent = null;
        try {
            agent = (Agent)
                    em.createQuery("SELECT e FROM Agent e WHERE e.extension = :extension")
                            .setMaxResults(1)
                            .setParameter("extension", extension)
                            .getSingleResult();
        } catch (NoResultException ex) {
            System.out.println(ex.getMessage());
        }

        return agent;
    }
}
