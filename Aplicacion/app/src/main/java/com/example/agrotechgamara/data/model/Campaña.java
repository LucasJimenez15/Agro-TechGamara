package com.example.agrotechgamara.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import org.jetbrains.annotations.NotNull;

@Entity(tableName = "campaña")
public class Campaña {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idCampaña")
    private int idCampaña;

    @ColumnInfo(name = "añoCampaña")
    @NotNull
    private int añoCampaña;

    // Getters & Setters
    public int getIdCampaña() { return idCampaña; }
    public void setIdCampaña(int idCampaña) { this.idCampaña = idCampaña; }

    public int getAñoCampaña() { return añoCampaña; }
    public void setAñoCampaña(int añoCampaña) { this.añoCampaña = añoCampaña; }
}