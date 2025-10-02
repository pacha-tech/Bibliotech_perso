<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar">
    <link rel="stylesheet" href="css/users/logoutModal.css">
    <div class="navbar-container">
        <div class="navbar-left">
            <a href="adminDashboard">
                <img src="assets/logo2.png" alt="Logo" class="logo-img">
            </a>
            <div class="user-info">
                <span class="user-name">${user.name} ${user.surname}</span>
                <div class="dashboard-label"><h3>Administrateur</h3></div>
            </div>
        </div>


        <div class="nav-links">
                    <a href="adminDashboard" class="${activePage == 'accueil' ? 'active' : ''}" >Accueil</a>
                    <a href="listBooks"  class="${activePage == 'livres' ? 'active' : ''}" >Livres</a>
                    <a href="manageUsers" class="${activePage == 'utilisateurs' ? 'active' : ''}" >Utilisateurs</a>
                    <a href="listLoan" class="${activePage == 'emprunts' ? 'active' : ''}" >Emprunts</a>
                    <a href="adminListReservations" class="${activePage == 'reservations' ? 'active' : ''}" >Réservations</a>
                </div>

        <a href="#" class="logout-btn" onclick="openLogoutModal(); return false;">
                        <i class="fas fa-sign-out-alt"></i>
        </a>
    </div>
</nav>

            <div id="logout-modal" class="logout-modal-backdrop">
                <div class="logout-modal-content">
                    <span class="logout-modal-close-btn" onclick="closeLogoutModal()">&times;</span>
                    <h3>Déconnexion</h3>
                    <p>${user.name} voulez-vous vraiment vous déconnecter?</p>
                    <div class="logout-actions">
                        <button type="button" class="cancel-btn" onclick="closeLogoutModal()">Annuler</button>
                        <a href="logout" class="confirm-logout-btn">Se déconnecter</a>
                    </div>
                </div>
            </div>

<script>

    function openLogoutModal() {
        document.getElementById("logout-modal").style.display = "flex";
    }

    function closeLogoutModal() {
        document.getElementById("logout-modal").style.display = "none";
    }

</script>