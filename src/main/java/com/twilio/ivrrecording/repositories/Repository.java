package com.twilio.ivrrecording.repositories;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public abstract class Repository<T> {

    protected final Class<T> entityType;
    protected Map<String, String> properties;

    public Repository(Class<T> entity) {

        entityType = entity;

        properties = getProperties();
    }

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    @SuppressWarnings("unchecked")
    public Iterable<T> findAll() {
        EntityManager em = getEm();
        Query query = em.createQuery(
                String.format("SELECT e FROM %s e", entityType.getSimpleName()));

        Iterable<T> results = query.getResultList();
        em.close();
        return results;
    }

    /**
     * Retrieves an entity by its id.
     *
     * @param id   The given id.
     *
     * @return the entity with the given id
     */
    public T find(long id) {
        EntityManager em = getEm();
        T entity = em.find(entityType, id);
        em.refresh(entity);
        em.close();
        return entity;
    }

    /**
     * Saves a given entity. Use the returned instance for further operations.
     *
     * @param entity   The entity
     *
     * @return the saved entity
     */
    public T create(T entity) {
        EntityManager em = getEm();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.persist(entity);
        transaction.commit();
        em.close();
        return entity;
    }

    /**
     * Updates a given entity. Use the returned instance for further operations.
     *
     * @param entity   The entity
     *
     * @return the updated entity
     */
    public T update(T entity) {
        EntityManager em = getEm();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        T updatedEntity = em.merge(entity);
        transaction.commit();
        em.close();
        return updatedEntity;
    }

    /**
     * Deletes the entity.
     *
     * @param entity   The entity
     */
    public void delete(T entity) {
        EntityManager em = getEm();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.remove(entity);
        transaction.commit();
        em.close();
    }

    private Map<String, String> getProperties() {
        Map<String, String> env = System.getenv();
        Map<String, String> config = new HashMap<>();
        for (String key : env.keySet()) {
            if (key.contains("JDBC_URL")) {
                config.put("javax.persistence.jdbc.url", env.get(key));
            }

            if (key.contains("DB_USER")) {
                config.put("javax.persistence.jdbc.user", env.get(key));
            }

            if (key.contains("DB_PASSWORD")) {
                config.put("javax.persistence.jdbc.password", env.get(key));
            }
        }

        return config;
    }

    protected EntityManager getEm(){
        return Persistence
                .createEntityManagerFactory("ivr-recording-servlets", properties)
                .createEntityManager();
    }
}