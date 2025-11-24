package com.example.agrotechgamara.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "agricultor")
public class Agricultor {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long idAgricultor;

    private String nomAgricultor;

}
