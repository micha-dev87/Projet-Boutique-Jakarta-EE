package com.intra.servlets;

import com.intra.dao.FactureDAO;
import com.intra.entities.Facture;
import com.intra.entities.Produit;
import com.intra.entities.Client;
import com.intra.entities.FactureProduit;
import com.intra.dao.ProduitDAO;
import com.intra.dao.FactureProduitDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import java.util.List;
import java.io.IOException;

/**
 * Servlet pour l'affichage de la page de confirmation de commande
 */
@WebServlet("/confirmation")
public class ConfirmationServlet extends HttpServlet {
    private FactureDAO factureDAO;
    private ProduitDAO produitDAO;
    private FactureProduitDAO factureProduitDAO;

    /**
     * Initialise le DAO nécessaire
     * 
     * @throws ServletException en cas d'erreur d'initialisation
     */
    @Override
    public void init() throws ServletException {
        factureDAO = new FactureDAO();
        produitDAO = new ProduitDAO();
        factureProduitDAO = new FactureProduitDAO();
    }

    /**
     * Affiche la page de confirmation avec les détails de la facture
     * 
     * @param request  la requête HTTP
     * @param response la réponse HTTP
     * @throws ServletException en cas d'erreur de traitement
     * @throws IOException      en cas d'erreur d'entrée/sortie
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Vérification de la session utilisateur
        HttpSession session = request.getSession();
        if (session.getAttribute("client") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Récupération du panier
        @SuppressWarnings("unchecked")
        Map<Produit, Integer> panier = (Map<Produit, Integer>) session.getAttribute("panier");

        if (panier == null || panier.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/produits");
            return;
        }
        // création de la facture pour ce client
        Facture facture = new Facture();
        facture.setClient((Client) session.getAttribute("client"));
        facture = factureDAO.save(facture);

        // enregistrement des produits dans la facture
        for (Map.Entry<Produit, Integer> entry : panier.entrySet()) {
            Produit produit = entry.getKey();
            FactureProduit factureProduit = new FactureProduit(facture, produit, entry.getValue());
            factureProduitDAO.save(factureProduit);
        }



        // vider le panier
        panier.clear();
        // ajout les produits dans la facture
        facture.setFactureProduits(factureProduitDAO.findByFacture(facture));
      

        // total
        double total = factureDAO.getTotalFacture(facture.getId());
      
        // Récupération des données pour la page de confirmation
        request.setAttribute("facture", facture);
        request.setAttribute("total", total);
   

        request.getRequestDispatcher("/WEB-INF/confirmation.jsp").forward(request, response);



    }
}