package ar.com.ada.api.empleados.controllersTest;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import ar.com.ada.api.empleados.controllers.CategoriaController;
import ar.com.ada.api.empleados.entities.Categoria;
import ar.com.ada.api.empleados.services.CategoriaService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CategoriaController.class)
public class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CategoriaService categoriaService;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void crearCategoria_SUCCESS()throws Exception{
        Categoria categoria = new Categoria();

        mockMvc.perform(MockMvcRequestBuilders.post("/categorias")
                .content(mapper.writeValueAsString(categoria))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Categoria creada con exito"));
    }

}