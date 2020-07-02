package ar.com.ada.api.empleados.controllersTest;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ar.com.ada.api.empleados.controllers.EmpleadoController;
import ar.com.ada.api.empleados.entities.Categoria;
import ar.com.ada.api.empleados.entities.Empleado;
import ar.com.ada.api.empleados.models.request.InfoEmpleadaRequest;
import ar.com.ada.api.empleados.models.request.SueldoModifRequest;
import ar.com.ada.api.empleados.services.CategoriaService;
import ar.com.ada.api.empleados.services.EmpleadoService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmpleadoController.class)
public class EmpleadoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    EmpleadoService empleadoService;

    @MockBean
    CategoriaService categoriaService;

    ObjectMapper mapper = new ObjectMapper();
    
    @Test
    public void deberia_devolver_empleadoCreado() throws Exception {
        InfoEmpleadaRequest info = new InfoEmpleadaRequest();
        info.categoriaId = 1;
        Categoria categoria = new Categoria();

        when(categoriaService.obtenerPorId(1)).thenReturn(categoria);

        mockMvc.perform(MockMvcRequestBuilders.post("/empleadas", "1")
               .content(mapper.writeValueAsString(info))
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Empleada creada con exito"));    
    }

    @Test
    public void obtenerEmpleada_IdInvalido_FAILED() throws Exception {
        when(empleadoService.obtenerPorId(2)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/empleadas/{id}", "2"))
               .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void obtenerEmpleada_SUCCESS() throws Exception {
        Empleado empleada = new Empleado();
        when(empleadoService.obtenerPorId(1)).thenReturn(empleada);

        mockMvc.perform(MockMvcRequestBuilders.get("/empleadas/{id}", "1"))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void actualizarSueldo_FAILED() throws Exception{
        SueldoModifRequest sueldoRequest = new SueldoModifRequest();
        when(empleadoService.obtenerPorId(3)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/empleadas/{id}/sueldos", "3")
               .content(mapper.writeValueAsString(sueldoRequest))
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void actualizarSueldo_SUCCESS() throws Exception{
        SueldoModifRequest sueldoRequest = new SueldoModifRequest();
        Empleado empleada = new Empleado();
        when(empleadoService.obtenerPorId(3)).thenReturn(empleada);

        mockMvc.perform(MockMvcRequestBuilders.put("/empleadas/{id}/sueldos", "3")
               .content(mapper.writeValueAsString(sueldoRequest))
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Sueldo actualizado con exito"));    
    }
 
    @Test
    public void bajaEmpleada_FAILED() throws Exception {
        when(empleadoService.obtenerPorId(4)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/empleadas/{id}", "4"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    } 

    @Test
    public void bajaEmpleada_SUCCESS() throws Exception {
        Empleado empleada = new Empleado();
        when(empleadoService.obtenerPorId(4)).thenReturn(empleada);

        mockMvc.perform(MockMvcRequestBuilders.delete("/empleadas/{id}", "4"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Empleada dada de baja")); 
    } 
}