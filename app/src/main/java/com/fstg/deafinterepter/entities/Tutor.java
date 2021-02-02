package com.fstg.deafinterepter.entities;

import java.io.Serializable;

public class Tutor implements Serializable {
    private String tutor_id;
    private String firstName;

    private String lastName;

    private String exp;  // 3 years

    private String password;
    private String sexe;
    private String nationality;
    private String phone;
    private String email;

    public void setTutor_id(String tutor_id) {
        this.tutor_id = tutor_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Tutor(String firstNmae, String lastName, String exp, String password, String sexe, String nationality, String phone, String email) {
        this.firstName = firstNmae;
        this.lastName = lastName;
        this.exp = exp;
        this.password = password;
        this.sexe = sexe;
        this.nationality = nationality;
        this.phone = phone;
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTutor_id() {
        return tutor_id;
    }

    public Tutor() {
    }

    @Override
    public String toString() {
        return "Tutor{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", exp='" + exp + '\'' +
                ", password='" + password + '\'' +
                ", sexe='" + sexe + '\'' +
                ", nationality='" + nationality + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
