<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tableau de bord Admin</title>
    <link rel="stylesheet" href="css/users/dashboard.css">
    <link rel="stylesheet" href="css/users/adminNavBar.css">
    <link rel="icon" type="image/png" href="assets/favicon.png" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>
    <jsp:include page="/WEB-INF/Vues/admin/adminNavBar.jsp"/>
    <main>

        <h1>Tableau de Bord</h1>
        <div class="dashboard-stats"> <div class="stat-card">
            <a href="listBooks"><h3>Livres Enregistrés</h3></a>
            <p>${bookCount}</p>
        </div>
        <div class="stat-card">
            <a href="listLoan"><h3>Livres Empruntés</h3></a>
            <p>${loanCount}</p>
        </div>
        <div class="stat-card">
            <a href="adminListReservations"><h3>Livres Reservés</h3></a>
            <p>${reservationCount}</p>
        </div>
        <div class="stat-card">
            <a href="manageUsers"><h3>Utilisateurs Enregistrés</h3></a>
            <p>${memberCount}</p>
        </div>


        <div class="dashboard-section">
            <h2>Livres les plus populaires</h2>
            <div class="popular-books">
                <c:forEach var="book" items="${popularBooks}" begin="0" end="3">
                    <div class="book-card">
                        <img src="${pageContext.request.contextPath}/${book.image}"  alt="Couverture de ${book.title}"  class="book-cover"  onerror="this.onerror=null;this.src='https://placehold.co/200x300/e0e0e0/555555?text=Pas+d%27image';">
                        <div class="book-info">
                            <h3>${book.title}</h3>
                            <p>Auteur: ${book.author}</p>
                            <p>Emprunts: ${book.loan_count}</p>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <c:if test="${fn:length(popularBooks) > 3}">
                <div class="voir-plus-container">
                    <a href="listBooks" class="voir-plus-btn">Voir plus de livres</a>
                </div>
            </c:if>
        </div>

        <div class="dashboard-section">
            <h2>Emprunts en cours</h2>
            <div class="loan-list">
                <c:forEach var="loan" items="${currentLoans}" begin="0" end="2">
                    <div class="loan-item">
                        <p><strong>Livre:</strong> ${loan.book_title}</p>
                        <p><strong>Emprunteur:</strong> ${loan.username}</p>
                        <p><strong>Date d'emprunt:</strong> ${loan.formattedBorrowDate}</p>
                        <p><strong>Date d'échéance:</strong> ${loan.formattedDueDate}</p>
                    </div>
                </c:forEach>
            </div>
            <c:if test="${fn:length(currentLoans) > 3}">
                <div class="voir-plus-container">
                    <a href="listLoan?status=null" class="voir-plus-btn">Voir les emprunts</a>
                </div>
            </c:if>
        </div>

        <div class="dashboard-section">
            <h2>Réservations en cours</h2>
            <div class="reservation-list">
                <c:forEach var="reservation" items="${activeReservations}" begin="0" end="2">
                    <div class="reservation-item">
                        <p><strong>Livre:</strong> ${reservation.book_title}</p>
                        <p><strong>Réservateur:</strong> ${reservation.user_name}</p>
                        <p><strong>Date de réservation:</strong> ${reservation.formattedDateRegister}</p>
                    </div>
                </c:forEach>
            </div>
            <c:if test="${fn:length(activeReservations) > 3}">
                <div class="voir-plus-container">
                    <a href="adminListReservations" class="voir-plus-btn">Voir les réservations</a>
                </div>
            </c:if>
        </div>
    </main>
</body>
</html>
