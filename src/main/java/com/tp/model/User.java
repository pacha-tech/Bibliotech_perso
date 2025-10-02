package com.tp.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User {
    private String user_id;
    private String name;
    private String surname;
    private int tel_num;
    private String email;
    private String password;
    private String role;
    private LocalDateTime registration_date;

    public User(String user_id, String name, String surname,int tel_num, String email, String password, String role, LocalDateTime registration_date) {
        this.user_id = user_id;
        this.name = name;
        this.surname = surname;
        this.tel_num = tel_num;
        this.email = email;
        this.password = password;
        this.role = role;
        this.registration_date = registration_date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getTel_num() {
        return tel_num;
    }

    public void setTel_num(int tel_num) {
        this.tel_num = tel_num;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(LocalDateTime registration_date) {
        this.registration_date = registration_date;
    }

    public String getFormattedDateRegister() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return registration_date.format(formatter);
    }
}
