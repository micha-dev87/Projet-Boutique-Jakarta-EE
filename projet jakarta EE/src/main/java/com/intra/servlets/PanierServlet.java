package com.intra.servlets;

import com.intra.dao.ProduitDAO;
import com.intra.entities.Produit;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Servlet pour afficher le panier
 */
@WebServlet("/panier-controller")
public class PanierServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProduitDAO produitDAO;

    @Override
    public void init() throws ServletException {
        produitDAO = new ProduitDAO();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupération de la session
        HttpSession session = request.getSession();

        // Vérification si l'utilisateur est connecté
        if (session.getAttribute("client") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Récupération du panier
        @SuppressWarnings("unchecked")
        Map<Produit, Integer> panier = (Map<Produit, Integer>) session.getAttribute("panier");

        if (panier == null || panier.isEmpty()) {
            request.setAttribute("panier", null);
            request.setAttribute("produits", new ArrayList<Produit>());
        } else {
            // Calcul du total
            double total = 0;
            for (Map.Entry<Produit, Integer> entry : panier.entrySet()) {
                total += entry.getKey().getPrix() * entry.getValue();
            }

            request.setAttribute("panier", panier);
            request.setAttribute("produits", new ArrayList<>(panier.keySet()));
            request.setAttribute("total", total);
        }

        // Forward vers la JSP
        request.getRequestDispatcher("/WEB-INF/panier.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        if (produitDAO != null) {
            produitDAO = null;
        }
    }
}