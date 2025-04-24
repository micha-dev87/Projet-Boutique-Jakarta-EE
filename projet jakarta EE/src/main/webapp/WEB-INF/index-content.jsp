<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container text-center">
    <h1 class="display-4 mb-4">Bienvenue dans notre boutique en ligne</h1>
    
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-body">
                    <p class="lead">Découvrez notre sélection de produits de qualité</p>

                    <!-- Affichage des données de session -->
                    <c:if test="${not empty sessionScope.client}">
                        <p>Bienvenue ${sessionScope.client.nom} !</p>
                        <a href="${pageContext.request.contextPath}/produits" class="btn btn-primary btn-lg">Voir les produits</a>
                    </c:if>
                    <c:if test="${empty sessionScope.client}">
                        <p>Connectez-vous pour commencer vos achats</p>
                        <a href="${pageContext.request.contextPath}/login" class="btn btn-primary btn-lg">Se connecter</a>
                    </c:if>
                </div>
            </div>
        </div>
    </div>

    <div class="row mt-5">
        <div class="col-md-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Large sélection</h5>
                    <p class="card-text">Découvrez notre vaste catalogue de produits de qualité.</p>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Paiement sécurisé</h5>
                    <p class="card-text">Transactions sécurisées pour vos achats en toute confiance.</p>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Livraison rapide</h5>
                    <p class="card-text">Expédition rapide et suivi de commande en temps réel.</p>
                </div>
            </div>
        </div>
    </div>
</div> 