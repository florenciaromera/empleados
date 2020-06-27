package ar.com.ada.api.empleados.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.empleados.entities.Empleado;
import ar.com.ada.api.empleados.models.request.InfoEmpleadaRequest;
import ar.com.ada.api.empleados.models.request.SueldoModifRequest;
import ar.com.ada.api.empleados.models.response.GenericResponse;
import ar.com.ada.api.empleados.services.*;

@RestController
public class EmpleadoController {
    @Autowired
    EmpleadoService empleadoService;
    @Autowired
    CategoriaService categoriaService;

    @PostMapping("/empleadas")
    public ResponseEntity<?> crearEmpleado(@RequestBody InfoEmpleadaRequest info){
        Empleado empleado = new Empleado();
        empleado.setNombre(info.nombre);
        empleado.setEdad(info.edad);
        empleado.setSueldo(info.sueldo);
        empleado.setFechaAlta(new Date());
        empleado.setCategoria(categoriaService.obtenerPorId(info.categoriaId));
        empleado.setEstadoId(1);
        empleadoService.crearEmpleado(empleado);
        GenericResponse gR = new GenericResponse();
        gR.isOk = true;
        gR.id = empleado.getEmpleadoId();
        gR.message = "Empleada creada con exito";
        return ResponseEntity.ok(gR);
    }
    
    @GetMapping("/empleadas")
    public ResponseEntity<List<Empleado>> listarEmpleadas(){
        return ResponseEntity.ok(empleadoService.obtenerEmpleados());
    }

    @GetMapping("/empleadas/{id}")
    //la variable id de tipo int va a estar en la ruta, se tiene que llamar igual a como esta declarado arriba, por eso la @PathVariable
    public ResponseEntity<Empleado> obtenerEmpleada(@PathVariable int id){
        Empleado empleada = empleadoService.obtenerPorId(id);
        if(empleada == null){
            return ResponseEntity.notFound().build();
            // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(empleada);
    }

    @GetMapping("/empleadas/categorias/{categoriaId}")
    public ResponseEntity<List<Empleado>> listarPorCategoriaId(@PathVariable int categoriaId){
        List<Empleado> listaEmpleadas = categoriaService.obtenerPorId(categoriaId).getEmpleados();
        return ResponseEntity.ok(listaEmpleadas);
    }
    // el /sueldos es una forma de expresar que es diferente al put de empleados id
    // esto es para actualizar sueldo nada mas
    @PutMapping("/empleadas/{id}/sueldos")
    public ResponseEntity<GenericResponse> actualizarSueldo(@PathVariable int id, @RequestBody SueldoModifRequest sueldoRequest){
        Empleado empleada = empleadoService.obtenerPorId(id);
        if(empleada == null){
            return ResponseEntity.notFound().build();
            // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        empleada.setSueldo(sueldoRequest.sueldoNuevo);
        empleadoService.grabar(empleada);
        GenericResponse gR = new GenericResponse();
        gR.isOk = true;
        gR.id = empleada.getEmpleadoId();
        gR.message = "Sueldo actualizado con exito";
        return ResponseEntity.ok(gR);
    }
    // borrado logico, no fisico, NUNCA SE ELIMINAN LOS DATOS   
    // diferencia con el put al delete no se le manda nada
    @DeleteMapping("/empleadas/{id}")
    public ResponseEntity<GenericResponse> bajaEmpleada(@PathVariable int id){
        Empleado empleada = empleadoService.obtenerPorId(id);
        if(empleada == null){
            return ResponseEntity.notFound().build();
            // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        empleada.setFechaBaja(new Date());
        empleada.setEstadoId(2); // en mi caso 2 es INACTIVO
        empleadoService.grabar(empleada);
        GenericResponse gR = new GenericResponse();
        gR.isOk = true;
        gR.id = empleada.getEmpleadoId();
        gR.message = "Empleada dada de baja";
        return ResponseEntity.ok(gR);
    }
}