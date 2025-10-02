<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="fr">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Gestion des utilisateurs</title>
        <link rel="stylesheet" href="css/users/manageUsers.css">
        <link rel="stylesheet" href="css/users/adminNavBar.css">
        <link rel="stylesheet" href="css/users/manageUsersModal.css">
        <link rel="stylesheet" href="css/users/confirmDelete.css">
        <link rel="icon" type="image/png" href="assets/favicon.png" />
        <link rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    </head>

    <body>
        <jsp:include page="/WEB-INF/Vues/admin/adminNavBar.jsp" />
        <main class="dashboard-container">
            <h1>Gestion des utilisateurs</h1>

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

            <div class="top-controls">
                <form action="listUser" method="get" class="search-user-form">
                    <select name="searchType">
                        <option value="user_id" ${param.searchType eq 'user_id'
                            ? 'selected' : '' }>ID Utilisateur
                        </option>
                        <option value="name" ${param.searchType eq 'name' ?
                            'selected' : '' }>Nom</option>
                    </select>
                    <div class="search-input-container">
                        <input type="text" name="searchValue"
                            placeholder="Rechercher..." required
                            value="${param.searchValue != null ? param.searchValue : ''}">
                        <button type="button" class="clear-search-btn"><i
                                class="fas fa-times"></i></button>
                    </div>
                    <button type="submit"><i class="fas fa-search"></i></button>
                </form>
                <a href="#" class="add-user-btn" onclick="openModal('addUser')">
                    <i class="fas fa-user-plus"></i> Ajouter
                </a>
            </div>

            <div class="section-card">
                <h3>Liste des utilisateurs</h3>
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
                        <c:choose>
                            <c:when test="${not empty userList}">
                                <c:forEach var="u" items="${userList}">
                                    <tr>
                                        <td>${u.user_id}</td>
                                        <td>${u.name}</td>
                                        <td>${u.surname}</td>
                                        <td>${u.tel_num}</td>
                                        <td>${u.email}</td>
                                        <td>${u.formattedDateRegister}</td>
                                        <td>
                                            <button type="button"
                                                class="delete-btn"
                                                onclick="openDeleteModal('${u.user_id}', '${u.name}', '${u.surname}')">
                                                <i class="fas fa-trash-alt"></i>
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="7"
                                        style="text-align:center;">Aucun
                                        utilisateur trouvé</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
            <div id="modal" class="modal">
                <div class="modal-content">
                    <span class="close-btn"
                        onclick="closeModal()">&times;
                    </span>
                    <div id="modal-body"></div>
                </div>
            </div>

            <div id="delete-modal" class="modal" style="display:none;">
                <div class="delete-modal-content">
                    <span class="delete-close-btn"
                    onclick="closeDeleteModal()">&times;</span>
                    <div id="delete-modal-body"></div>
                </div>
            </div>

            <script>
                    function openModal(url) {
                        fetch(url)
                            .then(response => response.text())
                            .then(html => {
                                document.getElementById("modal-body").innerHTML = html;
                                document.getElementById("modal").style.display = "flex";
                            })
                            .catch(err => console.error("Erreur modal :", err));
                    }

                    function closeModal() {
                        document.getElementById("modal").style.display = "none";
                        document.getElementById("modal-body").innerHTML = "";
                    }


                    document.addEventListener('DOMContentLoaded', function () {
                        const searchInput = document.querySelector('.search-user-form input[name="searchValue"]');
                        const clearBtn = document.querySelector('.clear-search-btn');

                        function toggleClearBtn() {
                            if (searchInput.value.length > 0) {
                                clearBtn.style.display = 'block';
                            } else {
                                clearBtn.style.display = 'none';
                            }
                        }

                        searchInput.addEventListener('input', toggleClearBtn);
                        clearBtn.addEventListener('click', function () {
                            searchInput.value = '';
                            toggleClearBtn();
                            document.querySelector('.search-user-form').submit();
                        });
                        toggleClearBtn();
                    });


                    function openDeleteModal(userId, name, surname) {
                        const modalBody = document.getElementById("delete-modal-body");
                        let htmlContent = '<h3>Confirmer la suppression</h3>';
                        htmlContent += '<p>Voulez vouz supprimer <b>' + name + ' ' + surname + '</b>?</p>';
                        htmlContent += '<form action="deleteUser" method="post">';
                        htmlContent += '    <input type="hidden" name="user_id" value="' + userId + '">';
                        htmlContent += '    <div class="delete-actions">';
                        htmlContent += '        <button type="button" onclick="closeDeleteModal()">Annuler</button>';
                        htmlContent += '        <button type="submit" class="delete-btn">Supprimer</button>';
                        htmlContent += '    </div>';
                        htmlContent += '</form>';

                        modalBody.innerHTML = htmlContent;
                        document.getElementById("delete-modal").style.display = "flex";
                    }


                    function closeDeleteModal() {
                        document.getElementById("delete-modal").style.display = "none";
                        document.getElementById("delete-modal-body").innerHTML = "";
                    }
                </script>
        </main>
        <script src="js/message.js"></script>
    </body>

</html>