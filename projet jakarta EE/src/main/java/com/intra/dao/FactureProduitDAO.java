package com.intra.dao;

import com.intra.entities.Facture;
import com.intra.entities.FactureProduit;
import com.intra.entities.Produit;
import com.intra.util.EntityManagerUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * DAO pour l'entité FactureProduit
 */
public class FactureProduitDAO implements GenericDAO<FactureProduit, Long> {

    /**
     * Trouve une ligne de facture par son identifiant
     * 
     * @param id l'identifiant de la ligne de facture
     * @return la ligne de facture trouvée ou null si non trouvée
     */
    @Override
    public FactureProduit findById(Long id) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        try {
            return em.find(FactureProduit.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Récupère toutes les lignes de facture
     * 
     * @return une liste de toutes les lignes de facture
     */
    @Override
    public List<FactureProduit> findAll() {
        EntityManager em = EntityManagerUtil.getEntityManager();
        try {
            TypedQuery<FactureProduit> query = em.createQuery("SELECT fp FROM FactureProduit fp", FactureProduit.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Sauvegarde une nouvelle ligne de facture
     * 
     * @param factureProduit la ligne de facture à sauvegarder
     * @return la ligne de facture sauvegardée
     */
    @Override
    public FactureProduit save(FactureProduit factureProduit) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(factureProduit);
            tx.commit();
            return factureProduit;
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
     * Met à jour une ligne de facture existante
     * 
     * @param factureProduit la ligne de facture à mettre à jour
     * @return la ligne de facture mise à jour
     */
    @Override
    public FactureProduit update(FactureProduit factureProduit) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            FactureProduit updatedFactureProduit = em.merge(factureProduit);
            tx.commit();
            return updatedFactureProduit;
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
     * Supprime une ligne de facture
     * 
     * @param factureProduit la ligne de facture à supprimer
     */
    @Override
    public void delete(FactureProduit factureProduit) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.remove(em.contains(factureProduit) ? factureProduit : em.merge(factureProduit));
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
     * Trouve les lignes de facture d'une facture
     * 
     * @param facture la facture dont on veut les lignes
     * @return une liste des lignes de la facture
     */
    public List<FactureProduit> findByFacture(Facture facture) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        try {
            TypedQuery<FactureProduit> query = em.createQuery(
                    "SELECT fp FROM FactureProduit fp WHERE fp.facture = :facture",
                    FactureProduit.class);
            query.setParameter("facture", facture);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Trouve les lignes de facture contenant un produit
     * 
     * @param produit le produit à rechercher
     * @return une liste des lignes de facture contenant le produit
     */
    public List<FactureProduit> findByProduit(Produit produit) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        try {
            TypedQuery<FactureProduit> query = em.createQuery(
                    "SELECT fp FROM FactureProduit fp WHERE fp.produit = :produit",
                    FactureProduit.class);
            query.setParameter("produit", produit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}