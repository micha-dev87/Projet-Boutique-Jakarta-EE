package com.intra.dao;

import java.util.List;

/**
 * Interface générique pour les opérations CRUD de base
 * 
 * @param <T>  le type d'entité
 * @param <ID> le type de l'identifiant
 */
public interface GenericDAO<T, ID> {
    /**
     * Trouve une entité par son identifiant
     * 
     * @param id l'identifiant de l'entité
     * @return l'entité trouvée ou null si non trouvée
     */
    T findById(ID id);

    /**
     * Récupère toutes les entités
     * 
     * @return une liste de toutes les entités
     */
    List<T> findAll();

    /**
     * Sauvegarde une nouvelle entité
     * 
     * @param entity l'entité à sauvegarder
     * @return l'entité sauvegardée
     */
    T save(T entity);

    /**
     * Met à jour une entité existante
     * 
     * @param entity l'entité à mettre à jour
     * @return l'entité mise à jour
     */
    T update(T entity);

    /**
     * Supprime une entité
     * 
     * @param entity l'entité à supprimer
     */
    void delete(T entity);
}