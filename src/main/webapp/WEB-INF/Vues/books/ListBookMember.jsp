<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des Livres</title>
    <link rel="stylesheet" href="css/books/ListBookMember.css">
    <link rel="stylesheet" href="css/users/memberNavBar.css">
    <link rel="icon" type="image/png" href="assets/favicon.png" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="/css/tailwind/main.css">
</head>
<body>
    <jsp:include page="/WEB-INF/Vues/member/memberNavBar.jsp"/>
    <div style="height: 50px;"></div>

    <!-- Barre de navigation du menu/recherche -->
<nav class="navbar-menu">
    <div class="search-section">
        <form id="searchForm" action="listBooks" method="get">
            <p>Rechercher par: </p>
            <select id="searchType" name="searchType">
                <option value="title" ${param.searchType eq 'title' ? 'selected' : ''}>Titre</option>
                <option value="author" ${param.searchType eq 'author' ? 'selected' : ''}>Auteur</option>
                <option value="year" ${param.searchType eq 'year' ? 'selected' : ''}>Année</option>
                <option value="category" ${param.searchType eq 'category' ? 'selected' : ''}>Catégorie</option>
            </select>
            <input type="search" placeholder="Rechercher..." name="searchValue" value="${param.searchValue != null ? param.searchValue : ''}">
            <button type="submit">Rechercher</button>
        </form>
    </div>

    <div class="filter-section">
        <form id="filterForm" action="listBooks" method="get">
            <p>Filtrer par :</p>
            <select id="filterType" name="filterType" onchange="this.form.submit()">
                <option value="all" ${param.filterType eq 'all' ? 'selected' : ''}>Tout</option>
                <option value="recent" ${param.filterType eq 'recent' ? 'selected' : ''}>Plus récent</option>
                <option value="old" ${param.filterType eq 'old' ? 'selected' : ''}>Plus ancien</option>
                <option value="disponible" ${param.filterType eq 'disponible' ? 'selected' : ''}>Disponible</option>
                <option value="emprunter" ${param.filterType eq 'emprunter' ? 'selected' : ''}>Emprunter</option>
                <option value="popularity" ${param.filterType eq 'popularity' ? 'selected' : ''}>Populaires</option>
            </select>
        </form>
    </div>

    <div class="header-container">
        <h1 class="page-title">Liste des Livres</h1>
    </div>
</nav>
    <div style="height: 50px;"></div>

    <!-- Grille des livres -->
    <c:if test="${not empty listbooks}">
        <div class="book-grid-container" id="bookGrid" >
            <c:forEach var="book" items="${listbooks}" varStatus="num">
                <div class="book-card">
                    <div class="image-And-icon-container">
                        <!-- L'icône pour afficher les détails au clic -->
                        <div class="icon-container" onclick="toggleBookDetails(this)">
                            <i class="fa-solid fa-ellipsis-vertical"></i>
                        </div>
                        <div class="card-image-wrapper">
                            <img src="${pageContext.request.contextPath}/${book.image}" alt="Couverture du livre" class="book-card-image" onerror="this.onerror=null;this.src='https://placehold.co/200x300/e0e0e0/555555?text=Pas+d%27image';"/>
                        </div>
                    </div>
                    <div class="card-initial-details">
                        <strong><p class="card-title">${book.title}</p></strong>
                        <p class="card-year">
                            <c:choose>
                                <c:when test="${book.status == 'disponible'}">
                                    <form action="status" method="post">
                                        <input type="hidden" name="id_book" id="" value="${book.id_Book}">
                                        <input type="hidden" name="action" id="" value="emprunté">
                                        <input type="submit" value="Emprunter" style="cursor: pointer;">
                                    </form>
                                </c:when>
                                <c:when test="${book.status == 'emprunté'}">
                                    <form action="status" method="post">
                                        <input type="hidden" name="id_book" id="" value="${book.id_Book}">
                                        <input type="hidden" name="action" id="" value="reserver">
                                        <input type="submit" value="Reserver" style="cursor: pointer;">
                                    </form>
                                </c:when>
                            </c:choose>
                        </p>
                    </div>
                    <div class="card-full-details">
                        <p><strong>Auteur:</strong> ${book.author}</p>
                        <p><strong>Catégorie:</strong> ${book.category}</p>
                        <p><strong>Description:</strong> ${book.description}</p>
                        <p><strong>Année:</strong> ${book.year}</p>
                    </div>
                </div>
            </c:forEach>
        </div>

    </c:if>

    <c:if test="${not empty sessionScope.succes}">
        <div class="message-container">
            <div class="success">${sessionScope.succes}<span>  </span><i class="fa-solid fa-circle-check"></i></div>
        </div>
        <c:remove var="succes" scope="session"/>
    </c:if>
    <c:if test="${not empty sessionScope.error}">
        <div class="message-container">
            <div class="error">${sessionScope.error}<span>  </span><i class="fa-solid fa-xmark"></i></div>
        </div>
        <c:remove var="error" scope="session"/>
    </c:if>

    <p id="emptyListMessage" class="empty-list-message" style="display: none;"></p>

    <!-- Si la liste est vide -->
    <c:if test="${empty listbooks}">
        <p class="empty-list-message">Aucun livre ne correspond a la recherche demandé</p>
    </c:if>


    <script>

        function toggleBookDetails(iconElement) {

            event.stopPropagation();

            const bookCard = iconElement.closest('.book-card');
            if (!bookCard) {
                console.error("Impossible de trouver le parent '.book-card' pour l'icône cliquée.");
                return;
            }

            const fullDetails = bookCard.querySelector('.card-full-details');
            if (!fullDetails) {
                console.error("Impossible de trouver '.card-full-details' dans la carte de livre.");
                return;
            }

            fullDetails.classList.toggle('active');
        }

        document.addEventListener('click', function(event) {
            const openDetails = document.querySelectorAll('.card-full-details.active');
            openDetails.forEach(detail => {

                if (!event.target.closest('.icon-container') && !event.target.closest('.card-full-details')) {
                    detail.classList.remove('active');
                }
            });
        });

        window.onload = function() {
            var messageDiv = document.querySelector(".message-container");
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

    </script>
</body>
</html>
