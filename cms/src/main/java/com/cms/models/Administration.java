package com.cms.models;

public class Administration {
    private String president; // id
    private String vicePresident; // id
    private String accountant; // id

    public Administration() {
    }

    public Administration(String president, String vicePresident, String accountant) {
        this.president = president;
        this.vicePresident = vicePresident;
        this.accountant = accountant;
    }

    public String getPresident() {
        return president;
    }

    public void setPresident(String president) {
        this.president = president;
    }

    public String getVicePresident() {
        return vicePresident;
    }

    public void setVicePresident(String vicePresident) {
        this.vicePresident = vicePresident;
    }

    public String getAccountant() {
        return accountant;
    }

    public void setAccountant(String accountant) {
        this.accountant = accountant;
    }

}
