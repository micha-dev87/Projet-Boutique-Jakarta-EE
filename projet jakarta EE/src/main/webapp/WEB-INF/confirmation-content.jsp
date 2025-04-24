<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card border-success">
                <div class="card-header bg-success text-white text-center">
                    <h3 class="mb-0">
                        <i class="fas fa-check-circle"></i> Commande confirmée
                    </h3>
                </div>
                <div class="card-body">
                    <div class="text-center mb-4">
                        <h4 class="text-success">Merci pour votre commande !</h4>
                        <p class="text-muted">Votre commande a été enregistrée avec succès.</p>
                    </div>

                    <div class="row mb-4">
                        <div class="col-md-12">
                            <h5>Détails de la facture</h5>
                            <p><strong>Numéro de facture :</strong> ${facture.id}</p>
                            <p><strong>Date :</strong> <fmt:formatDate value="${facture.dateFacture}" pattern="dd/MM/yyyy HH:mm"/></p>
                            <p><strong>Client :</strong> ${facture.client.nom} </p>
                        </div>
  
                        <div class="col-md-12">
                            <p><strong>Total général :</strong> <fmt:formatNumber value="${total}" type="currency" currencySymbol="$CAD"/></p>
                        </div>
                    </div>

                    <div class="table-responsive mb-4">
                        <table class="table table-striped table-hover">
                            <thead>
                                <tr>
                                    <th>Produit</th>
                                    <th>Quantité</th>
                                    <th>Prix unitaire</th>
                                    <th>Total</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${facture.factureProduits}" var="ligne">
                                    <tr>
                                        <td>${ligne.produit.nom}</td>
                                        <td>${ligne.quantite}</td>
                                        <td><fmt:formatNumber value="${ligne.produit.prix}" type="currency" currencySymbol="$CAD"/></td>
                                            <td><fmt:formatNumber value="${ligne.produit.prix * ligne.quantite}" type="currency" currencySymbol="$CAD"/></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <div class="text-center">
                        <a href="${pageContext.request.contextPath}/" class="btn btn-primary me-2">
                            <i class="fas fa-home"></i> Retour à l'accueil
                        </a>
                        <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-danger">
                            <i class="fas fa-sign-out-alt"></i> Déconnexion
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div> 