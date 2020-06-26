package ar.com.ada.api.empleados.entities;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "categoria")
public class Categoria {
    @Column(name = "categoria_id")
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoriaId;
    private String nombre;
    @Column(name = "sueldo_base")
    private BigDecimal sueldoBase;
    // OneToMany la clase categoria va de una a muchas en empleado, va a aparecer varias veces una misma categoria
    // mappedBy como voy a referenciarme al obj categoria pero desde el punto de vista empleado, "categoria" 
    // indica que le va a dar bola al atributo categoria  que tenga el obj empleado, nombre del atributo en el obj (atributo categoria
    // en el obj empleado), se tiene que llamar "categoria" el atributo en la clase Empleado (sino no lo va a encontrar)
    // cascade, si traemos desde el repo un obj categoria el cascadeType.All va a traer todos los empleados 
    // fetch, obliga que traiga a todos de una, no se suele utilizar, es por fines educativos, se suele usar LAZY
    @OneToMany (mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    // Ignora ese atributo cuando front lo manda
    @JsonIgnore
    private List<Empleado> empleados = new ArrayList<>();

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getSueldoBase() {
        return sueldoBase;
    }

    public void setSueldoBase(BigDecimal sueldoBase) {
        this.sueldoBase = sueldoBase;
	}

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }
    
}