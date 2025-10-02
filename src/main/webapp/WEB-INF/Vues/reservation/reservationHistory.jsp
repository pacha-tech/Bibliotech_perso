<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mes Réservations</title>
    <link rel="stylesheet" href="css/reservations/reservationList.css">
    <link rel="stylesheet" href="css/users/memberNavBar.css">
    <link rel="icon" type="image/png" href="assets/favicon.png" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>
    <jsp:include page="/WEB-INF/Vues/member/memberNavBar.jsp"/>
    <main class="dashboard-container">
        <h1>Historique de réservations</h1>
        <a href="memberListReservations"><i class="fa fa-arrow-left"></i><h3>Retour</h3></a>


        <c:if test="${not empty sessionScope.message}">
            <div class="alert success">${sessionScope.message}</div>
            <c:remove var="message" scope="session"/>
        </c:if>
        <c:if test="${not empty sessionScope.error}">
            <div class="alert error">${sessionScope.error}</div>
            <c:remove var="error" scope="session"/>
        </c:if>

        <div class="top-controls">
            <form action="reservationHistory" method="get" class="filter-form">
                <label for="status">Filtrer par statut :</label>
                <select name="status" id="status" onchange="this.form.submit()">
                    <option value="">Tous</option>
                    <option value="ACTIVE" ${param.status eq 'ACTIVE' ? 'selected' : ''}>Active</option>
                    <option value="FULFILLED" ${param.status eq 'FULFILLED' ? 'selected' : ''}>Terminée</option>
                    <option value="CANCELLED" ${param.status eq 'CANCELLED' ? 'selected' : ''}>Annulée</option>
                    <option value="EXPIRED" ${param.status eq 'EXPIRED' ? 'selected' : ''}>Expirée</option>
                </select>
            </form>

            <form action="reservationHistory" method="get" class="search-user-form">
                <select name="searchType">
                    <option value="bookId" ${param.searchType eq 'bookId' ? 'selected' : ''}>ID du livre</option>
                    <option value="bookName" ${param.searchType eq 'bookName' ? 'selected' : ''}>Titre du livre</option>
                </select>

                <div class="search-input-container">
                    <input type="text" name="searchValue" placeholder="Rechercher..."
                           required value="${param.searchValue != null ? param.searchValue : ''}">
                    <button type="button" class="clear-search-btn"><i class="fas fa-times"></i></button>
                </div>

                <button type="submit"><i class="fas fa-search"></i></button>
            </form>
        </div>
        <div class="section-card">
            <h3>Historique de réservations</h3>
            <table class="user-table">
                <thead>
                    <tr>
                        <th>ID Réservation</th>
                        <th>Titre du Livre</th>
                        <th>Date de Réservation</th>
                        <th>Date d'expiration</th>
                        <th>Statut</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty reservations}">
                            <c:forEach var="res" items="${reservations}">
                                <tr>
                                    <td>${res.reservation_id}</td>
                                    <td>${res.book_title}</td>
                                    <td>${res.formattedDateRegister}</td>
                                    <td>${res.formattedDueRegister}</td>
                                    <td>${res.status}</td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="5" style="text-align: center;">Vous n'avez jamais reservé de livres.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>

                <script>
                    document.addEventListener('DOMContentLoaded', function() {
                        const searchForm = document.querySelector('.search-user-form');
                        const searchInput = searchForm.querySelector('input[name="searchValue"]');
                        const clearBtn = searchForm.querySelector('.clear-search-btn');

                        function toggleClearBtn() {
                            if (searchInput.value.length > 0) {
                                clearBtn.style.display = 'block';
                            } else {
                                clearBtn.style.display = 'none';
                            }
                        }

                        searchInput.addEventListener('input', toggleClearBtn);
                        clearBtn.addEventListener('click', function() {
                            searchInput.value = '';
                            toggleClearBtn();
                            searchForm.submit();
                        });

                        toggleClearBtn();
                    });
                </script>
    </main>
</body>
</html>