package ar.com.ada.api.empleados.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ar.com.ada.api.empleados.entities.Categoria;
import ar.com.ada.api.empleados.models.response.GenericResponse;
import ar.com.ada.api.empleados.services.CategoriaService;

@TestInstance(Lifecycle.PER_CLASS)
public class CategoriaControllerTest {
    private static final String CATEGORIA_CREADA = "Categoria creada con exito";

    private Categoria categoria = new Categoria();

    @InjectMocks
    private CategoriaController categoriaController;

    @Mock
    private CategoriaService categoriaService;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void crearCategoria(){
        ResponseEntity re = categoriaController.crearCategoria(categoria);
        assertEquals(re.getStatusCode(), HttpStatus.OK);
        
        GenericResponse gR = (GenericResponse)re.getBody();
        assertEquals(gR.isOk, true);
        assertEquals(gR.id, categoria.getCategoriaId());
        assertEquals(gR.message, CATEGORIA_CREADA);
    }

}