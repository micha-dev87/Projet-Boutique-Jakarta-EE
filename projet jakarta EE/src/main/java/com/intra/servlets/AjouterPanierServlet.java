package com.intra.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.intra.entities.Produit;
import com.intra.dao.ProduitDAO;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet pour ajouter un produit au panier
 */
@WebServlet("/ajouterPanier")
public class AjouterPanierServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProduitDAO produitDAO;

    @Override
    public void init() throws ServletException {
        produitDAO = new ProduitDAO();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupération de la session
        HttpSession session = request.getSession();

        // Vérification si l'utilisateur est connecté
        if (session.getAttribute("client") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Récupération des paramètres
        String produitIdStr = request.getParameter("produitId");
        String quantiteStr = request.getParameter("quantite");

        if (produitIdStr == null || quantiteStr == null) {
            session.setAttribute("message", "Erreur : paramètres manquants");
            response.sendRedirect(request.getContextPath() + "/produits");
            return;
        }

        try {
            Long produitId = Long.parseLong(produitIdStr);
            int quantite = Integer.parseInt(quantiteStr);

            // Récupération du produit
            Produit produit = produitDAO.findById(produitId);
            if (produit == null) {
                session.setAttribute("message", "Erreur : produit non trouvé");
                response.sendRedirect(request.getContextPath() + "/produits");
                return;
            }

            // Récupération ou création du panier
            @SuppressWarnings("unchecked")
            Map<Produit, Integer> panier = (Map<Produit, Integer>) session.getAttribute("panier");
            if (panier == null) {
                panier = new HashMap<>();
            }

            // Ajout ou mise à jour de la quantité
            int quantiteActuelle = panier.getOrDefault(produit, 0);
            panier.put(produit, quantiteActuelle + quantite);

            // Mise à jour du panier en session
            session.setAttribute("panier", panier);

            // Message de confirmation
            session.setAttribute("message", "Le produit " + produit.getNom() + " a été ajouté au panier avec succès");

            // Redirection vers la page des produits
            response.sendRedirect(request.getContextPath() + "/produits");

        } catch (NumberFormatException e) {
            session.setAttribute("message", "Erreur : format de quantité invalide");
            response.sendRedirect(request.getContextPath() + "/produits");
        }
    }
}