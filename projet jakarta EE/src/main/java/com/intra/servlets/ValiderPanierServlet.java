package com.intra.servlets;

import com.intra.dao.FactureDAO;
import com.intra.dao.FactureProduitDAO;
import com.intra.dao.ProduitDAO;
import com.intra.entities.Client;
import com.intra.entities.Facture;
import com.intra.entities.FactureProduit;
import com.intra.entities.Produit;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@WebServlet("/validerPanier")
public class ValiderPanierServlet extends HttpServlet {
    private FactureDAO factureDAO;
    private FactureProduitDAO factureProduitDAO;
    private ProduitDAO produitDAO;

    @Override
    public void init() throws ServletException {
        factureDAO = new FactureDAO();
        factureProduitDAO = new FactureProduitDAO();
        produitDAO = new ProduitDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Client client = (Client) session.getAttribute("client");
        if (client == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        @SuppressWarnings("unchecked")
        Map<Long, Integer> panier = (Map<Long, Integer>) session.getAttribute("panier");
        if (panier == null || panier.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/panier");
            return;
        }

        try {
            // Créer la facture
            Facture facture = new Facture();
            facture.setClient(client);
            facture.setDateFacture(new Date());
            facture = factureDAO.save(facture);

            // Ajouter les produits à la facture
            for (Map.Entry<Long, Integer> entry : panier.entrySet()) {
                Produit produit = produitDAO.findById(entry.getKey());
                if (produit != null) {
                    FactureProduit factureProduit = new FactureProduit();
                    factureProduit.setFacture(facture);
                    factureProduit.setProduit(produit);
                    factureProduit.setQuantite(entry.getValue());
                    factureProduitDAO.save(factureProduit);
                }
            }

            // Vider le panier
            session.removeAttribute("panier");

            // Rediriger vers la page de confirmation
            response.sendRedirect(request.getContextPath() + "/confirmation");
        } catch (Exception e) {
            request.setAttribute("error", "Une erreur est survenue lors de la validation du panier");
            request.getRequestDispatcher("/WEB-INF/panier.jsp").forward(request, response);
        }
    }
}