package ar.com.ada.api.empleados.controllers;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import ar.com.ada.api.empleados.services.CategoriaService;

@TestInstance(Lifecycle.PER_CLASS)
public class CategoriaControllerTest {

    @InjectMocks
    private CategoriaController categoriaController;

    @Mock
    CategoriaService categoriaService;

    /**
     * public ResponseEntity<?> crearCategoria(@RequestBody Categoria categoria){
        categoriaService.crearCategoria(categoria);
        GenericResponse gR = new GenericResponse();
        gR.isOk = true;
        gR.id = categoria.getCategoriaId();
        gR.message = "Categoria creada con exito";
        // damos una respuesta a front
        // serializar, transformar un obj o algo a un flujo de cadena de textos (JSON es un texto, convertimos el obj en 
        // java a un String de texto)
        return ResponseEntity.ok(gR);
    }
     */

    public void crearCategoria(){
        



    }


    
}