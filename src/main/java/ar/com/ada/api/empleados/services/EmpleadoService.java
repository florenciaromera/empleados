package ar.com.ada.api.empleados.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.empleados.entities.Empleado;
import ar.com.ada.api.empleados.repos.EmpleadoRepo;

@Service
public class EmpleadoService {
    @Autowired
    private EmpleadoRepo repo;

    public void crearEmpleado(Empleado empleado){
        grabar(empleado);
    }

    public List<Empleado> obtenerEmpleados(){
        return repo.findAll();
    }

	public Empleado obtenerPorId(int id) {
		return repo.findById(id);
	}
    
    public void grabar(Empleado empleada){
        repo.save(empleada);
    }
}