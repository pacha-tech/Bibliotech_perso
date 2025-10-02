<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mes Réservations</title>
    <link rel="stylesheet" href="css/loans/listMemberLoans.css">
    <link rel="stylesheet" href="css/users/memberNavBar.css">
    <link rel="stylesheet" href="css/users/returnConfirm.css">
    <link rel="icon" type="image/png" href="assets/favicon.png" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <jsp:include page="/WEB-INF/Vues/member/memberNavBar.jsp"/>
    <main class="dashboard-container">
        <h1>Mes Emprunts</h1>
        <a href="loanHistory"><i class="fa fa-history"></i><h3>Historique</h3></a>

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
            <h3>Liste de mes emprunts</h3>
            <table class="user-table">
                <thead>
                    <tr>
                        <th>ID Emprunts</th>
                        <th>Titre du Livre</th>
                        <th>Date d'emprunts</th>
                        <th>Date limit</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty loans}">
                            <c:forEach var="l" items="${loans}">
                                <tr>
                                    <td>${l.loan_id}</td>
                                    <td>${l.book_title}</td>
                                    <td>${l.formattedBorrowDate}</td>
                                    <td 
                                        class="due-date">${l.formattedDueDate}<br>
                                        <span class="due-date-error"></span>
                                    </td>
                                    <td>
                                        <button type="button"
                                            class="return-btn"
                                            data-loan-id="${l.loan_id}"
                                            data-book-title="${l.book_title}">
                                            Rendre
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="5" style="text-align: center;">Vous n'avez emprunter aucun livre.</td>
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
        function openReturnModal(loanId, title) {
            let modalBody = document.getElementById("return-modal-body");

            let htmlContent = '';
            htmlContent += '<h3>Confirmation</h3>';
            htmlContent += '<p>Voulez-vous rendre le livre <b>' + title + '</b> ?</p>';
            htmlContent += '<form action="returnBook" method="post">';
            htmlContent += '    <input type="hidden" name="loanId" value="' + loanId + '">';
            htmlContent += '    <div class="return-actions">';
            htmlContent += '        <button type="button" onclick="closeReturnModal()">Annuler</button>';
            htmlContent += '        <button type="submit" class="return-btn">Rendre</button>';
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

        function parseDate(dateStr) {
            if (!dateStr) return null;

            if (/^\d{4}-\d{2}-\d{2}$/.test(dateStr.trim())) {
                return new Date(dateStr + "T00:00:00");
            }

            if (/^\d{2}\/\d{2}\/\d{4}$/.test(dateStr.trim())) {
                const [day, month, year] = dateStr.split('/');
                return new Date(year, month - 1, day);
            }

            return new Date(dateStr);
        }

        const dueDateElements = document.querySelectorAll(".due-date");
        const now = new Date();
        const currentDateOnly = new Date(now.getFullYear(), now.getMonth(), now.getDate());

        dueDateElements.forEach(function(element) {
            const dateTextNode = element.childNodes[0];
            const dueDateText = dateTextNode.textContent.trim();
            const dueDate = parseDate(dueDateText);

            const errorElement = element.querySelector('.due-date-error');

            if (dueDate && dueDate < currentDateOnly) {
                element.style.color = "red";
                element.style.fontWeight = "bold";

                if (errorElement) {
                    errorElement.textContent = "Veuillez retourner ce livre";
                    errorElement.style.color = "red";
                    errorElement.style.fontSize = "10px";
                }
            }
        });
    </script>

    <script src="js/message.js"></script>
</body>
</html>