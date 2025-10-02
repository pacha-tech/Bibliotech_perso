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
    <link rel="stylesheet" href="css/users/returnConfirm.css">
    <link rel="icon" type="image/png" href="assets/favicon.png" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <jsp:include page="/WEB-INF/Vues/member/memberNavBar.jsp"/>
    <main class="dashboard-container">
        <h1>Mes Réservations</h1>
        <a href="reservationHistory"><i class="fa fa-history"></i><h3>Historique</h3></a>

            <c:if test="${not empty sessionScope.message}">
                <div class="message-container">
                    <div class="message">${sessionScope.message}</div>
                </div>
                <c:remove var="message" scope="session"/>
            </c:if>
            <c:if test="${not empty sessionScope.error}">
                <div class="message-container">
                    <div class="error">${sessionScope.error}</div>
                </div>
                <c:remove var="error" scope="session"/>
            </c:if>

        <div class="section-card">
            <h3>Liste de mes réservations</h3>
            <table class="user-table">
                <thead>
                    <tr>
                        <th>ID Réservation</th>
                        <th>Titre du Livre</th>
                        <th>Date de Réservation</th>
                        <th>Date d'expiration</th>
                        <th>Actions</th>
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
                                    <td>
                                        <c:if test="${res.status eq 'ACTIVE'}">
                                            <button type="button"
                                                class="return-btn"
                                                data-loan-id="${res.reservation_id}"
                                                data-book-title="${res.book_title}">
                                                <i class="fas fa-times"></i> Annuler
                                            </button>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="5" style="text-align: center;">Vous n'avez aucune réservation en cours.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
                       <div id="return-modal" class="modal" style="display:none;">
                            <div class="return-modal-content">
                                <span class="return-close-btn" onclick="closeReturnModal()">&times;</span>
                                <div id="return-modal-body"></div>
                            </div>
                        </div>
    </main>
    <script>
        window.onload = function() {
            var messageDiv = document.querySelector(".alert");
            if (messageDiv) {
                messageDiv.style.display = 'block';


                setTimeout(function() {
                    messageDiv.style.opacity = '0';
                    setTimeout(function() {
                        messageDiv.style.display = 'none';
                    }, 500);
                }, 5000);
            }
        };

        function openReturnModal(reservationId, title) {
            let modalBody = document.getElementById("return-modal-body");

            let htmlContent = '';
            htmlContent += '<h3>Confirmation</h3>';
            htmlContent += '<p>Voulez-vous vraiment annuler la reservation pour  <b>' + title + '</b> ?</p>';
            htmlContent += '<form action="cancelReservation" method="post">';
            htmlContent += '    <input type="hidden" name="reservationId" value="' + reservationId + '">';
            htmlContent += '    <div class="return-actions">';
            htmlContent += '        <button type="button" onclick="closeReturnModal()">Annuler</button>';
            htmlContent += '        <button type="submit" class="return-btn">Annulé</button>';
            htmlContent += '    </div>';
            htmlContent += '</form>';

            modalBody.innerHTML = htmlContent;
            document.getElementById("return-modal").style.display = "flex";
        }

        function closeReturnModal() {
            document.getElementById("return-modal").style.display = "none";
            document.getElementById("return-modal-body").innerHTML = "";
        }

        document.querySelectorAll('.return-btn').forEach(btn => {
                    btn.addEventListener('click', function() {
                        const loanId = this.dataset.loanId;
                        const title = this.dataset.bookTitle;
                        openReturnModal(loanId, title);
                    });
                });
    </script>
    <script src="js/message.js"></script>
</body>
</html>