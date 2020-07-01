package ar.com.ada.api.empleados.controllersTest;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import ar.com.ada.api.empleados.controllers.EmpleadoController;
import ar.com.ada.api.empleados.services.EmpleadoService;

@RunWith(SpringRunner.class)
@WebMvcTest(EmpleadoController.class)
public class EmpleadoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    EmpleadoService empleadoService;

    ObjectMapper mapper = new ObjectMapper();
    
    @Test
    public void deberia_devolver_empleadoCreado() throws Exception{
        empleadoService.setNombre("test user");

        Empleado empleado = new Empleado();
        empleado.setNombre(empleadoService.getNombre());

        when(empleadoService.crearEmpleado(any(EmpleadoService.class))).thenReturn(empleado);
        
        mockMvc.perform(post("/empleadas")
               .content(mapper.writeValueAsString(empleadoService))
               .contentType(MediaType.APPLICATION_JSON)
               .andExpect(status().isOK())
               .andExpect(jsonPath("$.nombre")).value(empleadoService.getNombre()))

    }
}