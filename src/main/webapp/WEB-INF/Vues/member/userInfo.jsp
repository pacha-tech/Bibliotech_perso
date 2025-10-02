<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mon Profil</title>
    <link rel="stylesheet" href="css/users/userInfo.css">
    <link rel="icon" type="image/png" href="assets/favicon.png" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>
        <div class="section-card user-profile-card">
            <div class="profile-header">
                <i class="fas fa-user-circle profile-icon"></i>
                <h2>${user.name} ${user.surname}</h2>
            </div>

            <div class="profile-details">
                <div class="detail-item">
                    <i class="fas fa-id-badge detail-icon"></i>
                    <div class="detail-content">
                        <strong>ID Utilisateur :</strong>
                        <p>${user.user_id}</p>
                    </div>
                </div>
                <div class="detail-item">
                    <i class="fas fa-envelope detail-icon"></i>
                    <div class="detail-content">
                        <strong>Email :</strong>
                        <p>${user.email}</p>
                    </div>
                </div>
                <div class="detail-item">
                    <i class="fa fa-mobile detail-icon"></i>
                    <div class="detail-content">
                        <strong>Téléphone :</strong>
                        <p>${user.tel_num}</p>
                    </div>
                </div>
                <div class="detail-item">
                    <i class="fas fa-calendar-alt detail-icon"></i>
                    <div class="detail-content">
                        <strong>Date d'inscrit :</strong>
                        <p>${user.formattedDateRegister}</p>
                    </div>
                </div>
            </div>
            <div class="profile-actions">
                <a href="changePassword">Changer le mot de passe</a>
            </div>
        </div>
    </main>
</body>
</html>