package ar.com.ada.api.empleados.entities;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.*;

@Entity
@Table (name = "empleado")
public class Empleado {
    @Column (name = "empleado_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int empleadoId;
    private String nombre;
    private int edad;
    private BigDecimal sueldo;
    private Date fechaAlta;
    private Date fechaBaja;
    // no lo tratamos como objeto por eso la anotation es column 
    @Column(name = "estado_id")
    private int estadoId;
    // siempre que haya FK y los tratemos como objetos será utilizado el JoinColumn en una clase y en la otra mappedBy  
    @JoinColumn(name = "categoria_id", referencedColumnName = "categoria_id")
    @ManyToOne
    private Categoria categoria;

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public BigDecimal getSueldo() {
        return sueldo;
    }

    public void setSueldo(BigDecimal sueldoBase) {
        this.sueldo = sueldoBase;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
        // por el ManyToOne
        // a la lista de empleados va a agregarle el obj
        // devuelve la lista de empleados de la categoria actual y me agrega a mí mismo (o sea, a categoria)
        this.categoria.getEmpleados().add(this);
    }

    
}