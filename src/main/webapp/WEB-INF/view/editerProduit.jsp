<%@ page language="java" contentType="text/html; charset=windows-1256"
         pageEncoding="windows-1256"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="windows-1256">
    <title>Modifier un Produit</title>
    <style>
        /* Add your CSS styles here */
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
        }
        form {
            margin: 20px auto;
            width: 50%;
            border: 1px solid #ccc;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0px 0px 10px #ccc;
        }
        label, input[type="submit"] {
            display: block;
            margin-bottom: 10px;
        }
        input[type="text"], input[type="date"] {
            padding: 5px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }
        input[type="submit"] {
            background-color: #4CAF50;
            color: #fff;
            border: none;
            padding: 10px;
            cursor: pointer;
            border-radius: 5px;
        }
        a {
            display: block;
            margin: 20px auto;
            text-align: center;
            color: #4CAF50;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<form action="updateProduit" method="post">
<pre>
id : <input type="text" name="idProduit" value="${produit.idProduit}">
nom :<input type="text" name="nomProduit" value="${produit.nomProduit}">
prix :<input type="text" name="prixProduit" value="${produit.prixProduit}">
Date création :
<fmt:formatDate pattern="yyyy-MM-dd" value="${produit.dateCreation}" var="formatDate"
/>
<input type="date" name="date" value="${formatDate}"></input>
<input type="submit" value="Modifier">
</pre>
</form>
<br/>
<br/>
<a href="ListeProduits">Liste Produits</a>
</body>
</html>