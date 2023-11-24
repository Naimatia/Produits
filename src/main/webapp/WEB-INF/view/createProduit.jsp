<%@ page language="java" contentType="text/html; charset=windows-1256" pageEncoding="windows-1256"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="windows-1256">
    <title>Créer un Produit</title>
    <!-- Add Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1 class="my-4">Créer un Produit</h1>
    <form action="saveProduit" method="post">
        <div class="form-group">
            <label for="nomProduit">Nom :</label>
            <input type="text" class="form-control" id="nomProduit" name="nomProduit" required>
        </div>
        <div class="form-group">
            <label for="prixProduit">Prix :</label>
            <input type="number" class="form-control" id="prixProduit" name="prixProduit" min="0" step="0.01" required>
        </div>
        <div class="form-group">
            <label for="date">Date création :</label>
            <input type="date" class="form-control" id="date" name="date" required>
        </div>
        <button type="submit" class="btn btn-primary">Ajouter</button>
    </form>

    <br/>
    <br/>
    <a href="ListeProduits">Liste Produits</a>
</div>

<!-- Add Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
</body>
</html>
