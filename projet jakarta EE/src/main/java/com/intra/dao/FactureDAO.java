package com.intra.dao;

import com.intra.entities.Client;
import com.intra.entities.Facture;
import com.intra.util.EntityManagerUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * DAO pour l'entité Facture
 */
public class FactureDAO implements GenericDAO<Facture, Long> {

    /**
     * Trouve une facture par son identifiant
     * 
     * @param id l'identifiant de la facture
     * @return la facture trouvée ou null si non trouvée
     */
    @Override
    public Facture findById(Long id) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        try {
            return em.find(Facture.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Récupère toutes les factures
     * 
     * @return une liste de toutes les factures
     */
    @Override
    public List<Facture> findAll() {
        EntityManager em = EntityManagerUtil.getEntityManager();
        try {
            TypedQuery<Facture> query = em.createQuery("SELECT f FROM Facture f", Facture.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Sauvegarde une nouvelle facture
     * 
     * @param facture la facture à sauvegarder
     * @return la facture sauvegardée
     */
    @Override
    public Facture save(Facture facture) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(facture);
            tx.commit();
            return facture;
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
     * Met à jour une facture existante
     * 
     * @param facture la facture à mettre à jour
     * @return la facture mise à jour
     */
    @Override
    public Facture update(Facture facture) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            Facture updatedFacture = em.merge(facture);
            tx.commit();
            return updatedFacture;
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
     * Supprime une facture
     * 
     * @param facture la facture à supprimer
     */
    @Override
    public void delete(Facture facture) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.remove(em.contains(facture) ? facture : em.merge(facture));
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
     * Trouve les factures d'un client
     * 
     * @param client le client dont on veut les factures
     * @return une liste des factures du client
     */
    public List<Facture> findByClient(Client client) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        try {
            TypedQuery<Facture> query = em.createQuery(
                    "SELECT f FROM Facture f WHERE f.client = :client ORDER BY f.dateFacture DESC",
                    Facture.class);
            query.setParameter("client", client);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Calcule le total d'une facture
     * 
     * @param factureId l'identifiant de la facture
     * @return le total de la facture
     */
    public Double getTotalFacture(Long factureId) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        try {
            TypedQuery<Double> query = em.createQuery(
                    "SELECT SUM(fp.produit.prix * fp.quantite) FROM FactureProduit fp WHERE fp.facture.id = :factureId",
                    Double.class);
            query.setParameter("factureId", factureId);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}