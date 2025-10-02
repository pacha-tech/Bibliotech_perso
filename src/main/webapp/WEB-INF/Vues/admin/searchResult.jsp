<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Résultats de la Recherche - BiblioTech</title>
    <link rel="stylesheet" href="css/users/manageUsers.css">
    <link rel="icon" type="image/png" href="assets/favicon.png" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>
    <jsp:include page="/WEB-INF/Vues/admin/adminNavBar.jsp"/>
    <main class="dashboard-container">
        <h1>Résultats de la recherche</h1>

        <div class="top-actions">
            <a href="manageUsers" class="back-btn"><i class="fas fa-arrow-left"></i> Retour à la gestion</a>
        </div>

        <div class="section-card">
            <c:if test="${empty result}">
                <div class="alert info">Aucun utilisateur ne correspond à votre recherche.</div>
            </c:if>

            <c:if test="${not empty result}">
                <table class="user-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nom</th>
                            <th>Prénom</th>
                            <th>Téléphone</th>
                            <th>Email</th>
                            <th>Date d'inscription</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="u" items="${result}">
                            <tr>
                                <td>${u.user_id}</td>
                                <td>${u.name}</td>
                                <td>${u.surname}</td>
                                <td>${u.tel_num}</td>
                                <td>${u.email}</td>
                                <td>${u.formattedDateRegister}</td>
                                <td>
                                    <form action="deleteUser" method="post" onsubmit="return confirm('Voulez-vous vraiment supprimer cet utilisateur ?');">
                                        <input type="hidden" name="user_id" value="${u.user_id}">
                                        <button type="submit" class="delete-btn"><i class="fas fa-trash-alt"></i></button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </main>
</body>
</html>