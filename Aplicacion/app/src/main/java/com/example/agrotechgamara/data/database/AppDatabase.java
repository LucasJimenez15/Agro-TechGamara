package com.example.agrotechgamara.data.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.agrotechgamara.data.dao.ActividadEmpleadoDao;
import com.example.agrotechgamara.data.dao.ActividadesDao;
import com.example.agrotechgamara.data.dao.AgricultorDao;
import com.example.agrotechgamara.data.dao.CampañaDao;
import com.example.agrotechgamara.data.dao.EmpleadoDao;
import com.example.agrotechgamara.data.dao.IncidenciaDao;
import com.example.agrotechgamara.data.dao.LoteDao;
import com.example.agrotechgamara.data.dao.PagosDao;
import com.example.agrotechgamara.data.dao.PagosEmpleadoDao;
import com.example.agrotechgamara.data.dao.RecordatorioDao;
import com.example.agrotechgamara.data.dao.RendimientoDao;
import com.example.agrotechgamara.data.dao.SembradoDao;
import com.example.agrotechgamara.data.dao.UbicacionDao;
import com.example.agrotechgamara.data.model.*;
import com.example.agrotechgamara.util.Converters;

// 1. Definir todas las entidades
@Database(entities = {
        Lote.class,
        Ubicacion.class,
        Sembrado.class,
        Campaña.class,
        Agricultor.class,
        Rendimiento.class,
        Actividades.class,
        Recordatorio.class,
        Empleado.class,
        ActividadEmpleado.class,
        Incidencia.class,
        Pagos.class,
        PagosEmpleado.class
}, version = 1, exportSchema = false)

// 2. Agregar los converters
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    // 3. Exponer los DAOs (Getters abstractos)
    public abstract LoteDao loteDao();
    public abstract UbicacionDao ubicacionDao();
    public abstract SembradoDao sembradoDao();
    public abstract CampañaDao campanaDao();
    public abstract AgricultorDao agricultorDao();
    public abstract RendimientoDao rendimientoDao();
    public abstract ActividadesDao actividadesDao();
    public abstract RecordatorioDao recordatorioDao();
    public abstract EmpleadoDao empleadoDao();
    public abstract ActividadEmpleadoDao actividadEmpleadoDao();
    public abstract IncidenciaDao incidenciaDao();
    public abstract PagosDao pagosDao();
    public abstract PagosEmpleadoDao pagosEmpleadoDao();

    // 4. Usamos patron Singleton para evitar abrir múltiples instancias de la BD
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "AgroTechGamara_database") // Nombre del archivo .db
                            .fallbackToDestructiveMigration() // Borra la DB si se cambia la versión
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}