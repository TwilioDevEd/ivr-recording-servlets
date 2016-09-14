package com.twilio.ivrrecording.repositories;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.twilio.ivrrecording.models.Agent;

public class AgentRepository extends Repository<Agent> {
  public AgentRepository() {
    super(Agent.class);
  }

  public Agent findByExtension(String extension) {
    Agent agent = null;

    try {
      EntityManager em = getEm();
      agent = (Agent) em.createQuery("SELECT e FROM Agent e WHERE e.extension = :extension")
          .setMaxResults(1).setParameter("extension", extension).getSingleResult();
      em.close();
    } catch (NoResultException ex) {
      System.out.println(ex.getMessage());
    }

    return agent;
  }
}
