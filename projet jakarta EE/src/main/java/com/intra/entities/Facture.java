package com.intra.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "factures")
public class Facture implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "date_facture", nullable = false)


    private Date dateFacture;

    @OneToMany(mappedBy = "facture", cascade = CascadeType.ALL)
    private List<FactureProduit> factureProduits;

    // Constructeurs
    public Facture() {
        this.dateFacture = new Date();
    }

    public Facture(Client client) {
        this.client = client;
        this.dateFacture = new Date();
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(Date dateFacture) {
        this.dateFacture = dateFacture;
    }

    public List<FactureProduit> getFactureProduits() {
        return factureProduits;
    }

    public void setFactureProduits(List<FactureProduit> factureProduits) {
        this.factureProduits = factureProduits;
    }
}