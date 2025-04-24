<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2 class="mb-4">Nos Produits</h2>
<c:if test="${not empty sessionScope.message}">
    <div class="alert alert-info">
        ${sessionScope.message}
        <c:remove var="message" scope="session"/>
    </div>
</c:if>
<div class="row">
    <c:forEach var="produit" items="${produits}">
        <div class="col-md-4 mb-4">
            <div class="card h-100">
                <div class="card-body">
                    <h5 class="card-title">${produit.nom}</h5>
                    <p class="card-text">${produit.description}</p>
                    <p class="card-text"><strong>Prix : ${produit.prix} $CAD</strong></p>
                    <form action="${pageContext.request.contextPath}/ajouterPanier" method="post">
                        <input type="hidden" name="produitId" value="${produit.id}">
                        <div class="input-group mb-3">
                            <input type="number" class="form-control" name="quantite" value="1" min="1">
                            <button type="submit" class="btn btn-primary">Ajouter au panier</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </c:forEach>
</div> 