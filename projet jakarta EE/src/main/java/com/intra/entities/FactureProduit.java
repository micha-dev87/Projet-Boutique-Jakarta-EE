package com.intra.entities;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "facture_produits")
public class FactureProduit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * La facture associée à ce produit
     */
    @ManyToOne
    @JoinColumn(name = "facture_id", nullable = false)
    private Facture facture;

    /**
     * Le produit associé à cette facture
     */
    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;

    /**
     * La quantité de produit commandé
     */
    @Column(nullable = false)
    private Integer quantite;

    // Constructeurs
    public FactureProduit() {
    }

    public FactureProduit(Facture facture, Produit produit, Integer quantite) {
        this.facture = facture;
        this.produit = produit;
        this.quantite = quantite;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }
}