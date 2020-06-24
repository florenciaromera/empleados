package ar.com.ada.api.empleados.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.empleados.entities.Categoria;
import ar.com.ada.api.empleados.repos.CategoriaRepo;
// logica de negocio de las categorias
@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepo repo;

    public void crearCategoria(Categoria categoria){
        repo.save(categoria);

    }

    public List<Categoria> obtenerCategorias(){
        return (repo.findAll());
    }
    
}