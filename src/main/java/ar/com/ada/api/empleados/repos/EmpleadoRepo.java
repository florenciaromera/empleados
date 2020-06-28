package ar.com.ada.api.empleados.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.empleados.entities.Empleado;

@Repository
public interface EmpleadoRepo extends JpaRepository<Empleado, Integer>{
    // al crear este metodo no necesitamos usar un optional, son menos lineas de codigo
    Empleado findById(int id);

}