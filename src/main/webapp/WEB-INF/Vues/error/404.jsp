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
            padding-top: 80px;
        }
        h1 {
            font-size: 80px;
            color: #ff4d4d;
            margin-bottom: 10px;
        }
        h2 {
            font-size: 26px;
            margin-bottom: 20px;
        }
        p {
            font-size: 18px;
            color: #666;
        }
        a {
            display: inline-block;
            margin-top: 20px;
            padding: 12px 25px;
            background-color: #137657;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            transition: background-color 0.3s ease;
        }
        a:hover {
            background-color: #135776;
        }
        .container {
            max-width: 600px;
            margin: auto;
        }
        .emoji {
            font-size: 60px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="emoji">🚫</div>
        <h1>404</h1>
        <h2>Oups ! Page introuvable</h2>
        <p>La page que vous recherchez n'existe pas ou a été déplacée.</p>
        <a href="/bibliotech">Retour à l'accueil</a>
    </div>
</body>
</html>
