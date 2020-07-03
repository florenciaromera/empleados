package ar.com.ada.api.empleados.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ar.com.ada.api.empleados.entities.Categoria;
import ar.com.ada.api.empleados.entities.Empleado;
import ar.com.ada.api.empleados.models.request.InfoEmpleadaRequest;
import ar.com.ada.api.empleados.models.request.SueldoModifRequest;
import ar.com.ada.api.empleados.models.response.GenericResponse;
import ar.com.ada.api.empleados.services.CategoriaService;
import ar.com.ada.api.empleados.services.EmpleadoService;

public class EmpleadoControllerTest {
    private static final int CATEGORIA_ID = 3;
    private static final int EMPLEADO_ID = 4;
    private static final int ID = 2;

    private static final String ERROR_CATEGORIA = "Error, categoriaId= " + CATEGORIA_ID + " inexistente";
    private static final String EMPLEADA_CREADA = "Empleada creada con exito";
    private static final String SUELDO_ACTUALIZADO = "Sueldo actualizado con exito";
    private static final String BAJA_EMPLEADA = "Empleada dada de baja";

    @InjectMocks
    private EmpleadoController empleadoController;
    @Mock
    private CategoriaService categoriaService;
    @Mock
    private EmpleadoService empleadoService;

    private InfoEmpleadaRequest info = new InfoEmpleadaRequest();
    private SueldoModifRequest sueldoRequest = new SueldoModifRequest();


    @BeforeMethod
    public void setUp() {
        info.nombre = "Juana";
        info.edad = 30;
        info.categoriaId = CATEGORIA_ID;
        info.sueldo = new BigDecimal(30000);
        sueldoRequest.sueldoNuevo = new BigDecimal(50000);
        MockitoAnnotations.initMocks(this);
    }
    /**
     * public ResponseEntity<?> crearEmpleado(@RequestBody InfoEmpleadaRequest info) {
        if (empleadoService.obtenerEmpleados().stream().anyMatch(n -> n.getNombre().equals(info.nombre))) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: el empleado ya existe");
        }     
    }
    
    */
    
    @Test
    public void crearEmpleado_empleadoYaCreado_FAILED(){
        Empleado empleado = new Empleado();
        empleado.setNombre("Juana");
        List<Empleado> listaEmpleados = new ArrayList<>();
        listaEmpleados.add(empleado);
        when(empleadoService.obtenerEmpleados()).thenReturn(listaEmpleados);

        ResponseEntity re = empleadoController.crearEmpleado(info);
        assertEquals(HttpStatus.CONFLICT, re.getStatusCode());
        assertEquals("Error: el empleado ya existe", re.getBody().toString());
    }

    @Test
    public void crearEmpleado_categoriaInexistente_FAILED(){       
        Optional<Categoria> categoria = Optional.empty();
        when(categoriaService.obtenerPorId(CATEGORIA_ID)).thenReturn(categoria);
        
        ResponseEntity re = empleadoController.crearEmpleado(info);
        assertEquals(HttpStatus.BAD_REQUEST, re.getStatusCode());
        assertEquals(ERROR_CATEGORIA, re.getBody().toString());
    }

    @Test
    public void crearEmpleado_categoriaExistente_SUCCESS(){       
        Optional<Categoria> categoria = Optional.of(new Categoria());
        when(categoriaService.obtenerPorId(CATEGORIA_ID)).thenReturn(categoria);
        
        ResponseEntity re = empleadoController.crearEmpleado(info);
        assertEquals(HttpStatus.OK, re.getStatusCode());

        GenericResponse gR = (GenericResponse)re.getBody();
        assertEquals(true, gR.isOk);
        assertEquals(EMPLEADA_CREADA, gR.message);
    }

    @Test
    public void obtenerEmpleada_IdExistente_SUCCESS(){
        when(empleadoService.obtenerPorId(EMPLEADO_ID)).thenReturn(new Empleado());
        ResponseEntity re = empleadoController.obtenerEmpleada(EMPLEADO_ID);
        assertEquals(HttpStatus.OK, re.getStatusCode());

    }

    @Test
    public void obtenerEmpleada_IdInexistente_FAILED(){
        when(empleadoService.obtenerPorId(EMPLEADO_ID)).thenReturn(null);
        ResponseEntity re = empleadoController.obtenerEmpleada(EMPLEADO_ID);
        assertEquals(HttpStatus.NOT_FOUND, re.getStatusCode());

    }

    @Test
    public void listarPorCategoriaId_IdExistente_SUCCESS(){
        when(categoriaService.obtenerPorId(CATEGORIA_ID)).thenReturn(Optional.of(new Categoria()));
        ResponseEntity re = empleadoController.listarPorCategoriaId(CATEGORIA_ID);
        assertEquals(HttpStatus.OK, re.getStatusCode());
    }

    @Test
    public void listarPorCategoriaId_IdInexistente_FAILED(){
        when(categoriaService.obtenerPorId(CATEGORIA_ID)).thenReturn(Optional.empty());
        ResponseEntity re = empleadoController.listarPorCategoriaId(CATEGORIA_ID);
        assertEquals(HttpStatus.BAD_REQUEST, re.getStatusCode());
    }

    @Test
    public void actualizarSueldo_IdExistente_SUCCESS(){
        when(empleadoService.obtenerPorId(ID)).thenReturn(new Empleado());
        ResponseEntity re = empleadoController.actualizarSueldo(ID, sueldoRequest);
        assertEquals(HttpStatus.OK, re.getStatusCode());
        
        GenericResponse gR = (GenericResponse)re.getBody();
        assertEquals(true, gR.isOk);
        assertEquals(SUELDO_ACTUALIZADO, gR.message);
    } 

    @Test
    public void actualizarSueldo_IdInexistente_FAILED(){
        when(empleadoService.obtenerPorId(ID)).thenReturn(null);
        ResponseEntity re = empleadoController.actualizarSueldo(ID, sueldoRequest);
        assertEquals(HttpStatus.NOT_FOUND, re.getStatusCode());
    }
    
    @Test
    public void bajaEmpleada_IdExistente_SUCCESSS(){
        when(empleadoService.obtenerPorId(ID)).thenReturn(new Empleado());
        ResponseEntity re = empleadoController.bajaEmpleada(ID);
        assertEquals(HttpStatus.OK, re.getStatusCode());

        GenericResponse gR = (GenericResponse)re.getBody();
        assertEquals(true, gR.isOk);
        assertEquals(BAJA_EMPLEADA, gR.message);
    }

    @Test
    public void bajaEmpleada_IdInexistente_FAILED(){
        when(empleadoService.obtenerPorId(ID)).thenReturn(null);
        ResponseEntity re = empleadoController.bajaEmpleada(ID);
        assertEquals(HttpStatus.NOT_FOUND, re.getStatusCode());
    }
}