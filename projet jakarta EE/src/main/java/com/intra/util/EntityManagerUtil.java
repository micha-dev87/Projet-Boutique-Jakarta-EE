package com.intra.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Classe utilitaire pour gérer l'EntityManagerFactory et fournir des instances
 * d'EntityManager
 */
public class EntityManagerUtil {
    // Instance unique de l'EntityManagerFactory
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("boutiquePU");

    /**
     * Récupère une instance d'EntityManager
     * 
     * @return une nouvelle instance d'EntityManager
     */
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Ferme l'EntityManagerFactory si elle est ouverte
     */
    public static void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}