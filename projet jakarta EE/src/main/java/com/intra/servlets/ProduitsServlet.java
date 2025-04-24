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
import java.util.List;

@WebServlet("/produits")
public class ProduitsServlet extends HttpServlet {
    private ProduitDAO produitDAO;

    @Override
    public void init() throws ServletException {
        produitDAO = new ProduitDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("client") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String recherche = request.getParameter("recherche");
        String prixMin = request.getParameter("prixMin");
        String prixMax = request.getParameter("prixMax");

        List<Produit> produits;
        if (recherche != null && !recherche.isEmpty()) {
            produits = produitDAO.findByNom(recherche);
        } else if (prixMin != null && prixMax != null && !prixMin.isEmpty() && !prixMax.isEmpty()) {
            produits = produitDAO.findByPrixRange(Double.parseDouble(prixMin), Double.parseDouble(prixMax));
        } else {
            produits = produitDAO.findAll();
        }

        request.setAttribute("produits", produits);
        request.getRequestDispatcher("/WEB-INF/produits.jsp").forward(request, response);
    }
}