package ar.com.ada.api.empleados.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ar.com.ada.api.empleados.entities.Categoria;
import ar.com.ada.api.empleados.models.response.GenericResponse;
import ar.com.ada.api.empleados.services.CategoriaService;

public class CategoriaControllerTest {
    private static final String CATEGORIA_CREADA = "Categoria creada con exito";
    private static final String ERROR_CATEGORIA = "Error: la categoria ya existe";

    private Categoria categoria;

    @InjectMocks
    private CategoriaController categoriaController;

    @Mock
    private CategoriaService categoriaService;

    @BeforeMethod
    public void setUp() {
        categoria = new Categoria();
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void crearCategoria_categoriaCreada_SUCCESS(){
        ResponseEntity re = categoriaController.crearCategoria(categoria);
        assertEquals(HttpStatus.OK, re.getStatusCode());
        
        GenericResponse gR = (GenericResponse)re.getBody();
        assertEquals(true, gR.isOk);
        assertEquals(categoria.getCategoriaId(), gR.id);
        assertEquals(CATEGORIA_CREADA, gR.message);
    }

    @Test
    public void crearCategoria_categoriaCreada_FAILED(){
        categoria.setNombre("Supervisora");
        List<Categoria> listaCategoria = new ArrayList<>();
        listaCategoria.add(categoria);
        when(categoriaService.obtenerCategorias()).thenReturn(listaCategoria);

        ResponseEntity re = categoriaController.crearCategoria(categoria);
        assertEquals(HttpStatus.CONFLICT, re.getStatusCode());
        assertEquals(ERROR_CATEGORIA, re.getBody().toString());        
    }

}