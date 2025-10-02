<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion - BiblioTech</title>
    <link rel="stylesheet" href="css/login.css">
    <link rel="icon" type="image/png" href="assets/favicon.png" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>
    <div class="login-container">
        <div class="logo">
            <img src="assets/logo2.png" alt="Logo" class="logo-img">
        </div>
        <h2>Connexion</h2>

        <c:if test="${not empty error}">
            <p class="error-message">${error}</p>
        </c:if>

        <form action="login" method="post">
            <div class="form-group">
                <label for="user_id">ID Utilisateur :</label>
                <input type="text" id="user_id" name="user_id" required>
            </div>
            <div class="form-group password-group">
                <label for="password">Mot de passe :</label>
                <input type="password" id="password" name="password" required>
                <i class="fa-solid fa-eye toggle-password" id="togglePassword"></i>
            </div>
            <button type="submit" class="btn-submit">Se connecter</button>
        </form>
               <a href="/bibliotech" class="back-link back-link-button">
                   <i class="fas fa-arrow-left"></i> Retour
               </a>
    </div>

    <script>
        const togglePassword = document.getElementById("togglePassword");
        const passwordField = document.getElementById("password");

        togglePassword.addEventListener("click", function () {
            const type = passwordField.getAttribute("type") === "password" ? "text" : "password";
            passwordField.setAttribute("type", type);
            this.classList.toggle("fa-eye");
            this.classList.toggle("fa-eye-slash");
        });
    </script>
</body>
</html>