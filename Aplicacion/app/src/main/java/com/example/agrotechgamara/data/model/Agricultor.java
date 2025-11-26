package com.example.agrotechgamara.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "agricultor")
public class Agricultor {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idAgricultor")
    private int idAgricultor;

    @ColumnInfo(name = "nomAgricultor")
    private String nomAgricultor;

    @ColumnInfo(name = "emailAgricultor")
    private String emailAgricultor;

    @ColumnInfo(name = "contaAgricultor")
    private String contaAgricultor;

    @ColumnInfo(name = "codVerifAgricultor")
    private int codVerifAgricultor;

    // Getters & Setters
    public int getIdAgricultor() { return idAgricultor; }
    public void setIdAgricultor(int idAgricultor) { this.idAgricultor = idAgricultor; }

    public String getNomAgricultor() { return nomAgricultor; }
    public void setNomAgricultor(String nomAgricultor) { this.nomAgricultor = nomAgricultor; }

    public String getEmailAgricultor() { return emailAgricultor; }
    public void setEmailAgricultor(String emailAgricultor) { this.emailAgricultor = emailAgricultor; }

    public String getContaAgricultor() { return contaAgricultor; }
    public void setContaAgricultor(String contaAgricultor) { this.contaAgricultor = contaAgricultor; }

    public int getCodVerifAgricultor() { return codVerifAgricultor; }
    public void setCodVerifAgricultor(int codVerifAgricultor) { this.codVerifAgricultor = codVerifAgricultor; }
}
