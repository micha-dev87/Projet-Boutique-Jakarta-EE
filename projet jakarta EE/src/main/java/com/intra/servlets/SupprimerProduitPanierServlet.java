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
import java.util.Iterator;
import java.util.Map;

/**
 * Servlet pour supprimer un produit du panier
 */
@WebServlet("/supprimerProduitPanier")
public class SupprimerProduitPanierServlet extends HttpServlet {
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

        // Récupération de l'ID du produit à supprimer
        String produitIdStr = request.getParameter("produitId");
        if (produitIdStr == null || produitIdStr.isEmpty()) {
            session.setAttribute("message", "Erreur : ID du produit manquant");
            response.sendRedirect(request.getContextPath() + "/panier");
            return;
        }

        try {
            Long produitId = Long.parseLong(produitIdStr);

            // Récupération du panier
            @SuppressWarnings("unchecked")
            Map<Produit, Integer> panier = (Map<Produit, Integer>) session.getAttribute("panier");

            if (panier == null || panier.isEmpty()) {
                session.setAttribute("message", "Erreur : panier vide");
                response.sendRedirect(request.getContextPath() + "/panier-controller");
                return;
            }

            // Recherche et suppression du produit par son ID
            Iterator<Map.Entry<Produit, Integer>> iterator = panier.entrySet().iterator();
            Produit produitASupprimer = null;

            while (iterator.hasNext()) {
                Map.Entry<Produit, Integer> entry = iterator.next();
                if (entry.getKey().getId().equals(produitId)) {
                    produitASupprimer = entry.getKey();
                    iterator.remove();
                    break;
                }
            }

            if (produitASupprimer == null) {
                session.setAttribute("message", "Erreur : produit non présent dans le panier");
                response.sendRedirect(request.getContextPath() + "/panier-controller");
                return;
            }

            // Mise à jour du panier en session
            session.setAttribute("panier", panier);

            // Message de confirmation
            session.setAttribute("message", "Le produit " + produitASupprimer.getNom() + " a été supprimé du panier");

            // Redirection vers la page du panier
            response.sendRedirect(request.getContextPath() + "/panier-controller");

        } catch (NumberFormatException e) {
            session.setAttribute("message", "Erreur : format d'ID invalide");
            response.sendRedirect(request.getContextPath() + "/panier-controller");
        }
    }
}