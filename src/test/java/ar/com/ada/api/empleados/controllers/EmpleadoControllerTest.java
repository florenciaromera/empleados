package ar.com.ada.api.empleados.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

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
import ar.com.ada.api.empleados.models.request.InfoEmpleadaRequest;
import ar.com.ada.api.empleados.models.response.GenericResponse;
import ar.com.ada.api.empleados.services.CategoriaService;
import ar.com.ada.api.empleados.services.EmpleadoService;

@TestInstance(Lifecycle.PER_CLASS)
public class EmpleadoControllerTest {
    private static final int CATEGORIA_ID = 3;

    @InjectMocks
    private EmpleadoController empleadoController;
    @Mock
    private CategoriaService categoriaService;
    @Mock
    private EmpleadoService empleadoService;

    private InfoEmpleadaRequest info = new InfoEmpleadaRequest();

    @BeforeAll
    public void setUp() {
        info.nombre = "Juana";
        info.edad = 30;
        info.categoriaId = CATEGORIA_ID;
        info.sueldo = new BigDecimal(30000);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void crearEmpleado_categoriaInexistente_FAILED(){       
        Optional<Categoria> categoria = Optional.empty();
        when(categoriaService.obtenerPorId(CATEGORIA_ID)).thenReturn(categoria);
        
        ResponseEntity re = empleadoController.crearEmpleado(info);
        assertEquals(re.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(re.getBody().toString(), "Error, categoriaId= " + CATEGORIA_ID + " inexistente");
    }

    @Test
    public void crearEmpleado_categoriaExistente_SUCCESS(){       
        Optional<Categoria> categoria = Optional.of(new Categoria());
        when(categoriaService.obtenerPorId(CATEGORIA_ID)).thenReturn(categoria);
        
        ResponseEntity re = empleadoController.crearEmpleado(info);
        assertEquals(re.getStatusCode(), HttpStatus.OK);

        GenericResponse gR = (GenericResponse)re.getBody();
        assertEquals(gR.isOk, true);
        assertEquals(gR.message, "Empleada creada con exito");
    }

}