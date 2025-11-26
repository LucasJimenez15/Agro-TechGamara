package com.example.agrotechgamara.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
        tableName = "pagosEmpleado",
        primaryKeys = {"idPago", "idEmpleado"}//,
//        foreignKeys = {
//                @ForeignKey(entity = Pagos.class, parentColumns = "idPago", childColumns = "idPago", onDelete = ForeignKey.CASCADE),
//                @ForeignKey(entity = Empleado.class, parentColumns = "idEmpleado", childColumns = "idEmpleado", onDelete = ForeignKey.CASCADE)
//        },
//        indices = {@Index("idPago"), @Index("idEmpleado")}
)
public class PagosEmpleado {

    @ColumnInfo(name = "idPago")
    private int idPago;

    @ColumnInfo(name = "idEmpleado")
    private int idEmpleado;

    // Getters & Setters
    public int getIdPago() { return idPago; }
    public void setIdPago(int idPago) { this.idPago = idPago; }

    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }
}
