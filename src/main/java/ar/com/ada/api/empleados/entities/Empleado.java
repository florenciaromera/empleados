package ar.com.ada.api.empleados.entities;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "empleado")
public class Empleado {
    @Column(name = "empleado_id")
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

    private int dni;
    // siempre que haya FK y los tratemos como objetos será utilizado el JoinColumn
    // en una clase y en la otra mappedBy
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

    @JsonIgnore
    public BigDecimal getVentasActuales() {

        Random randomGenerator = new Random();

        // Genero un numero rando hasta 10000
        double venta = randomGenerator.nextDouble() * 10000 + 1;
        // redondeo en 2 decimales el random truncando
        venta = ((long) (venta * 100)) / 100d;

        return new BigDecimal(venta);
    }

    public EmpleadoEstadoEnum getEstadoId() {
        return EmpleadoEstadoEnum.parse(this.estadoId);
    }

    public void setEstadoId(EmpleadoEstadoEnum estadoId) {
        this.estadoId = estadoId.getValue();
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
        // por el ManyToOne
        // a la lista de empleados va a agregarle el obj
        // devuelve la lista de empleados de la categoria actual y me agrega a mí mismo
        // (o sea, a categoria)
        this.categoria.getEmpleados().add(this);
    }

    /***
     * En este caso es un ENUMERADO con numeracion customizada En JAVA, los
     * enumerados con numeros customizados deben tener un constructor y un
     * comparador para poder funcionar correctamente
     */
    public enum EmpleadoEstadoEnum {
        DESCONOCIDO(0), PENDIENTEALTA(1), ACTIVO(2), LICENCIA(3), DESVINCULADO(99);

        private final int value;

        // NOTE: Enum constructor tiene que estar en privado
        private EmpleadoEstadoEnum(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static EmpleadoEstadoEnum parse(int id) {
            EmpleadoEstadoEnum status = null; // Default
            for (EmpleadoEstadoEnum item : EmpleadoEstadoEnum.values()) {
                if (item.getValue() == id) {
                    status = item;
                    break;
                }
            }
            return status;
        }
    }
}