<%@ page language="java" contentType="text/html; charset=windows-1256"
         pageEncoding="windows-1256"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="windows-1256">
    <title>Liste Produits</title>
    <!-- Add Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1 class="my-4">Liste des Produits</h1>
    <table class="table">
        <thead>
        <tr>
            <th>ID</th><th>Nom Produit</th><th>Prix</th><th>Date Création</th><th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${produits}" var="p">
            <tr>
                <td>${p.idProduit }</td>
                <td>${p.nomProduit }</td>
                <td>${p.prixProduit }</td>
                <td><fmt:formatDate pattern="dd/MM/yyyy" value="${p.dateCreation}" /></td>
                <td>
                    <a class="btn btn-danger" onclick="return confirm('Etes-vous sûr ?')"
                       href="supprimerProduit?id=${p.idProduit }">Supprimer</a>
                    <a class="btn btn-info" href="modifierProduit?id=${p.idProduit }">Modifier</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- Add Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
</body>
</html>
