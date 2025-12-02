package com.example.agrotechgamara.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.agrotechgamara.data.model.Campaña;
import java.util.List;

@Dao
public interface CampañaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCampana(Campaña campaña);

    // Ordena las campañas de la más reciente a la más antigua
    @Query("SELECT * FROM campaña ORDER BY añoCampaña DESC")
    LiveData<List<Campaña>> getAllCampanas();
}