<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Changer votre mot de passe</title>
     <link rel="stylesheet" href="css/login.css">
     <link rel="icon" type="image/png" href="assets/favicon.png" />
</head>
<body>
    <div class="login-container">
            <div class="logo">
                <img src="assets/logo2.png" alt="Logo" class="logo-img">
            </div>
        <h2>Entrez un nouveau  mot de passe</h2>

        <c:if test="${not empty sessionScope.changePasswordError}">
            <p class="error-message">${sessionScope.changePasswordError}</p>
            <c:remove var="changePasswordError" scope="session"/>
        </c:if>

        <form action="changePassword" method="post">
            <div class="form-group">
                <label for="new_password">Nouveau mot de passe :</label>
                <input type="password" id="new_password" name="new_password" required>
            </div>
            <div class="form-group">
                <label for="confirm_password">Confirmer le mot de passe :</label>
                <input type="password" id="confirm_password" name="confirm_password" required>
            </div>
            <button type="submit" class="btn-submit">Changer le mot de passe</button>
        </form>
    </div>
</body>
</html>