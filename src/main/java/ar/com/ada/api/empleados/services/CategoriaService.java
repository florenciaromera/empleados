package ar.com.ada.api.empleados.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.empleados.entities.Categoria;
import ar.com.ada.api.empleados.entities.Empleado;
import ar.com.ada.api.empleados.repos.CategoriaRepo;

// logica de negocio de las categorias
@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepo repo;

    public Optional<Categoria> crearCategoria(Categoria categoria) {
        Categoria c = obtenerPorId(categoria.getCategoriaId());
        if(c == null){
            return Optional.empty();
        }
        return Optional.of(repo.save(c));
    }

    public List<Categoria> obtenerCategorias() {
        return (repo.findAll());
    }

    public Categoria obtenerPorId(int categoriaId) {
        Optional<Categoria> c = repo.findById(categoriaId);
        if (c.isPresent())
            return c.get();
        return null;

    }

    public List<Empleado> calcularProximosSueldos() {
        List<Empleado> empleados = new ArrayList<>();

        for  (Categoria categoria : this.obtenerCategorias()) {
            for (Empleado empleado : categoria.getEmpleados()) {
                empleado.setSueldo(categoria.calcularSueldo(empleado));
                empleados.add(empleado);
            }
        }
        return empleados;
    }

    public List<Empleado> calcularProximosSueldosStream() {
        List<Empleado> empleados = new ArrayList<>();

        this.obtenerCategorias().stream().forEach(categoria -> {

            categoria.getEmpleados().stream().forEach(empleado -> {

                empleado.setSueldo(categoria.calcularSueldo(empleado));
                empleados.add(empleado);
            });

        });

        return empleados;
    }

    public List<Empleado> obtenerSueldosActualesNoStream() {
        List<Empleado> empleados = new ArrayList<>();

        for (Categoria cat : this.obtenerCategorias()) {
            empleados.addAll(cat.getEmpleados());
        }
        
        return empleados;
    }

    public List<Empleado> obtenerSueldosActuales() {
        List<Empleado> empleados = new ArrayList<>();

        this.obtenerCategorias().stream().forEach(cat -> empleados.addAll(cat.getEmpleados()));

        return empleados;
    }

    /**
     * Modo normal.
     * 
     * @return
     */
    public List<Categoria> obtenerCategoriasSinEmpleadosEstandard() {

        List<Categoria> categoriasSinEmpleados = this.obtenerCategorias();

        for (Categoria categoria : categoriasSinEmpleados) {

            if (categoria.getEmpleados().size() == 0)
                categoriasSinEmpleados.add(categoria);
        }

        return categoriasSinEmpleados;

    }

    /**
     * Modo funcional, se crea un stream, se le pasa el filter, y luego del filter
     * una condicion, esa condicion se eevalua para cada elemento, devolviendo un
     * stream de aquellos qeu el filtro haya sido verdadero. Finalmente se los toma
     * y se tranforma a una lista Otros metodos interesantes de funcional son
     * anyMatch y allMatch que detecta si hay algun elemento que cumpla una
     * condicion, o todos respectivamente.
     * 
     * @return
     */
    public List<Categoria> obtenerCategoriasSinEmpleados() {

        return this.obtenerCategorias().stream().filter(cat -> cat.getEmpleados().size() == 0)
                .collect(Collectors.toList());

    }

    /**
     * Modo normal, procedural como antes.
     * 
     * @return
     */
    public List<String> obtenerNombresCategoriasEstandar() {

        List<String> nombres = new ArrayList<>();

        for (Categoria categoria : this.obtenerCategorias()) {

            nombres.add(categoria.getNombre());
        }

        return nombres;

    }

    /**
     * Modo funcional, se crea un stream, se mapea cada elemento(recorre) un stream
     * y el segundo, en caso de ser un array de arrays de X, deuvelve un array de X
     * 
     * @return
     */
    public List<String> obtenerNombresCategorias() {

        return this.obtenerCategorias().stream().map(categoria -> categoria.getNombre()).collect(Collectors.toList());

    }
}