package ar.com.ada.api.empleados.models.response;

import java.math.BigDecimal;
import java.util.Date;

import ar.com.ada.api.empleados.entities.Categoria;

public class GREmpleada {
    public int empleadoId;
    public String nombre;
    public int edad;
    public BigDecimal sueldo;
    public Date fechaAlta;
    public Date fechaBaja;
    public int estadoId;
    public int dni;
    public Categoria categoria;

    public GREmpleada(){

    }

    public GREmpleada(int empleadoId, String nombre, int edad, BigDecimal sueldo, Date fechaAlta, Date fechaBaja, int estadoId, int dni, Categoria categoria){
        this.empleadoId = empleadoId;
        this.nombre = nombre;
        this.edad = edad;
        this.sueldo = sueldo;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.estadoId = estadoId;
        this.dni = dni;
        this.categoria = categoria;
    }
}
