package com.intra.dao;

import com.intra.entities.Produit;
import com.intra.util.EntityManagerUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * DAO pour l'entité Produit
 */
public class ProduitDAO implements GenericDAO<Produit, Long> {

    /**
     * Trouve un produit par son identifiant
     * 
     * @param id l'identifiant du produit
     * @return le produit trouvé ou null si non trouvé
     */
    @Override
    public Produit findById(Long id) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        try {
            return em.find(Produit.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Récupère tous les produits
     * 
     * @return une liste de tous les produits
     */
    @Override
    public List<Produit> findAll() {
        EntityManager em = EntityManagerUtil.getEntityManager();
        try {
            TypedQuery<Produit> query = em.createQuery("SELECT p FROM Produit p", Produit.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Sauvegarde un nouveau produit
     * 
     * @param produit le produit à sauvegarder
     * @return le produit sauvegardé
     */
    @Override
    public Produit save(Produit produit) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(produit);
            tx.commit();
            return produit;
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
     * Met à jour un produit existant
     * 
     * @param produit le produit à mettre à jour
     * @return le produit mis à jour
     */
    @Override
    public Produit update(Produit produit) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            Produit updatedProduit = em.merge(produit);
            tx.commit();
            return updatedProduit;
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
     * Supprime un produit
     * 
     * @param produit le produit à supprimer
     */
    @Override
    public void delete(Produit produit) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.remove(em.contains(produit) ? produit : em.merge(produit));
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
     * Trouve des produits par leur nom (recherche partielle)
     * 
     * @param nom le nom ou une partie du nom à rechercher
     * @return une liste de produits correspondants
     */
    public List<Produit> findByNom(String nom) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        try {
            TypedQuery<Produit> query = em.createQuery(
                    "SELECT p FROM Produit p WHERE p.nom LIKE :nom",
                    Produit.class);
            query.setParameter("nom", "%" + nom + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Trouve des produits dans une fourchette de prix
     * 
     * @param prixMin le prix minimum
     * @param prixMax le prix maximum
     * @return une liste de produits dans la fourchette de prix
     */
    public List<Produit> findByPrixRange(Double prixMin, Double prixMax) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        try {
            TypedQuery<Produit> query = em.createQuery(
                    "SELECT p FROM Produit p WHERE p.prix BETWEEN :prixMin AND :prixMax",
                    Produit.class);
            query.setParameter("prixMin", prixMin);
            query.setParameter("prixMax", prixMax);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}