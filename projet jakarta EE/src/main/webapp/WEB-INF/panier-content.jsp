<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h2 class="mb-4">Votre Panier</h2>

<c:if test="${not empty sessionScope.message}">
    <div class="alert alert-info">
        ${sessionScope.message}
        <c:remove var="message" scope="session"/>
    </div>
</c:if>

<c:choose>
    <c:when test="${empty panier}">
        <div class="alert alert-info">
            Votre panier est vide.
        </div>
    </c:when>
    <c:otherwise>
        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Produit</th>
                        <th>Prix unitaire</th>
                        <th>Quantité</th>
                        <th>Total</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="totalGeneral" value="0" />
                    <c:if test="${not empty produits}">
                    <c:forEach var="produit" items="${produits}">
                        <c:set var="quantite" value="${panier[produit]}" />
                        <c:set var="totalProduit" value="${produit.prix * quantite}" />
                        <c:set var="totalGeneral" value="${totalGeneral + totalProduit}" />
                        <tr>
                            <td>${produit.nom}</td>
                            <td><fmt:formatNumber value="${produit.prix}" type="currency" currencyCode="CAD" /></td>
                            <td>${quantite}</td>
                            <td><fmt:formatNumber value="${totalProduit}" type="currency" currencyCode="CAD" /></td>
                            <td>
                                <form action="${pageContext.request.contextPath}/supprimerProduitPanier" method="post" class="d-inline">
                                    <input type="hidden" name="produitId" value="${produit.id}">
                                    <button type="submit" class="btn btn-danger btn-sm">Supprimer</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </c:if>
                    <c:if test="${empty produits}">
                        <tr>
                            <td colspan="5" class="text-center">Aucun produit disponible</td>
                        </tr>
                    </c:if>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="3" class="text-end"><strong>Total général :</strong></td>
                        <td colspan="2"><strong><fmt:formatNumber value="${totalGeneral}" type="currency" currencyCode="CAD" /></strong></td>
                    </tr>
                </tfoot>
            </table>
        </div>
        <div class="text-end">
           <a href="${pageContext.request.contextPath}/confirmation" class="btn btn-success">Valider la commande</a>
        </div>
    </c:otherwise>
</c:choose> 