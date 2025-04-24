package com.intra.dao;

import com.intra.entities.Client;
import com.intra.util.EntityManagerUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * DAO pour l'entité Client
 */
public class ClientDAO implements GenericDAO<Client, Long> {

    @Override
    public Client findById(Long id) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        try {
            return em.find(Client.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Récupère tous les clients
     * 
     * @return une liste de tous les clients
     */
    @Override
    public List<Client> findAll() {
        EntityManager em = EntityManagerUtil.getEntityManager();
        try {
            TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c", Client.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Sauvegarde un nouveau client
     * 
     * @param client le client à sauvegarder
     * @return le client sauvegardé
     */
    @Override
    public Client save(Client client) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(client);
            tx.commit();
            return client;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Met à jour un client existant
     * 
     * @param client le client à mettre à jour
     * @return le client mis à jour
     */
    @Override
    public Client update(Client client) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            Client updatedClient = em.merge(client);
            tx.commit();
            return updatedClient;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Supprime un client
     * 
     * @param client le client à supprimer
     */
    @Override
    public void delete(Client client) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.remove(em.contains(client) ? client : em.merge(client));
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Trouve un client par son email et mot de passe
     * 
     * @param email      l'email du client
     * @param motDePasse le mot de passe du client
     * @return le client trouvé ou null si non trouvé
     */
    public Client findByEmailAndPassword(String email, String motDePasse) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        try {
            TypedQuery<Client> query = em.createQuery(
                    "SELECT c FROM Client c WHERE c.email = :email AND c.motDePasse = :motDePasse",
                    Client.class);
            query.setParameter("email", email);
            query.setParameter("motDePasse", motDePasse);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Trouve un client par son email
     * 
     * @param email l'email du client
     * @return le client trouvé ou null si non trouvé
     */
    public Client findByEmail(String email) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        try {
            TypedQuery<Client> query = em.createQuery(
                    "SELECT c FROM Client c WHERE c.email = :email",
                    Client.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
}