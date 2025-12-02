package com.example.agrotechgamara.util;

/*esta clase manejará las fechas automáticamente. Nota: Para las fotos (blob), Room maneja byte[] nativamente, no necesitaremos converter si en la entidad usamos byte[].*/

import androidx.room.TypeConverter;
import java.util.Date;

public class Converters {
    // Convierte de Timestamp (Base de datos) a objeto Date (Java)
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    // Convierte de objeto Date (Java) a Timestamp (Base de datos)
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
