<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="css/users/logoutConfirm.css">

<div class="logout-modal-content">
    <span class="logout-close-btn" onclick="closeLogoutModal()">&times;</span>
    <h3>Confirmation de déconnexion</h3>
    <p>Êtes-vous sûr de vouloir vous déconnecter <b>${user.name} ${user.surname}</b> ?</p>

    <div class="logout-actions">
        <button type="button" onclick="closeLogoutModal()">Annuler</button>
        <a href="logout" class="logout-btn">Se déconnecter</a>
    </div>
</div>
