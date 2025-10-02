<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<body>
    <link rel="stylesheet" href="css/users/addMember.css">

    <c:if test="${not empty sessionScope.error}">
        <div class="alert error">${sessionScope.error}</div>
        <c:remove var="error" scope="session"/>
    </c:if>

    <div class="title">
        <h3>Ajoutez un nouveau Membre</h3>
    </div>

    <form action="addUser" method="post" class="add-user-form">
        <div class="form-group">
            <label for="name">Nom <span style="color: red; font-size: 17px;">*</span></label>
            <input type="text" id="name" name="name" placeholder="ex. Mike" required>
        </div>
        <div class="form-group">
            <label for="surname">Prénom <span style="color: red; font-size: 17px;">*</span></label>
            <input type="text" id="surname" name="surname" placeholder="ex. Ken" required>
        </div>
        <div class="form-group">
            <label for="tel_num">Numéro de téléphone <span style="color: red; font-size: 17px;">*</span></label>
            <input type="tel" id="tel_num" name="tel_num" placeholder="6xxxxxxxx" required>
            <span id="tel-error" style="color: red;"></span>
        </div>
        <div class="form-group">
            <label for="email">Email <span style="color: red; font-size: 17px;">*</span></label>
            <input type="email" id="email" name="email" placeholder="ex. mike@gmail.com" required>
            <span id="email-error" style="color: red;"></span>
        </div>
        <div class="form-actions">
            <a href="manageUsers" class="cancel-btn">Annuler</a>
            <button type="submit" class="add-btn"><i class="fas fa-user-plus"></i> Ajouter</button>
        </div>
    </form>
    <script src="js/formValidation.js"></script>
</body>
</html>
