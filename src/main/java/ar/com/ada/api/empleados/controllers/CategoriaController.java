package ar.com.ada.api.empleados.controllers;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.empleados.entities.Categoria;
import ar.com.ada.api.empleados.models.response.GenericResponse;
import ar.com.ada.api.empleados.services.CategoriaService;
// el controller va a hacer el método que vamos a exponer a internet para que desde internet a nosotros nos manden la info
// pero el controller delega al service que cree la categoria y a su vez el service se lo va a delegar al repo
// el controller es publicado en front
@RestController
public class CategoriaController {
    @Autowired
    CategoriaService categoriaService;
    // indica a front que haga un post a la ruta indicada (categorias)
    // buenas practicas sustantivos en plural(api rest)
    @PostMapping("/categorias")
    public ResponseEntity<?> crearCategoria(@RequestBody Categoria categoria){
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

    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> listarCategoria(){
        return ResponseEntity.ok(categoriaService.obtenerCategorias());     
    }
    
}