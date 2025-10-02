<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <link rel="icon" type="image/png" href="assets/favicon.png" />
    <title>Page non trouvée - Gestion Étudiants</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f6f9;
            color: #333;
            text-align: center;
        }
        h2 {
            font-size: 26px;
            margin-bottom: 20px;
            color: #ff0040;
        }
        p {
            font-size: 18px;
            color: #666;
        }
        a {
            display: inline-block;
            margin-top: 20px;
            padding: 12px 25px;
            background-color: #135776;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            transition: background-color 0.3s ease;
        }
        a:hover {
            background-color: #3eaf1b;
        }
        .container {
            /*max-width: 600px;*/
            margin: auto;
        }
    </style>
</head>
<body>
    <div>
        <img src="assets/forbitten.jpg">
    </div>
    <div class="container">
        <h2>Vous n'avez les droits pour effectuer cette action veuillez vous connectez avec un compte admin </h2>
        <p>Voulez vous vous connecter avec un compte admin ?</p>
        <a href="login">Se connecter avec un compte admin</a>
        <p>ou retourner a l'accueil</p>
        <a href="listBooks">Retour à l'accueil</a>
    </div>
</body>
</html>
